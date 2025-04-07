package com.aks_labs.pixelflow.ui.components.compose

import android.graphics.Bitmap
import androidx.compose.ui.geometry.Offset
import com.aks_labs.pixelflow.data.models.SimpleFolder

/**
 * State for the floating bubble.
 */
data class BubbleState(
    val bitmap: Bitmap? = null,
    val isVisible: Boolean = false,
    val isDragging: Boolean = false,
    val position: Offset = Offset.Zero
)

/**
 * State for the drag zones.
 */
data class DragZoneState(
    val folders: List<SimpleFolder> = emptyList(),
    val isVisible: Boolean = false,
    val highlightedIndex: Int = -1
)
