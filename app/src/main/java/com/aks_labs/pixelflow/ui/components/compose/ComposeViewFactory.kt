package com.aks_labs.pixelflow.ui.components.compose

import android.content.Context
import android.view.View
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

/**
 * Factory for creating ComposeView instances that can be used in a Service context.
 */
object ComposeViewFactory {

    /**
     * Creates a ComposeView with the necessary configuration for use in a Service.
     *
     * @param context The context to create the view with
     * @param content The composable content to display
     * @return A properly configured AndroidComposeView
     */
    fun createComposeView(context: Context, content: @Composable () -> Unit): View {
        return AndroidComposeView.create(context) {
            MaterialTheme {
                content()
            }
        }
    }
}
