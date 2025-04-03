package com.aks_labs.pixelflow.ui.viewmodels

import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.aks_labs.pixelflow.data.models.SimpleFolder
import com.aks_labs.pixelflow.data.models.SimpleScreenshot
import com.aks_labs.pixelflow.pixelFlowApp
import com.aks_labs.pixelflow.services.FloatingBubbleService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
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

    // Bubble enabled preference
    val isBubbleEnabled = application.pixelFlowApp.dataStore.data
        .map { preferences ->
            preferences[BUBBLE_ENABLED_KEY] ?: true // Default to true
        }
        .stateIn(viewModelScope, SharingStarted.Eagerly, true)

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
        val intent = Intent(context, FloatingBubbleService::class.java)
        context.startService(intent)
    }

    /**
     * Stop the floating bubble service
     */
    fun stopFloatingBubbleService(context: Context) {
        val intent = Intent(context, FloatingBubbleService::class.java)
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
}
