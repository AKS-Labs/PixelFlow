package com.aks_labs.pixelflow.ui.components.compose

import android.content.Context
import android.view.View
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry

/**
 * A utility for creating ComposeViews that can be used in a Service context.
 */
object ServiceComposeView {

    /**
     * Creates a new ComposeView with the given content.
     * This view is configured for use in a Service context with a custom lifecycle owner.
     */
    fun create(context: Context, content: @Composable () -> Unit): View {
        // Create a standard ComposeView
        val composeView = ComposeView(context)

        // Configure the ComposeView with a strategy that doesn't depend on lifecycle
        composeView.setViewCompositionStrategy(
            ViewCompositionStrategy.DisposeOnDetachedFromWindow
        )

        // Set the content
        composeView.setContent {
            MaterialTheme {
                content()
            }
        }

        return composeView
    }
}

/**
 * A custom LifecycleOwner implementation for use in Services.
 */
class ServiceLifecycleOwner : LifecycleOwner {
    private val lifecycleRegistry: LifecycleRegistry = LifecycleRegistry(this)

    override val lifecycle: Lifecycle
        get() = lifecycleRegistry

    fun handleLifecycleEvent(event: Lifecycle.Event) {
        lifecycleRegistry.handleLifecycleEvent(event)
    }

    fun performRestore(savedState: Any?) {
        lifecycleRegistry.currentState = Lifecycle.State.INITIALIZED
    }
}
