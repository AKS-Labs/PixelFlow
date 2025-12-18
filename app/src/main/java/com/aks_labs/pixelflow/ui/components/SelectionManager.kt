package com.aks_labs.pixelflow.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.aks_labs.pixelflow.data.models.SimpleScreenshot

/**
 * A class to manage the selection state of screenshots.
 */
class SelectionManager {
    // Whether selection mode is active
    var selectionMode by mutableStateOf(false)
        private set

    // Whether drag selection is active
    var isDragSelecting by mutableStateOf(false)
        private set

    // The last screenshot ID that was selected during drag
    var lastDragSelectedId by mutableStateOf<Long?>(null)
        private set

    // Map of selected screenshots (ID -> Screenshot)
    private val _selectedItems = mutableStateListOf<SimpleScreenshot>()
    val selectedItems: List<SimpleScreenshot> get() = _selectedItems

    /**
     * Toggle selection mode.
     */
    fun toggleSelectionMode() {
        selectionMode = !selectionMode
        if (!selectionMode) {
            clearSelection()
            stopDragSelection()
        }
    }

    /**
     * Start drag selection.
     */
    fun startDragSelection() {
        if (!selectionMode) {
            selectionMode = true
        }
        isDragSelecting = true
    }

    /**
     * Stop drag selection.
     */
    fun stopDragSelection() {
        isDragSelecting = false
        lastDragSelectedId = null
    }

    /**
     * Toggle selection of a screenshot.
     *
     * @param screenshot The screenshot to toggle
     * @return True if the screenshot is now selected, false otherwise
     */
    fun toggleSelection(screenshot: SimpleScreenshot): Boolean {
        val existing = _selectedItems.find { it.id == screenshot.id }
        return if (existing != null) {
            _selectedItems.remove(existing)
            if (_selectedItems.isEmpty()) {
                selectionMode = false
            }
            false
        } else {
            _selectedItems.add(screenshot)
            true
        }
    }

    /**
     * Select or deselect a screenshot during drag selection based on the desired state.
     *
     * @param screenshot The screenshot to select/deselect
     * @param shouldSelect True to select, false to deselect
     */
    fun setSelectionDuringDrag(screenshot: SimpleScreenshot, shouldSelect: Boolean) {
        if (!isDragSelecting) return
        
        val isCurrentlySelected = _selectedItems.any { it.id == screenshot.id }
        
        if (shouldSelect && !isCurrentlySelected) {
            _selectedItems.add(screenshot)
            lastDragSelectedId = screenshot.id
        } else if (!shouldSelect && isCurrentlySelected) {
            _selectedItems.removeAll { it.id == screenshot.id }
            lastDragSelectedId = screenshot.id
        }
    }

    /**
     * Select a screenshot during drag selection (legacy method).
     *
     * @param screenshot The screenshot to select
     */
    fun selectDuringDrag(screenshot: SimpleScreenshot) {
        if (isDragSelecting && _selectedItems.none { it.id == screenshot.id }) {
            _selectedItems.add(screenshot)
            lastDragSelectedId = screenshot.id
        }
    }

    /**
     * Check if a screenshot is selected.
     *
     * @param screenshot The screenshot to check
     * @return True if the screenshot is selected, false otherwise
     */
    fun isSelected(screenshot: SimpleScreenshot): Boolean {
        return _selectedItems.any { it.id == screenshot.id }
    }

    /**
     * Clear all selections.
     */
    fun clearSelection() {
        _selectedItems.clear()
    }

    /**
     * Get the number of selected screenshots.
     *
     * @return The number of selected screenshots
     */
    fun getSelectionCount(): Int {
        return _selectedItems.size
    }

    /**
     * Get the selected screenshots.
     *
     * @return The list of selected screenshots
     */
    fun getSelectedScreenshots(): List<SimpleScreenshot> {
        return _selectedItems.toList()
    }

    /**
     * Select all screenshots.
     *
     * @param screenshots The list of all screenshots
     */
    fun selectAll(screenshots: List<SimpleScreenshot>) {
        if (screenshots.isNotEmpty()) {
            selectionMode = true
            screenshots.forEach { screenshot ->
                if (_selectedItems.none { it.id == screenshot.id }) {
                    _selectedItems.add(screenshot)
                }
            }
        }
    }
}

/**
 * Remember a SelectionManager instance.
 *
 * @return A SelectionManager instance
 */
@Composable
fun rememberSelectionManager(): SelectionManager {
    return remember { SelectionManager() }
}
