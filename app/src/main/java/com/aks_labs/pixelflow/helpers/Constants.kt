package com.akslabs.pixelscreenshots.helpers

object AnimationConstants {
    const val DURATION_EXTRA_LONG = 400
}

object PhotoGridConstants {
    const val LOADING_TIME_SHORT = 100L
}

object Screens {
    sealed class Screen(val route: String)
    data class SingleAlbumView(val albumInfo: com.akslabs.pixelscreenshots.data.models.SimpleFolder) : Screen("folder_details/${albumInfo.id}")
}

enum class MultiScreenViewType {
    FavouritesGridView,
    TrashedPhotoView
}
