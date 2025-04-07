package com.aks_labs.pixelflow.ui.components.compose

import android.content.Context
import android.view.View
import android.widget.FrameLayout
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView

/**
 * Factory for creating ComposeView instances that can be used in a Service context.
 */
class ComposeViewFactory(private val context: Context) {

    /**
     * Creates a ComposeView with the given content.
     * For use in a Service, we wrap the ComposeView in a FrameLayout to avoid lifecycle issues.
     */
    fun createComposeView(content: @Composable () -> Unit): View {
        // Create a FrameLayout to hold the ComposeView
        val frameLayout = FrameLayout(context)

        // Create the ComposeView
        val composeView = ComposeView(context)

        // Set the content with the proper theme
        composeView.setContent {
            MaterialTheme {
                content()
            }
        }

        // Add the ComposeView to the FrameLayout
        frameLayout.addView(composeView)

        return frameLayout
    }
}
