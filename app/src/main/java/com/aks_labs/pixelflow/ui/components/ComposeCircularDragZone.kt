package com.aks_labs.pixelflow.ui.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.ComposeView
import androidx.constraintlayout.widget.ConstraintLayout
import com.aks_labs.pixelflow.R
import com.aks_labs.pixelflow.data.models.SimpleFolder
import com.aks_labs.pixelflow.ui.components.compose.CircularDragZone

/**
 * A custom View that wraps the CircularDragZone Composable for use in traditional View hierarchies.
 */
class ComposeCircularDragZone @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    
    // State for the Composable
    private val folders = mutableStateOf<List<SimpleFolder>>(emptyList())
    private val highlightedIndex = mutableStateOf(-1)
    
    // Callback for folder selection
    private var onFolderSelectedListener: ((Long) -> Unit)? = null
    
    // ComposeView for hosting the Composable
    private val composeView: ComposeView
    
    init {
        // Inflate the layout
        val view = LayoutInflater.from(context).inflate(R.layout.compose_view_container, this, true)
        
        // Get the ComposeView
        composeView = view.findViewById(R.id.compose_view)
        
        // Set the content
        composeView.setContent {
            androidx.compose.material3.MaterialTheme {
                CircularDragZone(
                    folders = folders.value,
                    highlightedIndex = highlightedIndex.value,
                    onFolderSelected = { folderId ->
                        onFolderSelectedListener?.invoke(folderId)
                    }
                )
            }
        }
    }
    
    /**
     * Sets the folders to display.
     */
    fun setFolders(newFolders: List<SimpleFolder>) {
        folders.value = newFolders
    }
    
    /**
     * Sets the highlighted zone index.
     */
    fun setHighlightedIndex(index: Int) {
        highlightedIndex.value = index
    }
    
    /**
     * Sets the listener for folder selection.
     */
    fun setOnFolderSelectedListener(listener: (Long) -> Unit) {
        onFolderSelectedListener = listener
    }
    
    /**
     * Highlights the zone at the given coordinates.
     */
    fun highlightZoneAt(x: Float, y: Float) {
        // Calculate which zone is under the coordinates
        val index = getZoneIndexAt(x, y)
        setHighlightedIndex(index)
    }
    
    /**
     * Gets the zone index at the given coordinates.
     */
    fun getZoneIndexAt(x: Float, y: Float): Int {
        // This is a simplified version that delegates to the service's calculation
        // In a real implementation, we would calculate this here
        return -1
    }
}
