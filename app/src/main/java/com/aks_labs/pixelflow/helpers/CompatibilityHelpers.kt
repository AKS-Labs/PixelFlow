package com.akslabs.pixelscreenshots.helpers

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.akslabs.pixelscreenshots.helpers.Screens

// Mock classes to match user's FolderScreen requirements

data class BottomBarTab(val title: String)

object DefaultTabs {
    object TabTypes {
        val secure = BottomBarTab("Secure")
    }
}

// Extension function used in FolderScreen
fun Any.signature(): String = this.toString()

fun NavController.navigate(screen: Screens.Screen) {
    this.navigate(screen.route)
}
