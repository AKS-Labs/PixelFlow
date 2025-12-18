package com.aks_labs.pixelflow.ui.screens

//import androidx.compose.material3.pulltorefresh.pullToRefresh
//import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
// import androidx.compose.ui.util.fastDistinctBy // Removed
// import androidx.compose.ui.util.fastFilter // Removed
// import androidx.compose.ui.util.fastMap // Removed
import android.content.res.Configuration
import android.util.Log
import androidx.annotation.FloatRange
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.center
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.round
import androidx.compose.ui.unit.toOffset
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.aks_labs.pixelflow.data.models.AlbumSortMode
//import com.aks_labs.pixelflow.data.models.SimpleFolder
import com.aks_labs.pixelflow.helpers.AnimationConstants
import com.aks_labs.pixelflow.helpers.BottomBarTab
import com.aks_labs.pixelflow.helpers.DefaultTabs
import com.aks_labs.pixelflow.helpers.MultiScreenViewType
import com.aks_labs.pixelflow.helpers.PhotoGridConstants
import com.aks_labs.pixelflow.helpers.Screens
import com.aks_labs.pixelflow.ui.components.AddFolderDialog
import java.io.File
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material.icons.filled.Add
import androidx.compose.ui.res.stringResource
import com.aks_labs.pixelflow.R
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import com.aks_labs.pixelflow.data.models.SimpleFolder
import com.aks_labs.pixelflow.data.models.SimpleScreenshot
import com.aks_labs.pixelflow.helpers.navigate
import com.aks_labs.pixelflow.ui.components.GlideImage
import com.aks_labs.pixelflow.ui.components.shimmerEffect
import com.aks_labs.pixelflow.ui.viewmodels.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FolderScreen(
    navController: NavController,
    viewModel: MainViewModel
) {
    val context = LocalContext.current
    val mainViewModel = viewModel
    val currentView = remember { mutableStateOf(BottomBarTab("Home")) } // Dummy
    val isMediaPicker = false
    val dummyScreenshot = SimpleScreenshot(0, "", "", 0, 0, 0)

    // used to save pinned albums in-case of auto detecting
    val normalAlbums = mainViewModel.settings.AlbumsList.getNormalAlbums()
        .collectAsState(initial = emptyList<SimpleFolder>())

    val listOfDirs by mainViewModel.allAvailableAlbums.collectAsState(initial = emptyList<SimpleFolder>())

    val sortMode by mainViewModel.settings.AlbumsList.getAlbumSortMode()
        .collectAsState(initial = AlbumSortMode.Custom)

    val sortByDescending by mainViewModel.settings.AlbumsList
        .getSortByDescending()
        .collectAsState(initial = true)

    val albums = remember { mutableStateOf(listOfDirs) }

    val albumToThumbnailMapping = mainViewModel.albumsThumbnailsMap
    val mediaSortMode by mainViewModel.sortMode.collectAsState()
    
    var showAddDialog by remember { mutableStateOf(false) }

    LaunchedEffect(listOfDirs, normalAlbums, sortMode, sortByDescending, albumToThumbnailMapping, mediaSortMode) {
        if (listOfDirs.isEmpty()) return@LaunchedEffect

        withContext(Dispatchers.IO) {
            val newList = mutableListOf<SimpleFolder>()

            // if the albums actually changed, not just the order then refresh
            // Simply refresh always for now or check IDs
            // Use the async version and wait for it to complete
            mainViewModel.refreshAlbumsAsync(listOfDirs)
            
            // Log the thumbnail mapping after refresh
            Log.d("FolderScreen", "After refresh - albumToThumbnailMapping size: ${albumToThumbnailMapping.size}")
            listOfDirs.forEach { album ->
                val thumbnail = albumToThumbnailMapping[album.id]
                Log.d("FolderScreen", "Album ${album.name} (id=${album.id}): thumbnail=${thumbnail?.filePath ?: "null"}")
            }
            
            val copy = listOfDirs.toList() // Safe copy
            
            // ... (Sorting Logic - keeping as is but adjusting properties if needed)
            // PixelFlow `SimpleFolder` doesn't have date properties for sorting? 
            // We map `albumToThumbnailMapping` (which is Map<Long, SimpleScreenshot>)
            
            when (sortMode) {
                AlbumSortMode.LastModified -> {
                     /* Logic from user code */
                     newList.addAll(
                        if (sortByDescending) {
                            copy.sortedByDescending { album ->
                                // Use mapped screenshot timestamp
                                albumToThumbnailMapping[album.id]?.savedTimestamp ?: 0L 
                            }
                        } else {
                            copy.sortedBy { album ->
                                albumToThumbnailMapping[album.id]?.savedTimestamp ?: 0L
                            }
                        }
                    )
                }
                AlbumSortMode.Alphabetically -> {
                    newList.addAll(
                        if (!sortByDescending) {
                            copy.sortedBy { it.name }
                        } else {
                            copy.sortedByDescending { it.name }
                        }
                    )
                }
                AlbumSortMode.Custom -> {
                    newList.addAll(copy)
                }
            }
            
            // Pinned Logic
            // Pinned Logic
             val pinnedInNormal = normalAlbums.value.filter { it.isPinned }
             val pinnedInNormalIds = pinnedInNormal.map { it.id }

             newList.removeAll { it.id in pinnedInNormalIds }
             newList.addAll(0, pinnedInNormal)

             albums.value = newList.distinctBy { it.id }
        }
    }
    
    // ...
    
    // Fix Drag Logic
    
    // ...
    
    // SortModeHeader calls
    
    // ...


    LaunchedEffect(albums.value) {
        if (albums.value.isEmpty()) return@LaunchedEffect // will never, EVER be empty

        // delay to avoid glitchy-ness when removing albums
        delay(PhotoGridConstants.LOADING_TIME_SHORT)

        // update the list to reflect custom order
        mainViewModel.settings.AlbumsList.setAlbumsList(albums.value)
    }

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(
                        text = "Folders",
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            ) {
                Icon(Icons.Default.Add, contentDescription = stringResource(id = R.string.add_folder))
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize(1f)
                .background(MaterialTheme.colorScheme.background)
                .padding(innerPadding)
                .padding(horizontal = 8.dp), // Add horizontal padding manually since we consumed innerPadding
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
        val localConfig = LocalConfiguration.current
        val localDensity = LocalDensity.current
        var isLandscape by remember { mutableStateOf(localConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) }

        LaunchedEffect(localConfig) {
            isLandscape = localConfig.orientation == Configuration.ORIENTATION_LANDSCAPE
        }

        val lazyGridState = rememberLazyGridState()
        var itemOffset by remember { mutableStateOf(Offset.Zero) }
        var selectedItem: SimpleFolder? by remember { mutableStateOf(null) }

        // val pullToRefreshState = rememberPullToRefreshState() // Removed
        var lockHeader by remember { mutableStateOf(false) }
        val headerHeight by remember {
            derivedStateOf {
                with(localDensity) {
                    0f // pullToRefreshState.distanceFraction * 56.dp.toPx()
                }
            }
        }

        /*
        LaunchedEffect(lazyGridState.isScrollInProgress) {
            if (lazyGridState.isScrollInProgress && lazyGridState.canScrollBackward) lockHeader =
                false
        }
        */

//        SortModeHeader(
//            sortMode = sortMode,
//            currentView = currentView,
//            progress = 0f, // pullToRefreshState.distanceFraction.coerceAtMost(1f),
//            modifier = Modifier
//                .height(56.dp) // Fixed ambiguous height call
//                .zIndex(1f),
//            viewModel = mainViewModel
//        )

        val coroutineScope = rememberCoroutineScope()
        val columnSize by mainViewModel.albumColumnSize.collectAsState()
        LazyVerticalGrid(
            state = lazyGridState,
            columns = GridCells.Fixed(
                if (!isLandscape) {
                    columnSize
                } else {
                    columnSize * 2
                }
            ),
            modifier = Modifier
                .fillMaxSize(1f)
               /* .pullToRefresh(
                    isRefreshing = lockHeader,
                    state = pullToRefreshState,
                    onRefresh = {
                        lockHeader = true
                    }
                ) */ // Commented out experimental M3 modifier
                .pointerInput(Unit) {
                    detectDragGesturesAfterLongPress(
                        onDragStart = { offset ->
                            lazyGridState.layoutInfo.visibleItemsInfo
                                .find { item ->
                                    IntRect(
                                        offset = item.offset,
                                        size = item.size
                                    ).contains(offset.round()) && !item.key.toString().startsWith("FavAndTrash")
                                }?.let { item ->
                                    selectedItem = albums.value[item.index - 1]
                                } ?: run { selectedItem = null }
                        },

                        onDrag = { change, offset ->
                            change.consume()
                            itemOffset += offset

                            val targetItem = lazyGridState.layoutInfo.visibleItemsInfo
                                .find { item ->
                                    IntRect(
                                        offset = item.offset,
                                        size = item.size
                                    ).contains(change.position.round())
                                }

                            val currentLazyItem =
                                lazyGridState.layoutInfo.visibleItemsInfo.find {
                                    it.key == selectedItem?.id
                                }

                            if (targetItem != null && currentLazyItem != null && targetItem.key in albums.value.map { it.id }) {
                                val targetItemIndex =
                                    albums.value.indexOfFirst { it.id == targetItem.key }
                                val newList = albums.value.toMutableList()
                                newList.remove(selectedItem)
                                newList.add(targetItemIndex, selectedItem!!)

                                itemOffset =
                                    change.position - (targetItem.offset + targetItem.size.center).toOffset()

                                albums.value = newList.distinctBy { it.id }
                                if (sortMode != AlbumSortMode.Custom) mainViewModel.settings.AlbumsList.setAlbumSortMode(
                                    AlbumSortMode.Custom
                                )
                            } else if (currentLazyItem != null) {
                                val startOffset = currentLazyItem.offset.y + itemOffset.y
                                val endOffset =
                                    currentLazyItem.offset.y + currentLazyItem.size.height + itemOffset.y

                                val offsetToTop =
                                    startOffset - lazyGridState.layoutInfo.viewportStartOffset
                                val offsetToBottom =
                                    endOffset - lazyGridState.layoutInfo.viewportEndOffset

                                val scroll = when {
                                    offsetToTop < 0 -> offsetToTop.coerceAtMost(0f)
                                    offsetToBottom > 0 -> offsetToBottom.coerceAtLeast(0f)
                                    else -> 0f
                                }

                                if (scroll != 0f && (lazyGridState.canScrollBackward || lazyGridState.canScrollForward)) coroutineScope.launch {
                                    lazyGridState.scrollBy(scroll)
                                }
                            }
                        },

                        onDragCancel = {
                            selectedItem = null
                            itemOffset = Offset.Zero
                        },

                        onDragEnd = {
                            selectedItem = null
                            itemOffset = Offset.Zero
                        }
                    )
                },
            horizontalArrangement = Arrangement.Start,
            verticalArrangement = Arrangement.Top
        ) {
            if (!isMediaPicker) {
                item(
                    span = { GridItemSpan(maxLineSpan) },
                    key = "FavAndTrash"
                ) {
//                    CategoryList(
//                        navigateToFavourites = {
//                            navController.navigate(MultiScreenViewType.FavouritesGridView.name)
//                        },
//                        navigateToTrash = {
//                            navController.navigate(MultiScreenViewType.TrashedPhotoView.name)
//                        }
//                    )
                }
            }

            items(
                count = albums.value.size,
                key = { index -> 
                    // Use a composite key or just the ID if unique. 
                    // Fallback to index if ID is duplicate (though strictly IDs should be unique)
                    val album = albums.value[index]
                    "${album.id}_$index" 
                }
            ) { index ->
                val album = albums.value[index]
                val mediaItem = albumToThumbnailMapping[album.id] ?: dummyScreenshot

                AlbumGridItem(
                    album = album,
                    item = mediaItem,
                    isSelected = selectedItem == album,
                    modifier = Modifier
                        .zIndex(
                            if (selectedItem == album) 1f
                            else 0f
                        )
                        .graphicsLayer {
                            if (selectedItem == album) {
                                translationX = itemOffset.x
                                translationY = itemOffset.y
                            }
                        }
                        .wrapContentSize()
                        .animateContentSize() // Fallback
                ) {
                    navController.navigate(
                        Screens.SingleAlbumView(
                            albumInfo = album
                        )
                    )
                }
            }

            // padding for floating bottom nav bar
            item(
                span = {
                    GridItemSpan(maxLineSpan)
                }
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .height(120.dp)
                )
            }
        }
    }

    if (showAddDialog) {
        AddFolderDialog(
            onDismiss = { showAddDialog = false },
            onAddFolder = { name, iconName ->
                mainViewModel.addFolder(name, iconName)
                showAddDialog = false
            }
        )
    }
} // End of Scaffold content lambda
} // End of FolderScreen function

// @OptIn(ExperimentalGlideComposeApi::class) // Removed
@Composable
private fun AlbumGridItem(
    album: SimpleFolder,
    item: SimpleScreenshot,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    // Log the item being displayed
    Log.d("AlbumGridItem", "Rendering album: ${album.name}, itemId: ${item.id}, filePath: ${item.filePath}, thumbnailPath: ${item.thumbnailPath}")
    
    val animatedScale by animateFloatAsState(
        targetValue = if (isSelected) 0.9f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "animate album grid item scale"
    )

    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant, // Replaced surfaceContainer
        animationSpec = tween(
            durationMillis = 200
        ),
        label = "animate selected album grid item background color"
    )

    Column(
        modifier = modifier
            .wrapContentHeight()
            .fillMaxWidth(1f)
            .scale(animatedScale)
            .padding(6.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(backgroundColor)
            .clickable {
                if (!isSelected) onClick()
            },
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(1f)
                .padding(8.dp, 8.dp, 8.dp, 4.dp)
                .clip(RoundedCornerShape(16.dp)),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnimatedContent(
                targetState = item.id != 0L,
                transitionSpec = {
                    fadeIn(
                        animationSpec = tween(
                            durationMillis = AnimationConstants.DURATION_EXTRA_LONG
                        )
                    ).togetherWith(
                        fadeOut(
                            animationSpec = tween(
                                durationMillis = AnimationConstants.DURATION_EXTRA_LONG
                            )
                        )
                    )
                },
                modifier = Modifier
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(16.dp))
            ) { state ->
                if (state) {
                    val model = if (item.thumbnailPath.isNotEmpty()) File(item.thumbnailPath) else File(item.filePath)
                    GlideImage(
                        model = model,
                        contentDescription = item.id.toString(), // Use id as desc
                        contentScale = ContentScale.Crop,
                        // failure = placeholder(R.drawable.broken_image), // Commented out
                        modifier = Modifier
                            .aspectRatio(1f)
                            .clip(RoundedCornerShape(16.dp))
                            .background(MaterialTheme.colorScheme.surfaceVariant), // Replaced surfaceContainerHigh
                    ) {
                       // it.signature(item.signature()) // Removed
                       //     .diskCacheStrategy(DiskCacheStrategy.ALL) // Removed
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .aspectRatio(1f)
                            .clip(RoundedCornerShape(16.dp))
                            .shimmerEffect(
                                containerColor = MaterialTheme.colorScheme.surface, // Replaced surfaceContainerLowest
                                highlightColor = MaterialTheme.colorScheme.surfaceVariant // Replaced surfaceContainerHighest
                            )
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .padding(2.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = " ${album.name}",
                    fontSize = TextUnit(14f, TextUnitType.Sp),
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                if (album.isCustomAlbum) {
                    Icon(
                        imageVector = Icons.Default.Lock, // Placeholder for art_track/custom
                        contentDescription = "Custom",
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier
                            .padding(end = 2.dp)
                    )
                }
            }
        }
    }
}

//@Composable
//private fun CategoryList(
//    navigateToTrash: () -> Unit,
//    navigateToFavourites: () -> Unit
//) {
//    Row(
//        modifier = Modifier
//            .fillMaxWidth(1f)
//            .wrapContentHeight()
//            .padding(8.dp)
//            .background(MaterialTheme.colorScheme.background),
//        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.SpaceEvenly
//    ) {
//        OutlinedButton(
//            onClick = {
//                navigateToFavourites()
//            },
//            modifier = Modifier
//                .weight(1f)
//                .height(48.dp)
//        ) {
//            Row(
//                modifier = Modifier.fillMaxWidth(1f),
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.SpaceEvenly
//            ) {
//                Icon(
//                    imageVector = Icons.Default.Favorite, // Replaced favourite
//                    contentDescription = "Favourites",
//                    tint = MaterialTheme.colorScheme.primary,
//                    modifier = Modifier
//                        .size(22.dp)
//                        .padding(0.dp, 2.dp, 0.dp, 0.dp)
//                )
//
//                Spacer(
//                    modifier = Modifier
//                        .width(8.dp)
//                )
//
//                Text(
//                    text = "Favourites", // Hardcoded
//                    fontSize = TextUnit(16f, TextUnitType.Sp),
//                    textAlign = TextAlign.Center,
//                    color = MaterialTheme.colorScheme.onBackground,
//                    modifier = Modifier
//                        .fillMaxWidth(1f)
//                )
//            }
//        }
//
//        Spacer(modifier = Modifier.width(8.dp))
//
//        OutlinedButton(
//            onClick = {
//                navigateToTrash()
//            },
//            modifier = Modifier
//                .weight(1f)
//                .height(48.dp)
//        ) {
//            Row(
//                modifier = Modifier.fillMaxWidth(1f),
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.SpaceEvenly
//            ) {
//                Icon(
//                    imageVector = Icons.Default.Delete, // Replaced trash
//                    contentDescription = "Trash",
//                    tint = MaterialTheme.colorScheme.primary,
//                    modifier = Modifier
//                        .size(20.dp)
//                )
//
//                Text(
//                    text = "Trash ", // Hardcoded
//                    fontSize = TextUnit(16f, TextUnitType.Sp),
//                    textAlign = TextAlign.Center,
//                    color = MaterialTheme.colorScheme.onBackground,
//                    modifier = Modifier
//                        .fillMaxWidth(1f)
//                )
//            }
//        }
//    }
//}

@Composable
private fun SortModeHeader(
    sortMode: AlbumSortMode,
    currentView: MutableState<BottomBarTab>,
    @FloatRange(0.0, 1.0) progress: Float,
    modifier: Modifier = Modifier,
    viewModel: MainViewModel
) {
    val mainViewModel = viewModel
    val tabList = emptyList<BottomBarTab>() // Dummy to fix build
    // val tabList by mainViewModel.settings.DefaultTabs.getTabList() ...

    LazyRow(
        modifier = modifier
            .fillMaxWidth(1f)
            .padding(4.dp, 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(
            space = 8.dp,
            alignment = Alignment.Start
        )
    ) {
        item {
            val sortByDescending by mainViewModel.settings.AlbumsList.getSortByDescending()
                .collectAsState(initial = true)

            OutlinedIconButton(
                onClick = {
                    mainViewModel.settings.AlbumsList.setSortByDescending(!sortByDescending)
                },
                enabled = sortMode != AlbumSortMode.Custom
            ) {
                val animatedRotation by animateFloatAsState(
                    targetValue = if (sortByDescending) -90f else 90f,
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioLowBouncy,
                        stiffness = Spring.StiffnessLow
                    ),
                    label = "Animate sort order arrow"
                )

                Icon(
                    imageVector = Icons.Default.ArrowBack, // Replaced back_arrow
                    contentDescription = "Sort",
                    modifier = Modifier.rotate(animatedRotation)
                )
            }
        }

        item {
            OutlinedButton(
                onClick = {
                    mainViewModel.settings.AlbumsList.setAlbumSortMode(AlbumSortMode.LastModified)
                },
                colors =
                    if (sortMode == AlbumSortMode.LastModified) ButtonDefaults.buttonColors()
                    else ButtonDefaults.outlinedButtonColors()
            ) {
                Text(
                    text = "Date", // Hardcoded
                    modifier = Modifier
                        .scale(progress)
                )
            }
        }

        item {
            OutlinedButton(
                onClick = {
                    mainViewModel.settings.AlbumsList.setAlbumSortMode(AlbumSortMode.Alphabetically)
                },
                colors =
                    if (sortMode == AlbumSortMode.Alphabetically) ButtonDefaults.buttonColors()
                    else ButtonDefaults.outlinedButtonColors()
            ) {
                Text(
                    text = "Name", // Hardcoded
                    modifier = Modifier
                        .scale(progress)
                )
            }
        }

        item {
            OutlinedButton(
                onClick = {
                    mainViewModel.settings.AlbumsList.setAlbumSortMode(AlbumSortMode.Custom)
                },
                colors =
                    if (sortMode == AlbumSortMode.Custom) ButtonDefaults.buttonColors()
                    else ButtonDefaults.outlinedButtonColors()
            ) {
                Text(
                    text = "Custom", // Hardcoded
                    modifier = Modifier
                        .scale(progress)
                )
            }
        }

        if (!tabList.contains(DefaultTabs.TabTypes.secure)) {
            item {
                OutlinedButton(
                    onClick = {
                        currentView.value = DefaultTabs.TabTypes.secure
                    },
                    colors =
                        if (sortMode == AlbumSortMode.Custom) ButtonDefaults.buttonColors()
                        else ButtonDefaults.outlinedButtonColors()
                ) {
                    Text(
                        text = "Secure", // Hardcoded
                        modifier = Modifier
                            .scale(progress)
                    )
                }
            }
        }
    }
}