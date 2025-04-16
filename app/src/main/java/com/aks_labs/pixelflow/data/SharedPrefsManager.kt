package com.aks_labs.pixelflow.data

import android.content.Context
import android.content.SharedPreferences
import android.os.Environment
import com.aks_labs.pixelflow.data.models.SimpleFolder
import com.aks_labs.pixelflow.data.models.SimpleScreenshot
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.json.JSONArray
import org.json.JSONObject
import java.io.File

/**
 * Manager for storing and retrieving data using SharedPreferences
 */
class SharedPrefsManager(context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        PREFS_NAME, Context.MODE_PRIVATE
    )

    // StateFlows to observe data changes
    private val _folders = MutableStateFlow<List<SimpleFolder>>(emptyList())
    val folders: Flow<List<SimpleFolder>> = _folders.asStateFlow()
    val foldersValue: List<SimpleFolder> get() = _folders.value

    private val _screenshots = MutableStateFlow<List<SimpleScreenshot>>(emptyList())
    val screenshots: Flow<List<SimpleScreenshot>> = _screenshots.asStateFlow()
    val screenshotsValue: List<SimpleScreenshot> get() = _screenshots.value

    // App directory
    private val appDirectory: File

    init {
        // Create app directory
        appDirectory = createAppDirectory()

        // Load initial data
        loadFolders()
        loadScreenshots()
    }

    /**
     * Create the main app directory and folder subdirectories
     */
    private fun createAppDirectory(): File {
        val baseDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val appDir = File(baseDir, "PixelFlow")

        if (!appDir.exists()) {
            appDir.mkdirs()
        }

        return appDir
    }

    /**
     * Get the folder directory for a specific folder
     */
    fun getFolderDirectory(folderName: String): File {
        val folderDir = File(appDirectory, folderName)
        if (!folderDir.exists()) {
            folderDir.mkdirs()
        }
        return folderDir
    }

    /**
     * Load folders from SharedPreferences
     */
    private fun loadFolders() {
        val foldersJson = sharedPreferences.getString(KEY_FOLDERS, null) ?: return
        val foldersList = mutableListOf<SimpleFolder>()

        try {
            val jsonArray = JSONArray(foldersJson)
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                foldersList.add(
                    SimpleFolder(
                        id = jsonObject.getLong("id"),
                        name = jsonObject.getString("name"),
                        iconName = jsonObject.getString("iconName"),
                        position = jsonObject.getInt("position"),
                        isDefault = jsonObject.getBoolean("isDefault"),
                        createdAt = jsonObject.getLong("createdAt")
                    )
                )
            }
            _folders.value = foldersList
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Load screenshots from SharedPreferences
     */
    private fun loadScreenshots() {
        val screenshotsJson = sharedPreferences.getString(KEY_SCREENSHOTS, null) ?: return
        val screenshotsList = mutableListOf<SimpleScreenshot>()

        try {
            val jsonArray = JSONArray(screenshotsJson)
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                screenshotsList.add(
                    SimpleScreenshot(
                        id = jsonObject.getLong("id"),
                        filePath = jsonObject.getString("filePath"),
                        thumbnailPath = jsonObject.getString("thumbnailPath"),
                        folderId = jsonObject.getLong("folderId"),
                        originalTimestamp = jsonObject.getLong("originalTimestamp"),
                        savedTimestamp = jsonObject.getLong("savedTimestamp")
                    )
                )
            }
            _screenshots.value = screenshotsList
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Save folders to SharedPreferences
     */
    private fun saveFolders() {
        val jsonArray = JSONArray()
        _folders.value.forEach { folder ->
            val jsonObject = JSONObject().apply {
                put("id", folder.id)
                put("name", folder.name)
                put("iconName", folder.iconName)
                put("position", folder.position)
                put("isDefault", folder.isDefault)
                put("createdAt", folder.createdAt)
            }
            jsonArray.put(jsonObject)
        }

        sharedPreferences.edit().putString(KEY_FOLDERS, jsonArray.toString()).apply()
    }

    /**
     * Save screenshots to SharedPreferences
     */
    private fun saveScreenshots() {
        val jsonArray = JSONArray()
        _screenshots.value.forEach { screenshot ->
            val jsonObject = JSONObject().apply {
                put("id", screenshot.id)
                put("filePath", screenshot.filePath)
                put("thumbnailPath", screenshot.thumbnailPath)
                put("folderId", screenshot.folderId)
                put("originalTimestamp", screenshot.originalTimestamp)
                put("savedTimestamp", screenshot.savedTimestamp)
            }
            jsonArray.put(jsonObject)
        }

        sharedPreferences.edit().putString(KEY_SCREENSHOTS, jsonArray.toString()).apply()
    }

    /**
     * Add a new folder
     */
    fun addFolder(folder: SimpleFolder) {
        val currentFolders = _folders.value.toMutableList()
        currentFolders.add(folder)
        _folders.value = currentFolders
        saveFolders()
    }

    /**
     * Update an existing folder
     */
    fun updateFolder(folder: SimpleFolder) {
        val currentFolders = _folders.value.toMutableList()
        val index = currentFolders.indexOfFirst { it.id == folder.id }
        if (index != -1) {
            currentFolders[index] = folder
            _folders.value = currentFolders
            saveFolders()
        }
    }

    /**
     * Delete a folder
     */
    fun deleteFolder(folderId: Long) {
        val currentFolders = _folders.value.toMutableList()
        val folder = currentFolders.find { it.id == folderId }

        if (folder != null && folder.isRemovable) {
            // Ensure the folder directory exists (for reference)
            getFolderDirectory(folder.name)

            // Remove the folder from the list
            currentFolders.remove(folder)
            _folders.value = currentFolders
            saveFolders()

            // Also delete all screenshots in this folder
            deleteScreenshotsByFolder(folderId)

            // Note: We don't delete the physical folder to avoid data loss
            // Users can still access their screenshots in the file system
        }
    }

    /**
     * Get a folder by ID
     */
    fun getFolder(folderId: Long): SimpleFolder? {
        return _folders.value.find { it.id == folderId }
    }

    /**
     * Get a folder by name
     */
    fun getFolderByName(name: String): SimpleFolder? {
        return _folders.value.find { it.name == name }
    }

    /**
     * Get default folders
     */
    fun getDefaultFolders(): List<SimpleFolder> {
        return _folders.value.filter { it.isDefault }
    }

    /**
     * Add a new screenshot
     */
    fun addScreenshot(screenshot: SimpleScreenshot) {
        val currentScreenshots = _screenshots.value.toMutableList()
        currentScreenshots.add(screenshot)
        _screenshots.value = currentScreenshots
        saveScreenshots()
    }

    /**
     * Update an existing screenshot
     */
    fun updateScreenshot(screenshot: SimpleScreenshot) {
        val currentScreenshots = _screenshots.value.toMutableList()
        val index = currentScreenshots.indexOfFirst { it.id == screenshot.id }
        if (index != -1) {
            currentScreenshots[index] = screenshot
            _screenshots.value = currentScreenshots
            saveScreenshots()
        }
    }

    /**
     * Delete a screenshot
     */
    fun deleteScreenshot(screenshotId: Long) {
        val currentScreenshots = _screenshots.value.toMutableList()
        val screenshot = currentScreenshots.find { it.id == screenshotId }

        if (screenshot != null) {
            currentScreenshots.remove(screenshot)
            _screenshots.value = currentScreenshots
            saveScreenshots()
        }
    }

    /**
     * Delete all screenshots in a folder
     */
    fun deleteScreenshotsByFolder(folderId: Long) {
        val currentScreenshots = _screenshots.value.toMutableList()
        val newScreenshots = currentScreenshots.filter { it.folderId != folderId }

        if (newScreenshots.size != currentScreenshots.size) {
            _screenshots.value = newScreenshots
            saveScreenshots()
        }
    }

    /**
     * Get screenshots for a specific folder
     */
    fun getScreenshotsByFolder(folderId: Long): List<SimpleScreenshot> {
        return _screenshots.value.filter { it.folderId == folderId }
    }

    /**
     * Move a screenshot to a different folder
     */
    fun moveScreenshotToFolder(screenshotId: Long, newFolderId: Long) {
        val currentScreenshots = _screenshots.value.toMutableList()
        val index = currentScreenshots.indexOfFirst { it.id == screenshotId }

        if (index != -1) {
            val screenshot = currentScreenshots[index]
            currentScreenshots[index] = screenshot.copy(folderId = newFolderId)
            _screenshots.value = currentScreenshots
            saveScreenshots()
        }
    }

    /**
     * Initialize default folders if none exist
     */
    fun initializeDefaultFolders() {
        if (_folders.value.isEmpty()) {
            val defaultFolders = listOf(
                SimpleFolder(id = 1, name = "Posts", iconName = "ic_posts", position = 0, isDefault = true, isEditable = true, isRemovable = true),
                SimpleFolder(id = 2, name = "Docs", iconName = "ic_images", position = 1, isDefault = true, isEditable = true, isRemovable = true),
                SimpleFolder(id = 3, name = "Chats", iconName = "ic_quotes", position = 2, isDefault = true, isEditable = true, isRemovable = true),
                SimpleFolder(id = 4, name = "Payments", iconName = "ic_tricks", position = 3, isDefault = true, isEditable = true, isRemovable = true),
                SimpleFolder(id = 5, name = "Memes", iconName = "ic_images", position = 4, isDefault = true, isEditable = true, isRemovable = true),
                SimpleFolder(id = 6, name = "Tweets", iconName = "ic_posts", position = 5, isDefault = true, isEditable = true, isRemovable = true),
                SimpleFolder(id = 7, name = "Quotes", iconName = "ic_quotes", position = 6, isDefault = true, isEditable = true, isRemovable = true),
                SimpleFolder(id = 8, name = "Messages", iconName = "ic_tricks", position = 7, isDefault = true, isEditable = true, isRemovable = true)
            )

            _folders.value = defaultFolders
            saveFolders()
        }

        // Always ensure physical folders exist for all folders (including defaults)
        // This ensures folders are created even if the app is restarted
        _folders.value.forEach { folder ->
            val folderDir = getFolderDirectory(folder.name)
            if (!folderDir.exists()) {
                folderDir.mkdirs()
            }
        }
    }

    /**
     * Generate a new unique ID for a folder
     */
    fun generateFolderId(): Long {
        val maxId = _folders.value.maxOfOrNull { it.id } ?: 0
        return maxId + 1
    }

    /**
     * Generate a new unique ID for a screenshot
     */
    fun generateScreenshotId(): Long {
        val maxId = _screenshots.value.maxOfOrNull { it.id } ?: 0
        return maxId + 1
    }

    /**
     * Theme mode enum
     */
    enum class ThemeMode {
        SYSTEM, LIGHT, DARK
    }

    /**
     * Get the current theme mode
     */
    fun getThemeMode(): ThemeMode {
        val themeModeString = sharedPreferences.getString(KEY_THEME_MODE, ThemeMode.SYSTEM.name)
        return try {
            ThemeMode.valueOf(themeModeString ?: ThemeMode.SYSTEM.name)
        } catch (e: Exception) {
            ThemeMode.SYSTEM
        }
    }

    /**
     * Set the theme mode
     */
    fun setThemeMode(themeMode: ThemeMode) {
        sharedPreferences.edit().putString(KEY_THEME_MODE, themeMode.name).apply()
    }

    /**
     * Check if onboarding has been completed
     */
    fun isOnboardingCompleted(): Boolean {
        return sharedPreferences.getBoolean(KEY_ONBOARDING_COMPLETED, false)
    }

    /**
     * Set onboarding completed status
     */
    fun setOnboardingCompleted(completed: Boolean) {
        sharedPreferences.edit().putBoolean(KEY_ONBOARDING_COMPLETED, completed).apply()
    }

    companion object {
        private const val PREFS_NAME = "pixelflow_prefs"
        private const val KEY_FOLDERS = "folders"
        private const val KEY_SCREENSHOTS = "screenshots"
        private const val KEY_THEME_MODE = "theme_mode"
        private const val KEY_ONBOARDING_COMPLETED = "onboarding_completed"
    }
}
