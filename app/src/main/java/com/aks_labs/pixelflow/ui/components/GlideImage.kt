package com.aks_labs.pixelflow.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest

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
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
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
