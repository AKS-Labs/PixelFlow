package com.aks_labs.pixelflow.ui.components


import android.view.ViewGroup
import android.widget.ImageView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.statusBars
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.aks_labs.pixelflow.data.models.SimpleScreenshot
import com.google.android.material.carousel.CarouselLayoutManager
import com.google.android.material.carousel.CarouselSnapHelper
import com.google.android.material.carousel.FullScreenCarouselStrategy
import com.google.android.material.carousel.MaskableFrameLayout
import com.google.android.material.shape.ShapeAppearanceModel
import android.view.Gravity
import android.view.View
import android.graphics.drawable.GradientDrawable
import android.widget.FrameLayout
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.filled.MoreVert
import java.io.File

/**
 * A vertical fullscreen carousel for browsing screenshots using Android View-based Material Carousel.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenshotCarousel(
    screenshots: List<SimpleScreenshot>,
    initialIndex: Int,
    onClose: () -> Unit,
    onScreenshotClick: () -> Unit,
    onShare: (SimpleScreenshot) -> Unit,
    onDelete: (SimpleScreenshot) -> Unit,
    onEdit: (SimpleScreenshot) -> Unit = {},
    onMove: (SimpleScreenshot) -> Unit = {},
    onAddNote: (SimpleScreenshot) -> Unit = {},
    onPageChanged: (Int) -> Unit = {}
) {
    var currentIndex by remember { mutableStateOf(initialIndex) }
    val currentScreenshot = screenshots.getOrNull(currentIndex)
    val context = LocalContext.current
    
    // State for delete confirmation dialog
    var showDeleteDialog by remember { mutableStateOf(false) }

    val metadata = remember(currentScreenshot) {
        currentScreenshot?.let { getScreenshotMetadata(it) }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background, // M3 Expressive Dynamic Color fallback
//        contentWindowInsets = WindowInsets(0, 0, 0, 0), // Handle insets manually for full control
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = metadata?.filename ?: "Carousel",
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.clickable {
                            metadata?.filename?.let { 
                                copyToClipboard(context, it, "Filename copied")
                            }
                        }
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onClose) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* Settings placeholder */ }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "Settings"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background, // Match Scaffold
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                    navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
                    actionIconContentColor = MaterialTheme.colorScheme.onSurface
                ),
                windowInsets = WindowInsets(0, 0, 0, 0) // Remove status bar padding for compact look
            )
        }
    ) {
            innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Spacer(modifier = Modifier.size(70.dp)) // Removed to make layout more compact

            // Metadata Section
            if (metadata != null) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp)
                ) {
                    // OCR Text Section
                    OutlinedTextField(
                        value = "No OCR text available- Feature Comming Soon...\n\n\n", // Placeholder/State for OCR text
                        onValueChange = {},
                        label = { Text("OCR Text", style = MaterialTheme.typography.labelSmall) },
                        placeholder = { Text("No OCR text available", style = MaterialTheme.typography.bodySmall) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        readOnly = true,
                        shape = MaterialTheme.shapes.small,
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                            unfocusedIndicatorColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                            focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                        ),
                        textStyle = MaterialTheme.typography.bodySmall
                    )

                    // Path Display with Text Field-like Background
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f),
                                shape = MaterialTheme.shapes.small
                            )
                            .clickable {
                                copyToClipboard(context, metadata.path, "Path copied")
                            }
                            .padding(8.dp)
                    ) {
                        Text(
                            text = metadata.path,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    Spacer(modifier = Modifier.size(4.dp))

                    // Chips Row
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        MetadataChip(text = metadata.size, color = MaterialTheme.colorScheme.secondaryContainer) {
                            copyToClipboard(context, metadata.size, "Size copied")
                        }
                        MetadataChip(text = metadata.resolution, color = MaterialTheme.colorScheme.tertiaryContainer) {
                            copyToClipboard(context, metadata.resolution, "Resolution copied")
                        }
                        MetadataChip(text = "${metadata.megaPixels} MP", color = MaterialTheme.colorScheme.primaryContainer) {
                            copyToClipboard(context, "${metadata.megaPixels} MP", "MP copied")
                        }
                        MetadataChip(text = metadata.date, color = MaterialTheme.colorScheme.surfaceVariant) {
                            copyToClipboard(context, metadata.date, "Date copied")
                        }
                    }
                }
            }

            // Action Buttons Row (Right Aligned as requested, below metadata)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp, vertical = 1.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (currentScreenshot != null) {
                    CarouselActionButton(Icons.Default.Share, "Share") { onShare(currentScreenshot) }
                    CarouselActionButton(Icons.Default.Edit, "Edit") { onEdit(currentScreenshot) }
                    CarouselActionButton(Icons.Default.Delete, "Delete") { showDeleteDialog = true }
                }
            }
            
            // Delete confirmation dialog
            if (showDeleteDialog && currentScreenshot != null) {
                AlertDialog(
                    onDismissRequest = { showDeleteDialog = false },
                    title = { Text("Delete Screenshot?") },
                    text = { Text("Are you sure you want to delete this screenshot? This action cannot be undone.") },
                    confirmButton = {
                        Button(
                            onClick = {
                                onDelete(currentScreenshot)
                                showDeleteDialog = false
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.error
                            )
                        ) {
                            Text("Delete")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDeleteDialog = false }) {
                            Text("Cancel")
                        }
                    }
                )
            }

//            Spacer(modifier = Modifier.size(4.dp))

            // The View-based Carousel
            // We wrap it in a box to manage padding/cutout if needed
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(16.dp)
                // The user said "no padding for corousel" - assume they mean edge-to-edge
                // But standard M3 carousel usually has start/end padding for next items peeking.
                // If they want it like the 2nd screenshot (Fullscreen one), it might be truly full width?
                // "it's not looking like the 2nd screenshot their is no padding for corousel"
                // If 2nd screenshot IS the target, and it has padding, then we need padding.
                // If 2nd screenshot is the "bad" one, then we remove padding.
                // Ambiguous. I will add minimal horizontal padding to allow peeking,
                // which is the signature Carousel look.
            ) {
                AndroidView(
                    modifier = Modifier.fillMaxSize(),
                    factory = { ctx ->
                        RecyclerView(ctx).apply {
                            layoutParams = ViewGroup.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT
                            )
                            clipChildren = false
                            clipToPadding = false

                            layoutManager = CarouselLayoutManager(FullScreenCarouselStrategy(), RecyclerView.VERTICAL)

                            adapter = CarouselAdapter(screenshots) {
                                onScreenshotClick()
                            }

                            val snapHelper = CarouselSnapHelper()
                            snapHelper.attachToRecyclerView(this)

                            scrollToPosition(initialIndex)

                            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                                    super.onScrollStateChanged(recyclerView, newState)
                                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                                        val lm = layoutManager as? CarouselLayoutManager ?: return
                                        val snapView = snapHelper.findSnapView(lm)
                                        if (snapView != null) {
                                            val position = lm.getPosition(snapView)
                                            if (position != RecyclerView.NO_POSITION) {
                                                currentIndex = position
                                                 onPageChanged(position)
                                             }
                                         }
                                     }
                                 }
                             })
                         }
                     },
                     update = { recyclerView ->
                        val adapter = recyclerView.adapter as? CarouselAdapter ?: return@AndroidView
                        val oldSize = adapter.itemCount
                        adapter.updateItems(screenshots)
                        
                        // If items were deleted, ensure currentIndex is still safe
                        if (adapter.itemCount < oldSize) {
                            if (currentIndex >= adapter.itemCount) {
                                currentIndex = (adapter.itemCount - 1).coerceAtLeast(0)
                            }
                            recyclerView.scrollToPosition(currentIndex)
                        }
                     }
                 )
             }
        }
    }
}

@Composable
fun MetadataChip(text: String, color: Color, onClick: () -> Unit = {}) {
    Box(
        modifier = Modifier
            .background(color, MaterialTheme.shapes.small)
            .clickable { onClick() }
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

data class ScreenshotMetadata(
    val filename: String,
    val path: String,
    val size: String,
    val resolution: String,
    val megaPixels: String,
    val date: String
)

fun getScreenshotMetadata(screenshot: SimpleScreenshot): ScreenshotMetadata {
    val file = File(screenshot.filePath)
    val filename = file.name
    val size = formatFileSize(file.length())
    val date = java.text.SimpleDateFormat("dd MMM yyyy", java.util.Locale.getDefault())
        .format(java.util.Date(screenshot.originalTimestamp))

    // Calculate resolution and MP (requires decoding bounds, done safely)
    val options = android.graphics.BitmapFactory.Options().apply {
        inJustDecodeBounds = true
    }
    android.graphics.BitmapFactory.decodeFile(screenshot.filePath, options)
    val width = options.outWidth
    val height = options.outHeight
    val resolution = "$width x $height"
    val mp = String.format("%.1f", (width * height) / 1_000_000f)

    return ScreenshotMetadata(filename, screenshot.filePath, size, resolution, mp, date)
}

fun formatFileSize(size: Long): String {
    val kb = size / 1024.0
    val mb = kb / 1024.0
    return when {
        mb >= 1 -> String.format("%.2f MB", mb)
        kb >= 1 -> String.format("%.2f KB", kb)
        else -> "$size B"
    }
}


@Composable
fun CarouselActionButton(
    icon: ImageVector,
    description: String,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        colors = IconButtonDefaults.iconButtonColors(
            contentColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Icon(imageVector = icon, contentDescription = description)
    }
}

/**
 * RecyclerView Adapter for the Carousel
 */
private class CarouselAdapter(
    private var items: List<SimpleScreenshot>,
    private val onClick: () -> Unit
) : RecyclerView.Adapter<CarouselAdapter.CarouselViewHolder>() {

    fun updateItems(newItems: List<SimpleScreenshot>) {
        if (items != newItems) {
            items = newItems
            notifyDataSetChanged()
        }
    }

    class CarouselViewHolder(val view: MaskableFrameLayout) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.getChildAt(0) as ImageView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselViewHolder {
        val context = parent.context
        val maskableFrameLayout = MaskableFrameLayout(context).apply {
            layoutParams = ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            ).apply {
                val margin = (context.resources.displayMetrics.density * 4).toInt() // 4dp margin
                setMargins(margin, 0, margin, 0)
            }
            shapeAppearanceModel = ShapeAppearanceModel.builder()
                .setAllCornerSizes(context.resources.displayMetrics.density * 28)
                .build()
        }

        val imageView = ImageView(context).apply {
            layoutParams = android.widget.FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            scaleType = ImageView.ScaleType.CENTER_CROP
        }

        maskableFrameLayout.addView(imageView)

        // --- BORDER SHADOW SETTINGS ---
        // Transparency: 0 (transparent) to 255 (opaque). Current: 160
        val shadowAlpha = 20
        // Size: Current: 120dp for top/bottom, 60dp for sides
        val verticalShadowSize = (context.resources.displayMetrics.density * 50).toInt()
        val horizontalShadowSize = (context.resources.displayMetrics.density * 50).toInt()
        // ------------------------------

        // Add Translucent Border Shadows (Dimmers)
        // Top Shadow
        val topShadow = View(context).apply {
            layoutParams = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                verticalShadowSize,
                Gravity.TOP
            )
            background = GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                intArrayOf(
                    android.graphics.Color.argb(shadowAlpha, 0, 0, 0),
                    android.graphics.Color.TRANSPARENT
                )
            )
        }
        maskableFrameLayout.addView(topShadow)

        // Bottom Shadow
        val bottomShadow = View(context).apply {
            layoutParams = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                verticalShadowSize,
                Gravity.BOTTOM
            )
            background = GradientDrawable(
                GradientDrawable.Orientation.BOTTOM_TOP,
                intArrayOf(
                    android.graphics.Color.argb(shadowAlpha, 0, 0, 0),
                    android.graphics.Color.TRANSPARENT
                )
            )
        }
        maskableFrameLayout.addView(bottomShadow)

        // Left Shadow
        val leftShadow = View(context).apply {
            layoutParams = FrameLayout.LayoutParams(
                horizontalShadowSize,
                ViewGroup.LayoutParams.MATCH_PARENT,
                Gravity.START
            )
            background = GradientDrawable(
                GradientDrawable.Orientation.LEFT_RIGHT,
                intArrayOf(
                    android.graphics.Color.argb(shadowAlpha, 0, 0, 0),
                    android.graphics.Color.TRANSPARENT
                )
            )
        }
        maskableFrameLayout.addView(leftShadow)

        // Right Shadow
        val rightShadow = View(context).apply {
            layoutParams = FrameLayout.LayoutParams(
                horizontalShadowSize,
                ViewGroup.LayoutParams.MATCH_PARENT,
                Gravity.END
            )
            background = GradientDrawable(
                GradientDrawable.Orientation.RIGHT_LEFT,
                intArrayOf(
                    android.graphics.Color.argb(shadowAlpha, 0, 0, 0),
                    android.graphics.Color.TRANSPARENT
                )
            )
        }
        maskableFrameLayout.addView(rightShadow)

        return CarouselViewHolder(maskableFrameLayout)
    }

    override fun onBindViewHolder(holder: CarouselViewHolder, position: Int) {
        val item = items[position]

        // Load image using Coil
        holder.imageView.load(File(item.filePath)) {
            crossfade(true)
        }

        holder.itemView.setOnClickListener {
            onClick()
        }
    }

    override fun getItemCount(): Int = items.size
}

/**
 * Helper to copy text to clipboard and show a toast
 */
private fun copyToClipboard(context: Context, text: String, label: String) {
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText(label, text)
    clipboard.setPrimaryClip(clip)
    Toast.makeText(context, "$label to clipboard", Toast.LENGTH_SHORT).show()
}
