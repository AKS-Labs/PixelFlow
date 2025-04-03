package com.aks_labs.pixelflow.data.models

/**
 * Simple data class for screenshots
 */
data class SimpleScreenshot(
    val id: Long,
    val filePath: String,
    val thumbnailPath: String,
    val folderId: Long,
    val originalTimestamp: Long,
    val savedTimestamp: Long = System.currentTimeMillis()
)
