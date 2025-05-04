package com.aks_labs.pixelflow

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.aks_labs.pixelflow.data.SharedPrefsManager
import com.aks_labs.pixelflow.receivers.ScreenshotBroadcastReceiver
import com.aks_labs.pixelflow.ui.viewmodels.MainViewModel

/**
 * Application class for PixelFlow app
 */
class PixelFlowApplication : Application() {

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
        private lateinit var instance: PixelFlowApplication

        fun getInstance(): PixelFlowApplication = instance
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

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
val Context.pixelFlowApp: PixelFlowApplication
    get() = applicationContext as PixelFlowApplication
