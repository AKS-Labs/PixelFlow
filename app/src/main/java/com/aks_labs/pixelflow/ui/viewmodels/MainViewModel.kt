package com.aks_labs.pixelflow.ui.viewmodels

import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import androidx.core.content.FileProvider
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.aks_labs.pixelflow.data.models.SimpleFolder
import com.aks_labs.pixelflow.data.models.SimpleScreenshot
import com.aks_labs.pixelflow.pixelFlowApp
import com.aks_labs.pixelflow.services.ViewBasedFloatingBubbleService
import com.aks_labs.pixelflow.data.SharedPrefsManager.ThemeMode
import java.io.File
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * ViewModel for the main screen
 */
class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val sharedPrefsManager = application.pixelFlowApp.sharedPrefsManager

    // DataStore keys
    private val BUBBLE_ENABLED_KEY = booleanPreferencesKey("bubble_enabled")

    // Folders
    val folders: Flow<List<SimpleFolder>> = sharedPrefsManager.folders

    // Screenshots
    val allScreenshots: Flow<List<SimpleScreenshot>> = sharedPrefsManager.screenshots

    // Filtered screenshots for UI display
    private val _filteredScreenshots = MutableStateFlow<List<SimpleScreenshot>>(emptyList())
    val filteredScreenshots = _filteredScreenshots.asStateFlow()

    // Grid columns for screenshot display
    private val _gridColumns = MutableStateFlow(3) // Set to 3 columns by default
    val gridColumns = _gridColumns.asStateFlow()

    // Bubble enabled preference
    val isBubbleEnabled = application.pixelFlowApp.dataStore.data
        .map { preferences ->
            preferences[BUBBLE_ENABLED_KEY] ?: true // Default to true
        }
        .stateIn(viewModelScope, SharingStarted.Eagerly, true)

    // Theme mode
    private val _themeMode = MutableStateFlow(sharedPrefsManager.getThemeMode())
    val themeMode = _themeMode.asStateFlow()

    /**
     * Set whether the floating bubble is enabled
     */
    fun setBubbleEnabled(enabled: Boolean) {
        viewModelScope.launch {
            getApplication<Application>().pixelFlowApp.dataStore.edit { preferences ->
                preferences[BUBBLE_ENABLED_KEY] = enabled
            }

            // Start or stop the service based on the setting
            val context = getApplication<Application>()
            if (enabled) {
                startFloatingBubbleService(context)
            } else {
                stopFloatingBubbleService(context)
            }
        }
    }

    /**
     * Start the floating bubble service
     */
    fun startFloatingBubbleService(context: Context) {
        val intent = Intent(context, ViewBasedFloatingBubbleService::class.java)
        intent.action = ViewBasedFloatingBubbleService.ACTION_START_FROM_APP
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent)
        } else {
            context.startService(intent)
        }
    }

    /**
     * Stop the floating bubble service
     */
    fun stopFloatingBubbleService(context: Context) {
        val intent = Intent(context, ViewBasedFloatingBubbleService::class.java)
        context.stopService(intent)
    }

    /**
     * Check if the app has overlay permission
     */
    fun hasOverlayPermission(context: Context): Boolean {
        return Settings.canDrawOverlays(context)
    }

    /**
     * Get the intent to request overlay permission
     */
    fun getOverlayPermissionIntent(context: Context): Intent {
        return Intent(
            Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
            Uri.parse("package:${context.packageName}")
        ).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
    }

    /**
     * Add a new folder
     */
    fun addFolder(name: String, iconName: String) {
        val id = sharedPrefsManager.generateFolderId()
        val position = sharedPrefsManager.foldersValue.size
        val folder = SimpleFolder(
            id = id,
            name = name,
            iconName = iconName,
            position = position,
            isDefault = false
        )
        sharedPrefsManager.addFolder(folder)

        // Create the physical folder in external storage
        sharedPrefsManager.getFolderDirectory(name)
    }

    /**
     * Update an existing folder
     */
    fun updateFolder(folder: SimpleFolder) {
        sharedPrefsManager.updateFolder(folder)
    }

    /**
     * Delete a folder
     */
    fun deleteFolder(folder: SimpleFolder) {
        sharedPrefsManager.deleteFolder(folder.id)
    }

    /**
     * Get screenshots for a specific folder
     */
    fun getScreenshotsForFolder(folderId: Long): List<SimpleScreenshot> {
        return sharedPrefsManager.getScreenshotsByFolder(folderId)
    }

    /**
     * Get the number of screenshots in a folder
     */
    fun getScreenshotCountForFolder(folderId: Long): Int {
        return sharedPrefsManager.getScreenshotsByFolder(folderId).size
    }

    /**
     * Get the thumbnail paths for screenshots in a folder (limited to the most recent 3)
     */
    fun getScreenshotThumbnailsForFolder(folderId: Long, limit: Int = 3): List<String> {
        val screenshots = sharedPrefsManager.getScreenshotsByFolder(folderId)
            .sortedByDescending { it.savedTimestamp }
            .take(limit)

        // Log the thumbnails for debugging
        screenshots.forEachIndexed { index, screenshot ->
            android.util.Log.d("PixelFlow", "Thumbnail $index for folder $folderId: ${screenshot.thumbnailPath}")
            // Check if file exists
            val file = java.io.File(screenshot.thumbnailPath)
            android.util.Log.d("PixelFlow", "File exists: ${file.exists()}, size: ${file.length()}")
        }

        return screenshots.map { it.thumbnailPath }
    }

    /**
     * Delete a screenshot
     */
    fun deleteScreenshot(screenshot: SimpleScreenshot) {
        sharedPrefsManager.deleteScreenshot(screenshot.id)
    }

    /**
     * Move a screenshot to a different folder
     */
    fun moveScreenshotToFolder(screenshotId: Long, folderId: Long) {
        sharedPrefsManager.moveScreenshotToFolder(screenshotId, folderId)
    }

    /**
     * Set the theme mode
     */
    fun setThemeMode(themeMode: ThemeMode) {
        sharedPrefsManager.setThemeMode(themeMode)
        _themeMode.value = themeMode
    }

    /**
     * Load screenshots from the device
     */
    fun loadDeviceScreenshots() {
        viewModelScope.launch {
            try {
                val screenshots = loadScreenshotsFromMediaStore()
                // Always sort by newest first (highest timestamp first)
                // Use originalTimestamp for consistent sorting
                val sortedScreenshots = screenshots.sortedByDescending { it.originalTimestamp }
                _filteredScreenshots.value = sortedScreenshots
                Log.d("MainViewModel", "Loaded ${sortedScreenshots.size} screenshots")
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error loading screenshots", e)
                _filteredScreenshots.value = emptyList()
            }
        }
    }

    /**
     * Refresh screenshots immediately - used when a new screenshot is detected
     * This is a more aggressive refresh that ensures new screenshots appear immediately
     */
    fun refreshScreenshotsImmediately() {
        viewModelScope.launch {
            try {
                // Force a refresh from MediaStore
                val screenshots = loadScreenshotsFromMediaStore(forceRefresh = true)
                val sortedScreenshots = screenshots.sortedByDescending { it.originalTimestamp }
                _filteredScreenshots.value = sortedScreenshots
                Log.d("MainViewModel", "Immediately refreshed ${sortedScreenshots.size} screenshots")
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error refreshing screenshots immediately", e)
                // Don't clear the list on error, keep the existing screenshots
            }
        }
    }

    /**
     * Load screenshots from the MediaStore
     *
     * @param forceRefresh If true, forces a refresh of the media store cache
     */
    private fun loadScreenshotsFromMediaStore(forceRefresh: Boolean = false): List<SimpleScreenshot> {
        val screenshots = mutableListOf<SimpleScreenshot>()
        val context = getApplication<Application>()

        try {
            // If forceRefresh is true, trigger a media scan to ensure we have the latest data
            if (forceRefresh) {
                Log.d("MainViewModel", "Forcing media store refresh")
                // This will help ensure the MediaStore is up-to-date
                context.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE))
            }

            // Query the media store for screenshots
            val projection = arrayOf(
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DATE_ADDED,
                MediaStore.Images.Media.DATE_MODIFIED
            )

            // Look for files in the Screenshots directory
            val selection = "${MediaStore.Images.Media.DATA} LIKE ?"
            val selectionArgs = arrayOf("%/Screenshots/%")
            val sortOrder = "${MediaStore.Images.Media.DATE_MODIFIED} DESC"

            context.contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                sortOrder
            )?.use { cursor ->
                val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
                val dataColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                val dateAddedColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_ADDED)
                val dateModifiedColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_MODIFIED)

                while (cursor.moveToNext()) {
                    val id = cursor.getLong(idColumn)
                    val filePath = cursor.getString(dataColumn)
                    val dateAdded = cursor.getLong(dateAddedColumn)
                    val dateModified = cursor.getLong(dateModifiedColumn)

                    val file = File(filePath)
                    if (file.exists() && file.name.contains("screenshot", ignoreCase = true)) {
                        val screenshot = SimpleScreenshot(
                            id = id,
                            filePath = filePath,
                            thumbnailPath = filePath, // Use the same path for thumbnail for now
                            folderId = 0, // Default folder
                            originalTimestamp = dateModified * 1000, // Use modified date for better sorting
                            savedTimestamp = dateModified * 1000 // Use the same timestamp for consistency
                        )
                        screenshots.add(screenshot)
                    }
                }
            }

            Log.d("MainViewModel", "Loaded ${screenshots.size} screenshots from device")
            return screenshots
        } catch (e: Exception) {
            Log.e("MainViewModel", "Error loading screenshots from device", e)
            return emptyList()
        }
    }

    /**
     * Share a screenshot
     */
    fun shareScreenshot(context: Context, screenshot: SimpleScreenshot) {
        try {
            val file = File(screenshot.filePath)
            if (!file.exists()) {
                Log.e("MainViewModel", "Screenshot file does not exist: ${file.absolutePath}")
                return
            }

            val uri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                file
            )

            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_STREAM, uri)
                type = "image/png"
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }

            context.startActivity(Intent.createChooser(shareIntent, "Share Screenshot"))
        } catch (e: Exception) {
            Log.e("MainViewModel", "Error sharing screenshot", e)
        }
    }

    /**
     * Share multiple screenshots
     */
    fun shareMultipleScreenshots(context: Context, screenshots: List<SimpleScreenshot>) {
        if (screenshots.isEmpty()) {
            Log.d("MainViewModel", "No screenshots to share")
            return
        }

        try {
            // If there's only one screenshot, use the single share method
            if (screenshots.size == 1) {
                shareScreenshot(context, screenshots.first())
                return
            }

            // For multiple screenshots, create a list of URIs
            val uriList = ArrayList<Uri>()

            for (screenshot in screenshots) {
                val file = File(screenshot.filePath)
                if (!file.exists()) {
                    Log.e("MainViewModel", "Screenshot file does not exist: ${file.absolutePath}")
                    continue
                }

                val uri = FileProvider.getUriForFile(
                    context,
                    "${context.packageName}.fileprovider",
                    file
                )
                uriList.add(uri)
            }

            if (uriList.isEmpty()) {
                Log.e("MainViewModel", "No valid screenshots to share")
                return
            }

            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND_MULTIPLE
                putParcelableArrayListExtra(Intent.EXTRA_STREAM, uriList)
                type = "image/*"
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }

            context.startActivity(Intent.createChooser(shareIntent, "Share ${uriList.size} Screenshots"))
        } catch (e: Exception) {
            Log.e("MainViewModel", "Error sharing multiple screenshots", e)
        }
    }

    /**
     * Set the number of grid columns for screenshot display
     */
    fun setGridColumns(columns: Int) {
        _gridColumns.value = columns
    }
}
