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
    val createdAt: Long = System.currentTimeMillis()
)
