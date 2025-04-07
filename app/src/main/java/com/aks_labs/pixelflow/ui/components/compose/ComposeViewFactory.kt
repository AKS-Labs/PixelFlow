package com.aks_labs.pixelflow.ui.components.compose

import android.content.Context
import android.view.View
import android.widget.FrameLayout
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy

/**
 * Factory for creating ComposeView instances that can be used in a Service context.
 */
object ComposeViewFactory {

    /**
     * Creates a ComposeView with the necessary configuration for use in a Service.
     *
     * @param context The context to create the view with
     * @param content The composable content to display
     * @return A properly configured ComposeView wrapped in a FrameLayout
     */
    fun createComposeView(context: Context, content: @Composable () -> Unit): View {
        // Create a FrameLayout to hold the ComposeView
        val frameLayout = FrameLayout(context)

        // Create the ComposeView
        val composeView = ComposeView(context).apply {
            // Set the composition strategy to dispose when detached
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnDetachedFromWindow)

            // Set the content
            setContent {
                MaterialTheme {
                    content()
                }
            }
        }

        // Add the ComposeView to the FrameLayout
        frameLayout.addView(composeView)

        return frameLayout
    }
}
