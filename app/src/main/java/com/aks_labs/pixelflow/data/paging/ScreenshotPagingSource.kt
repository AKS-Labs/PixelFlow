package com.aks_labs.pixelflow.data.paging

import android.content.ContentResolver
import android.database.Cursor
import android.provider.MediaStore
import android.os.Build
import android.os.Bundle
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.aks_labs.pixelflow.data.models.SimpleScreenshot
import java.io.File

class ScreenshotPagingSource(
    private val contentResolver: ContentResolver
) : PagingSource<Int, SimpleScreenshot>() {

    override fun getRefreshKey(state: PagingState<Int, SimpleScreenshot>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SimpleScreenshot> {
        val pageNumber = params.key ?: 0
        val pageSize = params.loadSize

        val screenshots = mutableListOf<SimpleScreenshot>()
        
        // Calculate offset
        val offset = pageNumber * pageSize

        try {
            val projection = arrayOf(
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DATE_MODIFIED
            )

            // Sort order
            val sortOrder = "${MediaStore.Images.Media.DATE_MODIFIED} DESC"
            
            // Selection logic
            val selection = "${MediaStore.Images.Media.DATA} LIKE ? OR ${MediaStore.Images.Media.DATA} LIKE ?"
            val selectionArgs = arrayOf("%/Screenshots/%", "%/PixelFlow/%")

            // Query using LIMIT and OFFSET handling
            // Note: Direct LIMIT/OFFSET is supported in standard SQL, but Android ContentResolver 
            // has different ways to handle pagination depending on API level.
            
            val queryCursor: Cursor?
            
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // Use ContentResolver.query with Bundle for pagination on Android O+
                val queryArgs = Bundle().apply {
                    putString(ContentResolver.QUERY_ARG_SQL_SELECTION, selection)
                    putStringArray(ContentResolver.QUERY_ARG_SQL_SELECTION_ARGS, selectionArgs)
                    putString(ContentResolver.QUERY_ARG_SQL_SORT_ORDER, sortOrder)
                    putInt(ContentResolver.QUERY_ARG_LIMIT, pageSize)
                    putInt(ContentResolver.QUERY_ARG_OFFSET, offset)
                }
                
                queryCursor = contentResolver.query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    projection,
                    queryArgs,
                    null
                )
            } else {
                // Fallback for older versions (less efficient but functional)
                // We'll limit the query manually via "LIMIT start, count" if supported or just reading manually
                // Standard ContentResolver doesn't easily support LIMIT clause in sortOrder string on all devices reliably.
                // For simplicity and safety on older devices, we might have to query a broader range or just support logic carefully.
                // However, since we are targeting minSdk 26 (Android 8.0), we can rely on the Bundle approach mostly,
                // BUT the bundle constants are actually API 26 (O) or API 30 (R) depending on specific args.
                // QUERY_ARG_OFFSET is API 26.
                
                val sortOrderWithLimit = "$sortOrder LIMIT $pageSize OFFSET $offset"
                queryCursor = contentResolver.query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    projection,
                    selection,
                    selectionArgs,
                    sortOrderWithLimit 
                )
            }

            queryCursor?.use { cursor ->
                val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
                val dataColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                val dateModifiedColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_MODIFIED)

                while (cursor.moveToNext()) {
                    val id = cursor.getLong(idColumn)
                    val filePath = cursor.getString(dataColumn)
                    val dateModified = cursor.getLong(dateModifiedColumn)

                    // Check if file exists to filter out stale MediaStore entries
                    val file = File(filePath)
                    if (!file.exists()) {
                        // File was deleted externally but MediaStore still has entry
                        android.util.Log.d("ScreenshotPagingSource", "Skipping deleted file: $filePath")
                        continue
                    }
                    
                    // Additional validation: check file is readable
                    if (!file.canRead()) {
                        android.util.Log.w("ScreenshotPagingSource", "Skipping unreadable file: $filePath")
                        continue
                    }
                    
                    // Filter logic (check if it is actually a screenshot based on name or path)
                    val isPixelFlow = filePath.contains("PixelFlow", ignoreCase = true)
                    val isScreenshotName = file.name.contains("screenshot", ignoreCase = true)
                    
                    if (isPixelFlow || isScreenshotName) {
                        val screenshot = SimpleScreenshot(
                            id = id,
                            filePath = filePath,
                            thumbnailPath = filePath,
                            folderId = 0,
                            originalTimestamp = dateModified * 1000,
                            savedTimestamp = dateModified * 1000
                        )
                        screenshots.add(screenshot)
                    }
                }
            }

            val prevKey = if (pageNumber > 0) pageNumber - 1 else null
            val nextKey = if (screenshots.size == pageSize) pageNumber + 1 else null

            return LoadResult.Page(
                data = screenshots,
                prevKey = prevKey,
                nextKey = nextKey
            )

        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}
