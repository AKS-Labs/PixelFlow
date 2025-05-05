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

    // List of selected screenshot IDs
    private val _selectedIds = mutableStateListOf<Long>()
    val selectedIds: List<Long> get() = _selectedIds

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
        return if (_selectedIds.contains(screenshot.id)) {
            _selectedIds.remove(screenshot.id)
            if (_selectedIds.isEmpty()) {
                selectionMode = false
            }
            false
        } else {
            _selectedIds.add(screenshot.id)
            true
        }
    }

    /**
     * Select a screenshot during drag selection.
     *
     * @param screenshot The screenshot to select
     */
    fun selectDuringDrag(screenshot: SimpleScreenshot) {
        if (isDragSelecting && !_selectedIds.contains(screenshot.id)) {
            _selectedIds.add(screenshot.id)
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
        return _selectedIds.contains(screenshot.id)
    }

    /**
     * Clear all selections.
     */
    fun clearSelection() {
        _selectedIds.clear()
    }

    /**
     * Get the number of selected screenshots.
     *
     * @return The number of selected screenshots
     */
    fun getSelectionCount(): Int {
        return _selectedIds.size
    }

    /**
     * Get the selected screenshots from a list of all screenshots.
     *
     * @param allScreenshots The list of all screenshots
     * @return The list of selected screenshots
     */
    fun getSelectedScreenshots(allScreenshots: List<SimpleScreenshot>): List<SimpleScreenshot> {
        return allScreenshots.filter { _selectedIds.contains(it.id) }
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
                if (!_selectedIds.contains(screenshot.id)) {
                    _selectedIds.add(screenshot.id)
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
