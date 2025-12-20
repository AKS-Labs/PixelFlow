package com.akslabs.pixelscreenshots

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.akslabs.pixelscreenshots.data.SharedPrefsManager
import com.akslabs.pixelscreenshots.receivers.ScreenshotBroadcastReceiver
import com.akslabs.pixelscreenshots.ui.viewmodels.MainViewModel

/**
 * Application class for PixelScreenshots app
 */
class PixelScreenshotsApplication : Application() {

    // SharedPreferences manager
    val sharedPrefsManager: SharedPrefsManager by lazy {
        SharedPrefsManager(this).apply {
            initializeDefaultFolders()
        }
    }

    // DataStore for app preferences
    val dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    // MainViewModel reference
    var mainViewModel: MainViewModel? = null

    // Screenshot broadcast receiver
    private lateinit var screenshotReceiver: ScreenshotBroadcastReceiver

    companion object {
        private lateinit var instance: PixelScreenshotsApplication

        fun getInstance(): PixelScreenshotsApplication = instance
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        // Initialize SharedPrefsManager and create default folders
        sharedPrefsManager.apply {
            initializeDefaultFolders()
            createUnsortedFolderIfMissing()
        }

        // Register the screenshot broadcast receiver
        screenshotReceiver = ScreenshotBroadcastReceiver.register(this)
    }

    override fun onTerminate() {
        super.onTerminate()

        // Unregister the screenshot broadcast receiver
        try {
            unregisterReceiver(screenshotReceiver)
        } catch (e: Exception) {
            // Receiver might not be registered
        }
    }
}

// Extension function to get the application instance from any context
val Context.pixelFlowApp: PixelScreenshotsApplication
    get() = applicationContext as PixelScreenshotsApplication
