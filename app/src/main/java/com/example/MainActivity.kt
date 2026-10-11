package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.ZainXRepository
import com.example.model.AppItem
import com.example.ui.components.*
import com.example.ui.screens.*
import com.example.ui.theme.NeonEmerald
import com.example.ui.theme.ZainXAppHubTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var isDarkTheme by remember { mutableStateOf(true) }

            ZainXAppHubTheme(darkTheme = isDarkTheme) {
                ZainXAppContent(
                    isDarkTheme = isDarkTheme,
                    onToggleTheme = { isDarkTheme = !isDarkTheme }
                )
            }
        }
    }
}

@Composable
fun ZainXAppContent(
    isDarkTheme: Boolean,
    onToggleTheme: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    // Repository states
    val currentUser by ZainXRepository.currentUser.collectAsState()
    val allApps by ZainXRepository.apps.collectAsState()
    val wishlist by ZainXRepository.wishlist.collectAsState()
    val followingDevs by ZainXRepository.followingDevelopers.collectAsState()
    val reports by ZainXRepository.reports.collectAsState()
    val maintenanceMode by ZainXRepository.maintenanceMode.collectAsState()

    // Navigation and Modal states
    var currentScreen by remember { mutableStateOf(NavScreen.LANDING) }
    var selectedApp by remember { mutableStateOf<AppItem?>(null) }
    var downloadingApp by remember { mutableStateOf<AppItem?>(null) }

    var showAuthDialog by remember { mutableStateOf(false) }
    var showPricingDialog by remember { mutableStateOf(false) }
    var showAboutDialog by remember { mutableStateOf(false) }

    // Handle back press if inside detail screen or secondary tab
    BackHandler(enabled = selectedApp != null || currentScreen != NavScreen.LANDING) {
        if (selectedApp != null) {
            selectedApp = null
        } else if (currentScreen != NavScreen.LANDING) {
            currentScreen = NavScreen.LANDING
        }
    }

    // Modal dialogs
    if (downloadingApp != null) {
        DownloadSecurityModal(
            app = downloadingApp!!,
            onDismiss = { downloadingApp = null },
            onDownloadComplete = {
                ZainXRepository.recordDownload(downloadingApp!!.id)
                coroutineScope.launch {
                    snackbarHostState.showSnackbar("Downloaded ${downloadingApp!!.name} successfully!")
                }
            }
        )
    }

    AuthDialog(
        isOpen = showAuthDialog,
        onDismiss = { showAuthDialog = false }
    )

    PricingDialog(
        isOpen = showPricingDialog,
        onDismiss = { showPricingDialog = false }
    )

    AboutDialog(
        isOpen = showAboutDialog,
        onDismiss = { showAboutDialog = false }
    )

    Box(modifier = Modifier.fillMaxSize()) {
        // Holographic ambient backdrop
        HolographicAmbientBackground(isDark = isDarkTheme)

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = Color.Transparent,
            snackbarHost = { SnackbarHost(snackbarHostState) },
            topBar = {
                Column {
                    if (maintenanceMode) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(0xFFFFB020))
                                .padding(vertical = 4.dp),
                            contentAlignment = androidx.compose.ui.Alignment.Center
                        ) {
                            Text(
                                text = "⚠️ Maintenance Mode Active: Some mirror servers may experience latency",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                color = Color.Black
                            )
                        }
                    }

                    ZainXNavbar(
                        currentScreen = currentScreen,
                        onNavigate = { screen ->
                            selectedApp = null
                            currentScreen = screen
                        },
                        currentUser = currentUser,
                        isDarkTheme = isDarkTheme,
                        onToggleTheme = onToggleTheme,
                        onOpenAuth = { showAuthDialog = true },
                        onOpenPricing = { showPricingDialog = true },
                        onOpenAbout = { showAboutDialog = true }
                    )
                }
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                // If an app is selected, render AppDetailScreen
                if (selectedApp != null) {
                    val currentAppDetails = allApps.find { it.id == selectedApp!!.id } ?: selectedApp!!
                    AppDetailScreen(
                        app = currentAppDetails,
                        isWishlisted = wishlist.contains(currentAppDetails.id),
                        isFollowingDev = followingDevs.contains(currentAppDetails.developerId),
                        onBack = { selectedApp = null },
                        onDownloadClick = { downloadingApp = currentAppDetails },
                        onToggleWishlist = { ZainXRepository.toggleWishlist(currentAppDetails.id) },
                        onToggleFollowDev = { ZainXRepository.toggleFollowDeveloper(currentAppDetails.developerId) }
                    )
                } else {
                    AnimatedContent(
                        targetState = currentScreen,
                        label = "screen_transition"
                    ) { targetScreen ->
                        when (targetScreen) {
                            NavScreen.LANDING -> {
                                LandingScreen(
                                    featuredApps = allApps.filter { it.isFeatured },
                                    onExploreStore = { currentScreen = NavScreen.STORE },
                                    onUploadClick = { currentScreen = NavScreen.UPLOAD },
                                    onAppClick = { app -> selectedApp = app },
                                    onOpenPricing = { showPricingDialog = true },
                                    onOpenAbout = { showAboutDialog = true }
                                )
                            }
                            NavScreen.STORE -> {
                                StoreScreen(
                                    apps = allApps,
                                    onAppClick = { app -> selectedApp = app },
                                    onQuickDownload = { app -> downloadingApp = app }
                                )
                            }
                            NavScreen.UPLOAD -> {
                                UploadScreen(
                                    onPublishSuccess = {
                                        currentScreen = NavScreen.DASHBOARD
                                        coroutineScope.launch {
                                            snackbarHostState.showSnackbar("Software package published live to ZainX AppHub!")
                                        }
                                    }
                                )
                            }
                            NavScreen.DASHBOARD -> {
                                DashboardScreen(
                                    userApps = allApps.filter { it.developerId == currentUser.id || it.developerName == currentUser.name },
                                    onAppClick = { app -> selectedApp = app },
                                    onUploadNewClick = { currentScreen = NavScreen.UPLOAD }
                                )
                            }
                            NavScreen.PROFILE -> {
                                ProfileScreen(
                                    user = currentUser,
                                    userApps = allApps.filter { it.developerId == currentUser.id || it.developerName == currentUser.name },
                                    wishlistApps = allApps.filter { wishlist.contains(it.id) },
                                    onAppClick = { app -> selectedApp = app },
                                    onOpenSettings = { currentScreen = NavScreen.SETTINGS }
                                )
                            }
                            NavScreen.SETTINGS -> {
                                SettingsScreen(
                                    user = currentUser,
                                    isDarkTheme = isDarkTheme,
                                    onToggleTheme = onToggleTheme,
                                    onSignOut = {
                                        showAuthDialog = true
                                    }
                                )
                            }
                            NavScreen.ADMIN -> {
                                AdminScreen(
                                    apps = allApps,
                                    reports = reports,
                                    onAppClick = { app -> selectedApp = app }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
