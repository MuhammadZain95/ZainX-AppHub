package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.AppItem
import com.example.ui.components.*
import com.example.ui.theme.*

@Composable
fun LandingScreen(
    featuredApps: List<AppItem>,
    onExploreStore: () -> Unit,
    onUploadClick: () -> Unit,
    onAppClick: (AppItem) -> Unit,
    onOpenPricing: () -> Unit,
    onOpenAbout: () -> Unit
) {
    var expandedFaqIndex by remember { mutableIntStateOf(-1) }

    val faqs = listOf(
        "What software formats are supported on ZainX AppHub?" to
                "ZainX supports Android APKs, Google Play App Bundles (AAB), Windows Executables (EXE), Windows Installers (MSI), and Universal Cross-Platform archives (ZIP).",
        "How does the automated security verification work?" to
                "Every uploaded binary is automatically analyzed by the ZainX Cloud Sandboxing Engine and cross-checked against 70+ antivirus engines. Files are stamped with an immutable SHA-256 hash.",
        "Can any developer publish software?" to
                "Yes! Anyone can sign up for free and publish up to 5 community applications. Verified developers receive advanced download analytics and priority CDN caching.",
        "Are downloads free for end users?" to
                "All free and open-source applications hosted on ZainX AppHub are 100% free with unlimited direct download bandwidth."
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 32.dp)
    ) {
        // HERO SECTION
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Floating 3D holographic banner card (match image 1 reference)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(210.dp)
                            .clip(RoundedCornerShape(24.dp))
                            .border(
                                BorderStroke(
                                    1.dp,
                                    Brush.linearGradient(
                                        listOf(NeonCyan.copy(alpha = 0.6f), NeonPurple.copy(alpha = 0.6f))
                                    )
                                ),
                                RoundedCornerShape(24.dp)
                            )
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.zainx_hero_banner),
                            contentDescription = "ZainX 3D Holographic Visuals",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )

                        // Subtle dark gradient vignette over hero banner
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(
                                            Color.Transparent,
                                            DeepNavyBackground.copy(alpha = 0.75f)
                                        )
                                    )
                                )
                        )

                        // Floating badge inside banner
                        Row(
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .padding(12.dp)
                                .clip(CircleShape)
                                .background(Color(0xB30B0F24))
                                .border(1.dp, NeonEmerald.copy(alpha = 0.5f), CircleShape)
                                .padding(horizontal = 12.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(NeonEmerald)
                            )
                            Text(
                                text = "Next-Gen App Distribution",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                color = NeonEmerald
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Huge Bold Sans-Serif Headline
                    Text(
                        text = "Where apps become tangible.",
                        style = MaterialTheme.typography.displayLarge.copy(
                            fontSize = 32.sp,
                            lineHeight = 38.sp,
                            fontWeight = FontWeight.Black,
                            letterSpacing = (-0.5).sp
                        ),
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "The decentralized platform to discover, verify, and download software across mobile and desktop. Android APK/AAB, Windows EXE/MSI, and universal ZIP packages.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF94A3B8),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 16.dp),
                        lineHeight = 20.sp
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Pill buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        PillButton(
                            text = "Learn more",
                            onClick = onExploreStore,
                            isPrimary = true,
                            trailingIcon = {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                    contentDescription = null,
                                    tint = Color.Black,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        PillButton(
                            text = "Publish App",
                            onClick = onUploadClick,
                            isPrimary = false,
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.CloudUpload,
                                    contentDescription = null,
                                    tint = NeonCyan,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        )
                    }
                }
            }
        }

        // STATS COUNTERS
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                val stats = listOf(
                    Triple("1.4M+", "Downloads", NeonCyan),
                    Triple("5,200+", "Apps Live", NeonPurple),
                    Triple("1,850+", "Developers", NeonEmerald),
                    Triple("100%", "Safe Scans", Color(0xFFFFB020))
                )

                stats.forEach { stat ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color(0xFF0F152E))
                            .border(1.dp, Color(0xFF1E274A), RoundedCornerShape(16.dp))
                            .padding(vertical = 12.dp, horizontal = 4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = stat.first,
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Black),
                                color = stat.third
                            )
                            Text(
                                text = stat.second,
                                style = MaterialTheme.typography.labelSmall,
                                color = Color(0xFF94A3B8)
                            )
                        }
                    }
                }
            }
        }

        // FEATURED APPS CAROUSEL
        item {
            Spacer(modifier = Modifier.height(28.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = NeonCyan,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "Featured Software",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = Color.White
                    )
                }

                Text(
                    text = "See All",
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                    color = NeonCyan,
                    modifier = Modifier.clickable { onExploreStore() }
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                items(featuredApps) { app ->
                    FeaturedAppCard(app = app, onClick = { onAppClick(app) })
                }
            }
        }

        // HOW IT WORKS SECTION
        item {
            Spacer(modifier = Modifier.height(32.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "HOW ZAINX WORKS",
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                    color = NeonEmerald
                )
                Text(
                    text = "Frictionless from publish to download",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(14.dp))

                val steps = listOf(
                    Triple("1. Instant Upload", "Drag & drop APK, EXE, AAB, MSI, or ZIP files up to 500MB with zero waiting periods.", Icons.Default.CloudUpload),
                    Triple("2. Sandboxed Security", "Automated threat detection, signature integrity verification, and SHA-256 stamping.", Icons.Default.Shield),
                    Triple("3. Direct High-Speed Download", "End users grab native binaries worldwide through our geo-distributed multi-CDN network.", Icons.Default.Speed)
                )

                steps.forEach { step ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color(0xFF0D1226))
                            .border(1.dp, Color(0xFF1E284D), RoundedCornerShape(16.dp))
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(CircleShape)
                                .background(NeonCyan.copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(step.third, contentDescription = null, tint = NeonCyan, modifier = Modifier.size(24.dp))
                        }

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = step.first,
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                color = Color.White
                            )
                            Text(
                                text = step.second,
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFF94A3B8)
                            )
                        }
                    }
                }
            }
        }

        // FAQ ACCORDION
        item {
            Spacer(modifier = Modifier.height(32.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "FREQUENTLY ASKED QUESTIONS",
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                    color = NeonPurple
                )
                Text(
                    text = "Everything you need to know",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(14.dp))

                faqs.forEachIndexed { index, pair ->
                    val isExpanded = expandedFaqIndex == index
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .background(Color(0xFF0F152E))
                            .border(1.dp, Color(0xFF1E274A), RoundedCornerShape(14.dp))
                            .clickable {
                                expandedFaqIndex = if (isExpanded) -1 else index
                            }
                            .padding(14.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = pair.first,
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
                                color = Color.White,
                                modifier = Modifier.weight(1f)
                            )
                            Icon(
                                imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                                contentDescription = null,
                                tint = NeonCyan
                            )
                        }

                        AnimatedVisibility(visible = isExpanded) {
                            Column {
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = pair.second,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color(0xFFCBD5E1),
                                    lineHeight = 18.sp
                                )
                            }
                        }
                    }
                }
            }
        }

        // CTA BANNER
        item {
            Spacer(modifier = Modifier.height(32.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        Brush.linearGradient(
                            listOf(Color(0xFF192552), Color(0xFF28144D))
                        )
                    )
                    .border(
                        BorderStroke(1.dp, Brush.horizontalGradient(listOf(NeonCyan, NeonPurple))),
                        RoundedCornerShape(20.dp)
                    )
                    .padding(20.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Ready to launch your application?",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Black),
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Join 1,800+ independent developers distributing their creations worldwide.",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFFCBD5E1),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    PillButton(
                        text = "Publish App Now",
                        onClick = onUploadClick,
                        isPrimary = true
                    )
                }
            }
        }

        // FOOTER
        item {
            Spacer(modifier = Modifier.height(36.dp))
            ZainXFooter(
                onOpenAbout = onOpenAbout,
                onOpenPricing = onOpenPricing
            )
        }
    }
}

@Composable
fun FeaturedAppCard(
    app: AppItem,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .width(220.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(Color(0xFF0F152E))
            .border(1.dp, Color(0xFF1E284D), RoundedCornerShape(18.dp))
            .clickable(onClick = onClick)
            .padding(14.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(app.iconColor)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = app.iconSymbol,
                        fontWeight = FontWeight.Black,
                        fontSize = 18.sp,
                        color = Color.Black
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color(app.platform.badgeColorHex).copy(alpha = 0.2f))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = app.platform.extension.uppercase().removePrefix("."),
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = Color(app.platform.badgeColorHex)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = app.name,
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                color = Color.White,
                maxLines = 1
            )

            Text(
                text = app.developerName,
                style = MaterialTheme.typography.labelSmall,
                color = Color(0xFF94A3B8),
                maxLines = 1
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = app.tagline,
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFFCBD5E1),
                maxLines = 2,
                lineHeight = 16.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = Color(0xFFFFB020),
                        modifier = Modifier.size(14.dp)
                    )
                    Text(
                        text = "${app.rating}",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = Color.White
                    )
                }

                Text(
                    text = "${app.downloadCount / 1000}k DL",
                    style = MaterialTheme.typography.labelSmall,
                    color = NeonCyan
                )
            }
        }
    }
}
