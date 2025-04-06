package com.aks_labs.pixelflow.data.models

/**
 * Simple data class for folders
 */
data class SimpleFolder(
    val id: Long,
    val name: String,
    val iconName: String,
    val position: Int,
    val isDefault: Boolean = false,
    val isEditable: Boolean = true,  // All folders are editable by default
    val isRemovable: Boolean = true, // All folders are removable by default
    val createdAt: Long = System.currentTimeMillis()
)
