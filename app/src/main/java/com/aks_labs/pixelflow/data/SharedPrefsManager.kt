package com.aks_labs.pixelflow.data

import android.content.ContentUris
import android.content.Context
import android.content.SharedPreferences
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
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
class SharedPrefsManager(private val context: Context) {

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

    // Listener for SharedPreferences changes
    private val prefsListener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
        when (key) {
            KEY_FOLDERS -> loadFolders()
            KEY_SCREENSHOTS -> loadScreenshots()
        }
    }

    init {
        // Create app directory
        appDirectory = createAppDirectory()

        // Load initial data
        loadFolders()
        loadScreenshots()
        
        // Register listener for external updates (e.g. from Service)
        sharedPreferences.registerOnSharedPreferenceChangeListener(prefsListener)
    }

    /**
     * Force refresh data from SharedPreferences
     */
    fun refresh() {
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
            val oldFolder = currentFolders[index]
            val oldFolderName = oldFolder.name
            val newFolderName = folder.name
            
            // Update folder in memory
            currentFolders[index] = folder
            _folders.value = currentFolders
            saveFolders()
            
            // If folder name changed, rename the physical directory
            if (oldFolderName != newFolderName) {
                try {
                    val oldDir = File(appDirectory, oldFolderName)
                    val newDir = File(appDirectory, newFolderName)
                    
                    if (oldDir.exists()) {
                        val renamed = oldDir.renameTo(newDir)
                        if (renamed) {
                            Log.d("SharedPrefsManager", "Successfully renamed folder: $oldFolderName -> $newFolderName")
                            
                            // Scan the new directory to update MediaStore
                            android.media.MediaScannerConnection.scanFile(
                                context,
                                arrayOf(newDir.absolutePath),
                                null
                            ) { path, uri ->
                                Log.d("SharedPrefsManager", "Media scan completed for renamed folder: $path")
                            }
                        } else {
                            Log.w("SharedPrefsManager", "Failed to rename folder: $oldFolderName -> $newFolderName")
                        }
                    } else {
                        Log.d("SharedPrefsManager", "Old folder doesn't exist, creating new one: $newFolderName")
                        // Create new folder if old one doesn't exist
                        getFolderDirectory(newFolderName)
                    }
                } catch (e: Exception) {
                    Log.e("SharedPrefsManager", "Error renaming folder: $oldFolderName -> $newFolderName", e)
                }
            }
        }
    }

    /**
     * Delete a folder physically from storage along with all its contents
     */
    fun deleteFolderPhysically(folderId: Long) {
        val currentFolders = _folders.value.toMutableList()
        val folder = currentFolders.find { it.id == folderId }

        if (folder != null) {
            // 1. Get all screenshots in this folder
            val folderScreenshots = getScreenshotsByFolder(folderId)
            
            // 2. Delete each screenshot physically and from MediaStore
            folderScreenshots.forEach { screenshot ->
                deleteScreenshot(screenshot.id)
            }
            
            // For filesystem-scanned folders (like Unsorted or Custom folders)
            // We should also scan and delete images that might not be in the metadata
            val diskImages = getImagesFromFolder(folder.name)
            diskImages.forEach { screenshot ->
                deleteScreenshotByPath(screenshot.filePath)
            }

            // 3. Delete the physical directory
            try {
                val folderDir = File(appDirectory, folder.name)
                if (folderDir.exists() && folderDir.isDirectory) {
                    val deleted = folderDir.deleteRecursively()
                    if (deleted) {
                        Log.d("SharedPrefsManager", "Physically deleted folder directory: ${folder.name}")
                    } else {
                        Log.w("SharedPrefsManager", "Failed to physically delete folder directory: ${folder.name}")
                    }
                }
            } catch (e: Exception) {
                Log.e("SharedPrefsManager", "Error deleting physical folder: ${folder.name}", e)
            }

            // 4. Remove the folder from the list
            currentFolders.remove(folder)
            _folders.value = currentFolders
            saveFolders()
            
            // 5. Notify MediaStore to scan app directory to reflect changes
            android.media.MediaScannerConnection.scanFile(
                context,
                arrayOf(appDirectory.absolutePath),
                null
            ) { path, uri ->
                Log.d("SharedPrefsManager", "Media scan completed for app directory after folder deletion: $path")
            }
        }
    }

    /**
     * Delete a folder (soft delete - keep physical files)
     */
    fun deleteFolder(folderId: Long) {
        val currentFolders = _folders.value.toMutableList()
        val folder = currentFolders.find { it.id == folderId }

        if (folder != null && folder.isRemovable) {
            // Remove the folder from the list
            currentFolders.remove(folder)
            _folders.value = currentFolders
            saveFolders()

            // Also delete all screenshots in this folder from metadata
            deleteScreenshotsByFolder(folderId)
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
     * Delete a screenshot from both memory and storage
     */
    fun deleteScreenshot(screenshotId: Long) {
        val screenshot = _screenshots.value.find { it.id == screenshotId }
        
        if (screenshot != null) {
            // 1. Remove from memory list immediately for instant UI update
            val currentScreenshots = _screenshots.value.toMutableList()
            currentScreenshots.remove(screenshot)
            _screenshots.value = currentScreenshots
            saveScreenshots()
            
            // 2. Delete from MediaStore - first verify the ID exists
            try {
                val uri = ContentUris.withAppendedId(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    screenshotId
                )
                
                // Query to verify entry exists before deletion
                val projection = arrayOf(MediaStore.Images.Media._ID)
                val cursor = context.contentResolver.query(uri, projection, null, null, null)
                
                if (cursor != null && cursor.count > 0) {
                    cursor.close()
                    // Entry exists, proceed with deletion
                    val deleted = context.contentResolver.delete(uri, null, null)
                    if (deleted > 0) {
                        Log.d("SharedPrefsManager", "MediaStore delete successful for ID $screenshotId: $deleted rows")
                    } else {
                        Log.w("SharedPrefsManager", "MediaStore delete returned 0 rows for ID $screenshotId")
                    }
                } else {
                    cursor?.close()
                    Log.d("SharedPrefsManager", "MediaStore entry doesn't exist for ID $screenshotId, skipping MediaStore deletion")
                }
            } catch (e: Exception) {
                Log.e("SharedPrefsManager", "MediaStore delete failed for ID $screenshotId", e)
            }
            
            // 3. Delete physical file as fallback/additional safety
            try {
                val file = File(screenshot.filePath)
                if (file.exists()) {
                    val fileDeleted = file.delete()
                    if (fileDeleted) {
                        Log.d("SharedPrefsManager", "Physical file deleted: ${screenshot.filePath}")
                        
                        // 4. Notify MediaStore to scan and remove entry if it still exists
                        android.media.MediaScannerConnection.scanFile(
                            context,
                            arrayOf(screenshot.filePath),
                            null
                        ) { path, uri ->
                            Log.d("SharedPrefsManager", "Media scan completed for deleted file: $path")
                        }
                    } else {
                        Log.w("SharedPrefsManager", "Failed to delete physical file: ${screenshot.filePath}")
                    }
                } else {
                    Log.d("SharedPrefsManager", "Physical file already deleted or doesn't exist: ${screenshot.filePath}")
                }
                
                // Also try to delete thumbnail if it exists and is different from main file
                if (screenshot.thumbnailPath != screenshot.filePath) {
                    val thumbnailFile = File(screenshot.thumbnailPath)
                    if (thumbnailFile.exists()) {
                        thumbnailFile.delete()
                        Log.d("SharedPrefsManager", "Thumbnail deleted: ${screenshot.thumbnailPath}")
                    }
                }
            } catch (e: Exception) {
                Log.e("SharedPrefsManager", "Error deleting physical file: ${screenshot.filePath}", e)
            }
        } else {
            Log.w("SharedPrefsManager", "Screenshot with ID $screenshotId not found in memory")
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
     * Delete a screenshot by file path (for screenshots loaded from filesystem, not in StateFlow)
     */
    fun deleteScreenshotByPath(filePath: String) {
        try {
            // 1. First, try to delete from MediaStore using content resolver
            // This is the preferred way for images in public directories
            val projection = arrayOf(MediaStore.Images.Media._ID)
            val selection = "${MediaStore.Images.Media.DATA} = ?"
            val selectionArgs = arrayOf(filePath)
            val queryUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            
            val cursor = context.contentResolver.query(queryUri, projection, selection, selectionArgs, null)
            var mediaStoreDeleted = false
            
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    val id = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID))
                    val deleteUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)
                    val rowsDeleted = context.contentResolver.delete(deleteUri, null, null)
                    if (rowsDeleted > 0) {
                        Log.d("SharedPrefsManager", "MediaStore deletion successful for path: $filePath")
                        mediaStoreDeleted = true
                    }
                }
                cursor.close()
            }
            
            // 2. Fallback: physical file deletion if MediaStore failed or as an extra safety measure
            val file = File(filePath)
            if (file.exists()) {
                val fileDeleted = file.delete()
                if (fileDeleted) {
                    Log.d("SharedPrefsManager", "Physical file deleted by path: $filePath")
                    
                    // Notify MediaStore to scan and remove entry if we haven't already deleted it from there
                    if (!mediaStoreDeleted) {
                        android.media.MediaScannerConnection.scanFile(
                            context,
                            arrayOf(filePath),
                            null
                        ) { path, uri ->
                            Log.d("SharedPrefsManager", "Media scan completed for deleted file: $path")
                        }
                    }
                } else {
                    Log.w("SharedPrefsManager", "Failed to delete physical file by path: $filePath")
                }
            } else {
                Log.d("SharedPrefsManager", "File doesn't exist at path: $filePath (possibly already deleted by MediaStore)")
            }
        } catch (e: Exception) {
            Log.e("SharedPrefsManager", "Error deleting file by path: $filePath", e)
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
     * Create 'Unsorted' folder if it doesn't exist
     */
    fun createUnsortedFolderIfMissing() {
        val unsortedFolder = getFolderByName("Unsorted")
        if (unsortedFolder == null) {
            val newFolder = SimpleFolder(
                id = generateFolderId(),
                name = "Unsorted",
                iconName = "ic_images",
                position = -1,
                isDefault = false,
                isEditable = false,
                isRemovable = false
            )
            addFolder(newFolder)
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

    /**
     * Get screenshots from system screenshot directories
     * Scans both Pictures/Screenshot and Pictures/Screenshots
     */
    private fun getSystemScreenshots(): List<SimpleScreenshot> {
        val screenshots = mutableListOf<SimpleScreenshot>()
        val picturesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        
        // Common screenshot directory names
        val screenshotDirNames = listOf("Screenshot", "Screenshots")
        
        for (dirName in screenshotDirNames) {
            val screenshotDir = File(picturesDir, dirName)
            
            if (!screenshotDir.exists() || !screenshotDir.isDirectory) {
                continue
            }
            
            val imageFiles = screenshotDir.listFiles { file ->
                file.isFile && file.exists() && (file.extension.lowercase() in listOf("jpg", "jpeg", "png", "gif", "webp"))
            } ?: emptyArray()
            
            imageFiles.forEach { file ->
                val screenshot = SimpleScreenshot(
                    id = file.absolutePath.hashCode().toLong(),
                    filePath = file.absolutePath,
                    thumbnailPath = file.absolutePath,
                    folderId = "Unsorted".hashCode().toLong(),
                    originalTimestamp = file.lastModified(),
                    savedTimestamp = file.lastModified()
                )
                screenshots.add(screenshot)
            }
        }
        
        Log.d("SharedPrefsManager", "Found ${screenshots.size} screenshots in system directories")
        return screenshots
    }
    
    /**
     * Get images from a folder by scanning the filesystem
     * For "Unsorted" folder, also includes system screenshot directories
     */
    fun getImagesFromFolder(folderName: String): List<SimpleScreenshot> {
        val images = mutableListOf<SimpleScreenshot>()
        
        // For "Unsorted" folder, load from system screenshot directories
        if (folderName.equals("Unsorted", ignoreCase = true)) {
            // Add system screenshots from Pictures/Screenshot and Pictures/Screenshots
            images.addAll(getSystemScreenshots())
        }
        
        // Also check app's own folder
        val folderDir = File(appDirectory, folderName)
        if (folderDir.exists() && folderDir.isDirectory) {
            val imageFiles = folderDir.listFiles { file ->
                file.isFile && file.exists() && (file.extension.lowercase() in listOf("jpg", "jpeg", "png", "gif", "webp"))
            } ?: emptyArray()
            
            imageFiles.forEach { file ->
                val screenshot = SimpleScreenshot(
                    id = file.absolutePath.hashCode().toLong(),
                    filePath = file.absolutePath,
                    thumbnailPath = file.absolutePath,
                    folderId = folderName.hashCode().toLong(),
                    originalTimestamp = file.lastModified(),
                    savedTimestamp = file.lastModified()
                )
                images.add(screenshot)
            }
        }
        
        // Remove duplicates based on file path and sort by most recent
        val uniqueImages = images.distinctBy { it.filePath }
            .sortedByDescending { it.savedTimestamp }
        
        Log.d("SharedPrefsManager", "Loaded ${uniqueImages.size} images from folder: $folderName")
        return uniqueImages
    }

    /**
     * Get all folder names from the PixelFlow directory by scanning filesystem
     */
    fun getFolderNamesFromDisk(): List<String> {
        if (!appDirectory.exists()) return emptyList()
        
        val folders = mutableListOf<String>()
        val contents = appDirectory.listFiles { file ->
            file.isDirectory
        } ?: emptyArray()
        
        contents.forEach { folder ->
            folders.add(folder.name)
        }
        
        return folders.sorted()
    }

    companion object {
        private const val PREFS_NAME = "pixelflow_prefs"
        private const val KEY_FOLDERS = "folders"
        private const val KEY_SCREENSHOTS = "screenshots"
        private const val KEY_THEME_MODE = "theme_mode"
        private const val KEY_ONBOARDING_COMPLETED = "onboarding_completed"
    }
}
