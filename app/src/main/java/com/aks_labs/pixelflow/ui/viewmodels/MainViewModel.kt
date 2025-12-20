package com.akslabs.pixelscreenshots.ui.viewmodels

import android.app.Application
import android.content.Context
import android.content.Intent
import android.database.ContentObserver
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import androidx.core.content.FileProvider
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.akslabs.pixelscreenshots.data.models.SimpleFolder
import com.akslabs.pixelscreenshots.data.models.SimpleScreenshot
import com.akslabs.pixelscreenshots.data.paging.ScreenshotPagingSource
import com.akslabs.pixelscreenshots.pixelFlowApp
import com.akslabs.pixelscreenshots.services.ViewBasedFloatingBubbleService
import com.akslabs.pixelscreenshots.data.SharedPrefsManager.ThemeMode
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
    private val DYNAMIC_COLORS_ENABLED_KEY = booleanPreferencesKey("dynamic_colors_enabled")

    // Folders
    val folders: Flow<List<SimpleFolder>> = sharedPrefsManager.folders

    // Screenshots
    val allScreenshots: Flow<List<SimpleScreenshot>> = sharedPrefsManager.screenshots

    // Paging 3 for Screenshots
    val screenshotPager = androidx.paging.Pager(
        config = androidx.paging.PagingConfig(
            pageSize = 20,
            enablePlaceholders = false
        )
    ) {
        com.akslabs.pixelscreenshots.data.paging.ScreenshotPagingSource(getApplication<Application>().contentResolver)
    }.flow.cachedIn(viewModelScope)

    // Filtered screenshots for UI display - DEPRECATED in favor of screenshotPager
    // Keeping it as empty list to avoid immediate compilation errors in other files before they are refactored
    private val _filteredScreenshots = MutableStateFlow<List<SimpleScreenshot>>(emptyList())
    val filteredScreenshots = _filteredScreenshots.asStateFlow()

    // Grid columns for screenshot display
    private val _gridColumns = MutableStateFlow(4) // Set to 3 columns by default
    val gridColumns = _gridColumns.asStateFlow()

    // Bubble enabled preference
    val isBubbleEnabled = application.pixelFlowApp.dataStore.data
        .map { preferences ->
            preferences[BUBBLE_ENABLED_KEY] ?: true // Default to true
        }
        .stateIn(viewModelScope, SharingStarted.Eagerly, true)

    // Dynamic colors enabled preference
    val isDynamicColorsEnabled = application.pixelFlowApp.dataStore.data
        .map { preferences ->
            preferences[DYNAMIC_COLORS_ENABLED_KEY] ?: false // Default to false
        }
        .stateIn(viewModelScope, SharingStarted.Eagerly, false)

    // Theme mode
    private val _themeMode = MutableStateFlow(sharedPrefsManager.getThemeMode())
    val themeMode = _themeMode.asStateFlow()
    
    // MediaStore change notification
    private val _mediaStoreChanged = MutableStateFlow(0L)
    val mediaStoreChanged = _mediaStoreChanged.asStateFlow()
    
    // MediaStore ContentObserver to detect external changes
    private val mediaStoreObserver = object : ContentObserver(Handler(Looper.getMainLooper())) {
        override fun onChange(selfChange: Boolean, uri: Uri?) {
            super.onChange(selfChange, uri)
            Log.d("MainViewModel", "MediaStore changed, triggering refresh")
            // Increment counter to notify observers
            _mediaStoreChanged.value = System.currentTimeMillis()
            // Trigger SharedPrefsManager refresh for folder-based screenshots
            sharedPrefsManager.refresh()
        }
    }
    
    init {
        // Register MediaStore observer to detect external file changes
        try {
            getApplication<Application>().contentResolver.registerContentObserver(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                true,
                mediaStoreObserver
            )
            Log.d("MainViewModel", "MediaStore observer registered")
        } catch (e: Exception) {
            Log.e("MainViewModel", "Failed to register MediaStore observer", e)
        }
    }
    
    override fun onCleared() {
        super.onCleared()
        // Unregister MediaStore observer
        try {
            getApplication<Application>().contentResolver.unregisterContentObserver(mediaStoreObserver)
            Log.d("MainViewModel", "MediaStore observer unregistered")
        } catch (e: Exception) {
            Log.e("MainViewModel", "Failed to unregister MediaStore observer", e)
        }
    }

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
     * Set whether dynamic colors are enabled
     */
    fun setDynamicColorsEnabled(enabled: Boolean) {
        viewModelScope.launch {
            getApplication<Application>().pixelFlowApp.dataStore.edit { preferences ->
                preferences[DYNAMIC_COLORS_ENABLED_KEY] = enabled
            }
        }
    }

    /**
     * Start the floating bubble service
     */

    /**
     * Start the floating bubble service
     */
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
     * Delete a folder physically from storage along with all its contents
     */
    fun deleteFolderPhysically(folderId: Long) {
        sharedPrefsManager.deleteFolderPhysically(folderId)
    }

    /**
     * Get screenshots for a specific folder
     */
    fun getScreenshotsForFolder(folderId: Long): List<SimpleScreenshot> {
        return sharedPrefsManager.getScreenshotsByFolder(folderId)
    }

    /**
     * Get screenshots for a specific folder as a Flow
     */
    fun getScreenshotsForFolderAsFlow(folderId: Long): Flow<List<SimpleScreenshot>> {
        return allScreenshots.map { screenshots ->
            screenshots.filter { it.folderId == folderId }
        }
    }

    /**
     * Get screenshots for a specific folder by folder name (scans filesystem)
     */
    fun getScreenshotsForFolderByNameAsFlow(folderName: String): Flow<List<SimpleScreenshot>> {
        // Combine mediaStoreChanged flow to trigger refresh when external changes occur
        return mediaStoreChanged.map {
            getSharedPrefsManager().getImagesFromFolder(folderName)
        }
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
            android.util.Log.d("Pixel Screenshots", "Thumbnail $index for folder $folderId: ${screenshot.thumbnailPath}")
            // Check if file exists
            val file = java.io.File(screenshot.thumbnailPath)
            android.util.Log.d("Pixel Screenshots", "File exists: ${file.exists()}, size: ${file.length()}")
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
     * Delete a screenshot by file path (for filesystem-scanned screenshots)
     */
    fun deleteScreenshotByPath(filePath: String) {
        sharedPrefsManager.deleteScreenshotByPath(filePath)
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
     * Load screenshots - Deprecated, now handled by Pager
     */
    fun loadDeviceScreenshots() {
        // No-op, handled by Pager
    }

    /**
     * Refresh screenshots immediately
     * Note: With Paging 3, UI should call items.refresh()
     */
    fun refreshScreenshotsImmediately() {
         // This might be needed if we want to force invalidate the data source
         // But mostly handled by UI adapter refresh.
         // For now, we'll leave it as no-op or we could emit a signal to UI to refresh.
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

    // --- Compatibility Region for FolderScreen ---

    // Sort Mode
    private val _sortMode = MutableStateFlow(com.akslabs.pixelscreenshots.data.models.MediaItemSortMode.DateTaken)
    val sortMode = _sortMode.asStateFlow()

    // Album Column Size
    val albumColumnSize = MutableStateFlow(3)
    val allAvailableAlbums = folders

    // Thumbnails Map
    private val _albumsThumbnailsMap = androidx.compose.runtime.mutableStateMapOf<Long, SimpleScreenshot>()
    val albumsThumbnailsMap: Map<Long, SimpleScreenshot> get() = _albumsThumbnailsMap

    // Settings Delegate
    val settings = SettingsDelegate()

    inner class SettingsDelegate {
        val AlbumsList = AlbumsListDelegate()
        val DefaultTabs = DefaultTabsDelegate()
    }

    inner class AlbumsListDelegate {
        fun getNormalAlbums() = folders
        fun getAlbumSortMode() = _albumSortMode.asStateFlow()
        fun getSortByDescending() = _sortByDescending.asStateFlow()
        suspend fun setAlbumsList(list: List<SimpleFolder>) {
             // Logic to reorder would go here (e.g., update DB position)
        }
        fun setAlbumSortMode(mode: com.akslabs.pixelscreenshots.data.models.AlbumSortMode) { _albumSortMode.value = mode }
        fun setSortByDescending(desc: Boolean) { _sortByDescending.value = desc }
    }

    inner class DefaultTabsDelegate {
         fun getTabList() = kotlinx.coroutines.flow.flowOf(emptyList<String>())
    }

    // Internal state for sorting
    private val _albumSortMode = MutableStateFlow(com.akslabs.pixelscreenshots.data.models.AlbumSortMode.Custom)
    private val _sortByDescending = MutableStateFlow(true)

    // Refresh Logic
    fun refreshAlbums(context: Context, albums: List<SimpleFolder>, sortMode: com.akslabs.pixelscreenshots.data.models.MediaItemSortMode) {
         viewModelScope.launch {
             refreshAlbumsAsync(albums)
         }
    }

    /**
     * Asynchronous version of refreshAlbums that can be awaited
     */
    suspend fun refreshAlbumsAsync(albums: List<SimpleFolder>) {
         android.util.Log.d("refreshAlbumsAsync", "Starting refresh for ${albums.size} albums")
         
         albums.forEach { folder ->
              // Scan the actual filesystem folder for images
              val thumbs = getSharedPrefsManager().getImagesFromFolder(folder.name)
                  .sortedByDescending { it.savedTimestamp }
                  .take(1)
              
              android.util.Log.d("refreshAlbumsAsync", "Folder '${folder.name}' (id=${folder.id}): found ${thumbs.size} thumbnails")
              
              if (thumbs.isNotEmpty()) {
                    _albumsThumbnailsMap[folder.id] = thumbs.first()
                    android.util.Log.d("refreshAlbumsAsync", "Stored thumbnail for '${folder.name}': ${thumbs.first().filePath}")
              } else {
                    // Remove from map if no screenshots in folder
                    _albumsThumbnailsMap.remove(folder.id)
                    android.util.Log.d("refreshAlbumsAsync", "No images found in folder '${folder.name}'")
              }
         }
         
         android.util.Log.d("refreshAlbumsAsync", "Refresh complete. Total thumbnails: ${_albumsThumbnailsMap.size}")
    }
    
    // Helper to expose sharedManager
    fun getSharedPrefsManager() = sharedPrefsManager

    // --- End Compatibility Region ---
}
