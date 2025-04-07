package com.aks_labs.pixelflow.ui.components.compose

import android.content.Context
import android.view.View
import android.widget.FrameLayout
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.savedstate.SavedStateRegistry
import androidx.savedstate.SavedStateRegistryController
import androidx.savedstate.SavedStateRegistryOwner

/**
 * A custom ComposeView implementation for use in a Service context.
 * This class provides a proper lifecycle for Compose in a Service.
 */
class AndroidComposeView private constructor(context: Context) {

    private val lifecycleOwner = ServiceLifecycleOwner()
    private val frameLayout = FrameLayout(context)
    private val composeView = ComposeView(context)

    init {
        // Configure the ComposeView
        composeView.setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnDetachedFromWindow)

        // Add the ComposeView to the FrameLayout
        frameLayout.addView(composeView)

        // Set up lifecycle handling
        frameLayout.addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
            override fun onViewAttachedToWindow(v: View) {
                lifecycleOwner.markState(Lifecycle.State.RESUMED)
            }

            override fun onViewDetachedFromWindow(v: View) {
                lifecycleOwner.markState(Lifecycle.State.DESTROYED)
            }
        })
    }

    /**
     * Sets the content of the ComposeView.
     */
    fun setContent(content: @Composable () -> Unit) {
        composeView.setContent {
            // Provide the MaterialTheme
            MaterialTheme {
                content()
            }
        }
    }

    /**
     * Gets the root view.
     */
    fun getView(): View {
        return frameLayout
    }

    /**
     * A LifecycleOwner implementation for use in a Service context.
     */
    private class ServiceLifecycleOwner : LifecycleOwner, ViewModelStoreOwner, SavedStateRegistryOwner {
        private val lifecycleRegistry = LifecycleRegistry(this)
        private val vmStore = ViewModelStore()
        private val savedStateRegistryController = SavedStateRegistryController.create(this)

        init {
            // Initialize the SavedState registry
            savedStateRegistryController.performAttach()
            savedStateRegistryController.performRestore(null)

            // Set the lifecycle to CREATED state initially
            lifecycleRegistry.currentState = Lifecycle.State.CREATED
        }

        /**
         * Updates the lifecycle state.
         */
        fun markState(state: Lifecycle.State) {
            lifecycleRegistry.currentState = state
        }

        override val lifecycle: Lifecycle
            get() = lifecycleRegistry

        override val viewModelStore: ViewModelStore
            get() = vmStore

        override val savedStateRegistry: SavedStateRegistry
            get() = savedStateRegistryController.savedStateRegistry
    }

    companion object {
        /**
         * Creates a new AndroidComposeView with the given content.
         */
        fun create(context: Context, content: @Composable () -> Unit): View {
            val androidComposeView = AndroidComposeView(context)
            androidComposeView.setContent(content)
            return androidComposeView.getView()
        }
    }
}
