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
        }
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
