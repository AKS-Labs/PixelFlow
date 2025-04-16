package com.aks_labs.pixelflow.ui.screens

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.aks_labs.pixelflow.R
import androidx.navigation.NavController
import com.aks_labs.pixelflow.data.SharedPrefsManager
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(
    navController: NavController,
    sharedPrefsManager: SharedPrefsManager
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    // Define onboarding pages
    val pages = listOf(
        OnboardingPage(
            title = "Organize Screenshots Effortlessly",
            description = "The app automatically organizes your screenshots & generates AI summaries. Easily search and find the right screenshot based on content.",
            permissionType = null,
            imageResId = R.drawable.ic_onboarding_organize
        ),
        OnboardingPage(
            title = "How PixelFlow Works",
            description = "Your screenshots never leave your device. All text extraction is done locally to ensure your privacy & none of your images are ever uploaded to the cloud.",
            permissionType = null,
            imageResId = R.drawable.ic_onboarding_privacy
        ),
        OnboardingPage(
            title = "Storage Permission",
            description = "To analyze your screenshots & create searchable versions, we need access to your photos. This will allow you to easily find & retrieve specific screenshots whenever you need them.",
            permissionType = PermissionType.STORAGE,
            imageResId = R.drawable.ic_onboarding_storage
        ),
        OnboardingPage(
            title = "Notification Permission",
            description = "To analyze your screenshots in the background, we need notification permission. This will also allow us to show you the progress of the number of screenshots analyzed.",
            permissionType = PermissionType.NOTIFICATION,
            imageResId = R.drawable.ic_onboarding_notification
        )
    )

    // Pager state
    val pagerState = rememberPagerState()

    // Permission request launchers
    val storagePermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            // Permission granted, move to next page
            scope.launch {
                if (pagerState.currentPage < pages.size - 1) {
                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                }
            }
        }
    }

    val notificationPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        // Even if not granted, we'll complete onboarding
        // Mark onboarding as completed
        sharedPrefsManager.setOnboardingCompleted(true)
        // Navigate to home screen
        navController.navigate("home") {
            popUpTo("onboarding") { inclusive = true }
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Pager for onboarding pages
            HorizontalPager(
                state = pagerState,
                pageCount = pages.size,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) { page ->
                OnboardingPageContent(
                    page = pages[page],
                    pageIndex = page
                )
            }

            // Bottom navigation (dots and button)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // Page indicator dots
                Row(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    repeat(pages.size) { index ->
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(
                                    if (pagerState.currentPage == index)
                                        MaterialTheme.colorScheme.primary
                                    else
                                        MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f)
                                )
                        )
                    }
                }

                // Next/Continue button
                val isLastPage = pagerState.currentPage == pages.size - 1
                val buttonText = if (isLastPage) "Continue" else "Next"

                Button(
                    onClick = {
                        val currentPage = pages[pagerState.currentPage]
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
                                    // For older versions, just complete onboarding
                                    sharedPrefsManager.setOnboardingCompleted(true)
                                    navController.navigate("home") {
                                        popUpTo("onboarding") { inclusive = true }
                                    }
                                }
                            }
                            null -> {
                                // Just go to next page
                                scope.launch {
                                    if (pagerState.currentPage < pages.size - 1) {
                                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                                    }
                                }
                            }
                        }
                    },
                    modifier = Modifier.align(Alignment.CenterEnd)
                ) {
                    Text(buttonText)
                }
            }
        }
    }
}

@Composable
fun OnboardingPageContent(
    page: OnboardingPage,
    pageIndex: Int
) {
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(pageIndex) {
        visible = false
        // Small delay before fading in
        kotlinx.coroutines.delay(100)
        visible = true
    }

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(animationSpec = tween(300)),
        exit = fadeOut(animationSpec = tween(300))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Illustration
            Image(
                painter = painterResource(id = page.imageResId),
                contentDescription = page.title,
                modifier = Modifier
                    .size(240.dp)
                    .padding(16.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Title
            Text(
                text = page.title,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Description
            Text(
                text = page.description,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f),
                modifier = Modifier.padding(horizontal = 16.dp)
            )
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
