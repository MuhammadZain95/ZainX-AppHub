package com.example.ui.screens

import android.content.Intent
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.ZainXRepository
import com.example.model.AppItem
import com.example.ui.components.PillButton
import com.example.ui.components.ReportAppDialog
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.NeonEmerald
import com.example.ui.theme.NeonPurple

@Composable
fun AppDetailScreen(
    app: AppItem,
    isWishlisted: Boolean,
    isFollowingDev: Boolean,
    onBack: () -> Unit,
    onDownloadClick: () -> Unit,
    onToggleWishlist: () -> Unit,
    onToggleFollowDev: () -> Unit
) {
    BackHandler { onBack() }
    val context = LocalContext.current

    var showReportDialog by remember { mutableStateOf(false) }
    var showWriteReviewDialog by remember { mutableStateOf(false) }
    var reviewRating by remember { mutableIntStateOf(5) }
    var reviewComment by remember { mutableStateOf("") }
    var shareToastMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(shareToastMessage) {
        if (shareToastMessage != null) {
            kotlinx.coroutines.delay(2500)
            shareToastMessage = null
        }
    }

    if (showReportDialog) {
        ReportAppDialog(
            app = app,
            isOpen = true,
            onDismiss = { showReportDialog = false }
        )
    }

    // Write Review Dialog
    if (showWriteReviewDialog) {
        AlertDialog(
            onDismissRequest = { showWriteReviewDialog = false },
            containerColor = Color(0xFF0F152E),
            title = {
                Text(
                    text = "Review ${app.name}",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = Color.White
                )
            },
            text = {
                Column {
                    Text(
                        text = "Rate your experience (1 to 5 stars):",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFFCBD5E1)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        (1..5).forEach { star ->
                            Icon(
                                imageVector = if (star <= reviewRating) Icons.Default.Star else Icons.Default.StarBorder,
                                contentDescription = "$star stars",
                                tint = Color(0xFFFFB020),
                                modifier = Modifier
                                    .size(36.dp)
                                    .clickable { reviewRating = star }
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(14.dp))
                    OutlinedTextField(
                        value = reviewComment,
                        onValueChange = { reviewComment = it },
                        label = { Text("Write your thoughts...") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(110.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = NeonCyan,
                            unfocusedBorderColor = Color(0xFF263366)
                        )
                    )
                }
            },
            confirmButton = {
                PillButton(
                    text = "Submit Review",
                    onClick = {
                        if (reviewComment.isNotBlank()) {
                            ZainXRepository.addReview(app.id, reviewRating, reviewComment)
                            reviewComment = ""
                            showWriteReviewDialog = false
                        }
                    },
                    isPrimary = true
                )
            },
            dismissButton = {
                TextButton(onClick = { showWriteReviewDialog = false }) {
                    Text("Cancel", color = Color(0xFF94A3B8))
                }
            }
        )
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 14.dp),
        contentPadding = PaddingValues(bottom = 36.dp)
    ) {
        // TOP APP BAR
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    IconButton(onClick = onToggleWishlist) {
                        Icon(
                            imageVector = if (isWishlisted) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Favorite",
                            tint = if (isWishlisted) Color(0xFFFF3D8B) else Color.White
                        )
                    }

                    IconButton(
                        onClick = {
                            val sendIntent = Intent().apply {
                                action = Intent.ACTION_SEND
                                putExtra(Intent.EXTRA_TEXT, "Check out ${app.name} on ZainX AppHub: https://zainx.io/app/${app.id}")
                                type = "text/plain"
                            }
                            context.startActivity(Intent.createChooser(sendIntent, "Share ${app.name}"))
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Share",
                            tint = Color.White
                        )
                    }

                    IconButton(onClick = { showReportDialog = true }) {
                        Icon(
                            imageVector = Icons.Default.Flag,
                            contentDescription = "Report",
                            tint = Color(0xFF94A3B8)
                        )
                    }
                }
            }
        }

        // HERO APP INFO CARD
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color(0xFF0F152E))
                    .border(1.dp, Color(0xFF1E284D), RoundedCornerShape(20.dp))
                    .padding(18.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(72.dp)
                                .clip(RoundedCornerShape(18.dp))
                                .background(Color(app.iconColor)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = app.iconSymbol,
                                fontWeight = FontWeight.Black,
                                fontSize = 28.sp,
                                color = Color.Black
                            )
                        }

                        Column(modifier = Modifier.weight(1f)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Text(
                                    text = app.name,
                                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Black),
                                    color = Color.White
                                )
                                if (app.isVerified) {
                                    Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = "Verified",
                                        tint = NeonEmerald,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }

                            Text(
                                text = app.developerName,
                                style = MaterialTheme.typography.bodyMedium,
                                color = NeonCyan
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Box(
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .background(Color(app.platform.badgeColorHex).copy(alpha = 0.2f))
                                        .padding(horizontal = 8.dp, vertical = 3.dp)
                                ) {
                                    Text(
                                        text = app.platform.displayName,
                                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                        color = Color(app.platform.badgeColorHex)
                                    )
                                }

                                Box(
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .background(Color(0xFF1E284D))
                                        .padding(horizontal = 8.dp, vertical = 3.dp)
                                ) {
                                    Text(
                                        text = "v${app.version}",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = Color(0xFFCBD5E1)
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    // Stats row
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFF090D1C))
                            .padding(vertical = 10.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "★ ${app.rating}",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = Color(0xFFFFB020)
                            )
                            Text(
                                text = "${app.ratingCount} reviews",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color(0xFF94A3B8)
                            )
                        }

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "${app.downloadCount / 1000}K+",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = NeonCyan
                            )
                            Text(
                                text = "Downloads",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color(0xFF94A3B8)
                            )
                        }

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "${app.fileSizeMb} MB",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = Color.White
                            )
                            Text(
                                text = "Package Size",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color(0xFF94A3B8)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    // DOWNLOAD BUTTON
                    PillButton(
                        text = "Download ${app.platform.extension.uppercase()} (${app.fileSizeMb} MB)",
                        onClick = onDownloadClick,
                        modifier = Modifier.fillMaxWidth(),
                        isPrimary = true,
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Download,
                                contentDescription = null,
                                tint = Color.Black
                            )
                        }
                    )
                }
            }
        }

        // SCREENSHOTS CAROUSEL
        item {
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Preview & Screenshots",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = Color.White
            )

            Spacer(modifier = Modifier.height(10.dp))

            LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                items(3) { idx ->
                    Box(
                        modifier = Modifier
                            .width(240.dp)
                            .height(135.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .background(Color(0xFF141A38))
                            .border(1.dp, Color(0xFF263366), RoundedCornerShape(14.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = Icons.Default.PhoneAndroid,
                                contentDescription = null,
                                tint = NeonCyan.copy(alpha = 0.5f),
                                modifier = Modifier.size(36.dp)
                            )
                            Text(
                                text = "${app.name} Mockup #${idx + 1}",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color(0xFF94A3B8)
                            )
                        }
                    }
                }
            }
        }

        // ABOUT THIS SOFTWARE
        item {
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "About this application",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = Color.White
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = app.description,
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFFCBD5E1),
                lineHeight = 22.sp
            )
        }

        // TECHNICAL SPECIFICATIONS
        item {
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Technical Specifications",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = Color.White
            )

            Spacer(modifier = Modifier.height(10.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(Color(0xFF0F152E))
                    .padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Category", color = Color(0xFF94A3B8), style = MaterialTheme.typography.bodySmall)
                    Text(app.category.displayName, color = Color.White, style = MaterialTheme.typography.bodySmall)
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Compatibility", color = Color(0xFF94A3B8), style = MaterialTheme.typography.bodySmall)
                    Text(app.minOs, color = Color.White, style = MaterialTheme.typography.bodySmall)
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Released", color = Color(0xFF94A3B8), style = MaterialTheme.typography.bodySmall)
                    Text(app.releaseDate, color = Color.White, style = MaterialTheme.typography.bodySmall)
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("SHA-256", color = Color(0xFF94A3B8), style = MaterialTheme.typography.bodySmall)
                    Text(app.checksumSha256.take(16) + "...", color = NeonCyan, style = MaterialTheme.typography.bodySmall, fontFamily = FontFamily.Monospace)
                }
            }
        }

        // VERSION HISTORY & CHANGELOG
        item {
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Changelog & Version History",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = Color.White
            )

            Spacer(modifier = Modifier.height(10.dp))

            app.versions.forEach { ver ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFF0F152E))
                        .padding(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Version ${ver.versionNumber}",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = NeonCyan
                        )
                        Text(
                            text = ver.releaseDate,
                            style = MaterialTheme.typography.labelSmall,
                            color = Color(0xFF94A3B8)
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = ver.changelog,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFFCBD5E1)
                    )
                }
            }
        }

        // DEVELOPER CARD
        item {
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Developer",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = Color.White
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(Color(0xFF0F152E))
                    .padding(14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(NeonPurple),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = app.developerName.take(1),
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Column {
                        Text(
                            text = app.developerName,
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = Color.White
                        )
                        Text(
                            text = "Verified Publisher",
                            style = MaterialTheme.typography.labelSmall,
                            color = NeonEmerald
                        )
                    }
                }

                PillButton(
                    text = if (isFollowingDev) "Following" else "Follow",
                    onClick = onToggleFollowDev,
                    isPrimary = !isFollowingDev
                )
            }
        }

        // RATINGS & REVIEWS
        item {
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Ratings & User Reviews",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = Color.White
                )

                Text(
                    text = "Write a Review",
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                    color = NeonCyan,
                    modifier = Modifier.clickable { showWriteReviewDialog = true }
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            if (app.reviews.isEmpty()) {
                Text(
                    text = "No reviews yet. Be the first to leave feedback!",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF94A3B8)
                )
            } else {
                app.reviews.forEach { rev ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFF0F152E))
                            .padding(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = rev.userName,
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                color = Color.White
                            )
                            Row {
                                repeat(rev.rating) {
                                    Icon(
                                        imageVector = Icons.Default.Star,
                                        contentDescription = null,
                                        tint = Color(0xFFFFB020),
                                        modifier = Modifier.size(14.dp)
                                    )
                                }
                            }
                        }
                        Text(
                            text = rev.date,
                            style = MaterialTheme.typography.labelSmall,
                            color = Color(0xFF64748B)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = rev.comment,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFFCBD5E1)
                        )
                    }
                }
            }
        }
    }
}
