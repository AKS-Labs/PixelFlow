package com.aks_labs.pixelflow.ui.components

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest
import java.io.File

// Wrapper to mimic GlideImage API used in user's code but using Coil
@Composable
fun GlideImage(
    model: Any?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit,
    failure: Any? = null, // Ignored for simple wrapper
    requestBuilder: (Any) -> Any = {} // Ignored
) {
    val context = LocalContext.current
    
    // Log for debugging
    if (model is File) {
        Log.d("GlideImage", "Loading image from file: ${model.absolutePath}, exists: ${model.exists()}")
    } else {
        Log.d("GlideImage", "Loading image with model: $model")
    }
    
    AsyncImage(
        model = ImageRequest.Builder(context)
            .data(model)
            .crossfade(true)
            .build(),
        contentDescription = contentDescription,
        contentScale = contentScale,
        modifier = modifier
    )
}

// Dummy for API compatibility
fun placeholder(resId: Int) = resId
