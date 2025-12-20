package com.akslabs.pixelscreenshots.ui.screens

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.akslabs.pixelscreenshots.R
import com.akslabs.pixelscreenshots.data.SharedPrefsManager
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(
    navController: NavController,
    sharedPrefsManager: SharedPrefsManager
) {
    val scope = rememberCoroutineScope()

    val pages = listOf(
        OnboardingPage(
            title = "Organize Screenshots\nEffortlessly",
            description = "The app automatically organizes your screenshots & generates AI summaries. Easily search and find the right screenshot.",
            permissionType = null,
            imageResId = R.drawable.ic_onboarding_organize
        ),
        OnboardingPage(
            title = "How PixelScreenshots Works",
            description = "Your screenshots never leave your device. All text extraction is done locally to ensure your privacy.",
            permissionType = null,
            imageResId = R.drawable.ic_onboarding_privacy
        ),
        OnboardingPage(
            title = "Storage Permission",
            description = "We need access to your photos to analyze and create searchable versions. Your data stays safe on your device.",
            permissionType = PermissionType.STORAGE,
            imageResId = R.drawable.ic_onboarding_storage
        ),
        OnboardingPage(
            title = "Notification Permission",
            description = "Allow notifications to keep you updated on analyzing progress in the background.",
            permissionType = PermissionType.NOTIFICATION,
            imageResId = R.drawable.ic_onboarding_notification
        )
    )

    val pagerState = rememberPagerState(pageCount = { pages.size })

    val storagePermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            scope.launch {
                if (pagerState.currentPage < pages.size - 1) {
                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                }
            }
        }
    }

    val notificationPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        // Proceed regardless of result
        sharedPrefsManager.setOnboardingCompleted(true)
        navController.navigate("home") {
            popUpTo("onboarding") { inclusive = true }
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) { page ->
                OnboardingPageContent(
                    page = pages[page],
                    isActive = pagerState.currentPage == page
                )
            }

            // Bottom controls
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Indicators
                Row(
                    modifier = Modifier.padding(bottom = 32.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    repeat(pages.size) { index ->
                        val isSelected = pagerState.currentPage == index
                        Box(
                            modifier = Modifier
                                .height(8.dp)
                                .width(if (isSelected) 24.dp else 8.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(
                                    if (isSelected) MaterialTheme.colorScheme.primary
                                    else MaterialTheme.colorScheme.surfaceVariant
                                )
                        )
                    }
                }

                val currentPage = pages[pagerState.currentPage]
                val isLastPage = pagerState.currentPage == pages.size - 1

                Button(
                    onClick = {
                        when (currentPage.permissionType) {
                            PermissionType.STORAGE -> {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                    storagePermissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
                                } else {
                                    storagePermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                                }
                            }
                            PermissionType.NOTIFICATION -> {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                    notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                                } else {
                                    sharedPrefsManager.setOnboardingCompleted(true)
                                    navController.navigate("home") {
                                        popUpTo("onboarding") { inclusive = true }
                                    }
                                }
                            }
                            null -> {
                                scope.launch {
                                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                                }
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(28.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Text(
                        text = if (isLastPage) "Get Started" else "Continue",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@Composable
fun OnboardingPageContent(
    page: OnboardingPage,
    isActive: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AnimatedVisibility(
            visible = isActive,
            enter = fadeIn() + slideInVertically(initialOffsetY = { 40 }),
            exit = fadeOut() + slideOutVertically(targetOffsetY = { -40 })
        ) {
             Column(horizontalAlignment = Alignment.CenterHorizontally) {
                 Box(
                    modifier = Modifier
                        .size(280.dp)
                        .clip(RoundedCornerShape(40.dp))
                        .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)),
                    contentAlignment = Alignment.Center
                 ) {
                     Image(
                         painter = painterResource(id = page.imageResId),
                         contentDescription = null,
                         modifier = Modifier.size(160.dp),
                         colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimaryContainer)
                     )
                 }

                 Spacer(modifier = Modifier.height(48.dp))

                 Text(
                     text = page.title,
                     style = MaterialTheme.typography.displaySmall,
                     fontWeight = FontWeight.Bold,
                     textAlign = TextAlign.Center,
                     color = MaterialTheme.colorScheme.onBackground
                 )

                 Spacer(modifier = Modifier.height(16.dp))

                 Text(
                     text = page.description,
                     style = MaterialTheme.typography.bodyLarge,
                     textAlign = TextAlign.Center,
                     color = MaterialTheme.colorScheme.onSurfaceVariant
                 )
             }
        }
    }
}

data class OnboardingPage(
    val title: String,
    val description: String,
    val permissionType: PermissionType?,
    val imageResId: Int
)

enum class PermissionType {
    STORAGE,
    NOTIFICATION
}
