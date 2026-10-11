package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.ZainXRepository
import com.example.model.AppItem
import com.example.model.AppStatus
import com.example.ui.components.DownloadStatsChart
import com.example.ui.components.PillButton
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.NeonEmerald
import com.example.ui.theme.NeonPurple

@Composable
fun DashboardScreen(
    userApps: List<AppItem>,
    onAppClick: (AppItem) -> Unit,
    onUploadNewClick: () -> Unit
) {
    var editingApp by remember { mutableStateOf<AppItem?>(null) }
    var versionDialogApp by remember { mutableStateOf<AppItem?>(null) }

    // Dialog for uploading new version
    if (versionDialogApp != null) {
        val targetApp = versionDialogApp!!
        var newVersionStr by remember { mutableStateOf("v2.0.0") }
        var changelogText by remember { mutableStateOf("") }

        AlertDialog(
            onDismissRequest = { versionDialogApp = null },
            containerColor = Color(0xFF0F152E),
            title = {
                Text(
                    text = "Release New Version for ${targetApp.name}",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = Color.White
                )
            },
            text = {
                Column {
                    OutlinedTextField(
                        value = newVersionStr,
                        onValueChange = { newVersionStr = it },
                        label = { Text("Version String (e.g. 2.1.0)") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(
                        value = changelogText,
                        onValueChange = { changelogText = it },
                        label = { Text("Changelog / Release Highlights") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                    )
                }
            },
            confirmButton = {
                PillButton(
                    text = "Release Version",
                    onClick = {
                        if (newVersionStr.isNotBlank()) {
                            ZainXRepository.addAppVersion(targetApp.id, newVersionStr, changelogText, targetApp.fileSizeMb)
                            versionDialogApp = null
                        }
                    },
                    isPrimary = true
                )
            },
            dismissButton = {
                TextButton(onClick = { versionDialogApp = null }) {
                    Text("Cancel", color = Color(0xFF94A3B8))
                }
            }
        )
    }

    // Dialog for editing app
    if (editingApp != null) {
        val app = editingApp!!
        var editName by remember { mutableStateOf(app.name) }
        var editTagline by remember { mutableStateOf(app.tagline) }
        var editDesc by remember { mutableStateOf(app.description) }

        AlertDialog(
            onDismissRequest = { editingApp = null },
            containerColor = Color(0xFF0F152E),
            title = {
                Text("Edit App Details", color = Color.White, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
            },
            text = {
                Column {
                    OutlinedTextField(
                        value = editName,
                        onValueChange = { editName = it },
                        label = { Text("App Name") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = editTagline,
                        onValueChange = { editTagline = it },
                        label = { Text("Tagline") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = editDesc,
                        onValueChange = { editDesc = it },
                        label = { Text("Description") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(90.dp)
                    )
                }
            },
            confirmButton = {
                PillButton(
                    text = "Save Changes",
                    onClick = {
                        ZainXRepository.updateApp(app.id, editName, editTagline, editDesc, app.category)
                        editingApp = null
                    },
                    isPrimary = true
                )
            },
            dismissButton = {
                TextButton(onClick = { editingApp = null }) {
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
        item {
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Developer Dashboard",
                        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Black),
                        color = Color.White
                    )
                    Text(
                        text = "Live telemetry, downloads, and binary release pipeline.",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF94A3B8)
                    )
                }

                IconButton(
                    onClick = onUploadNewClick,
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(NeonCyan)
                        .size(38.dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add App", tint = Color.Black)
                }
            }

            Spacer(modifier = Modifier.height(18.dp))
        }

        // STATS OVERVIEW CARDS
        item {
            val totalDownloads = userApps.sumOf { it.downloadCount }
            val avgRating = if (userApps.isNotEmpty()) {
                (userApps.map { it.rating }.average() * 10).toInt() / 10f
            } else 5.0f

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color(0xFF0F152E))
                        .border(1.dp, Color(0xFF1E284D), RoundedCornerShape(14.dp))
                        .padding(14.dp)
                ) {
                    Column {
                        Text("Active Packages", style = MaterialTheme.typography.labelSmall, color = Color(0xFF94A3B8))
                        Text("${userApps.size}", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Black), color = NeonCyan)
                    }
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color(0xFF0F152E))
                        .border(1.dp, Color(0xFF1E284D), RoundedCornerShape(14.dp))
                        .padding(14.dp)
                ) {
                    Column {
                        Text("Total Downloads", style = MaterialTheme.typography.labelSmall, color = Color(0xFF94A3B8))
                        Text("${totalDownloads / 1000}K+", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Black), color = NeonEmerald)
                    }
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color(0xFF0F152E))
                        .border(1.dp, Color(0xFF1E284D), RoundedCornerShape(14.dp))
                        .padding(14.dp)
                ) {
                    Column {
                        Text("Average Rating", style = MaterialTheme.typography.labelSmall, color = Color(0xFF94A3B8))
                        Text("★ $avgRating", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Black), color = Color(0xFFFFB020))
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))
        }

        // DOWNLOAD TRAFFIC CHART
        item {
            DownloadStatsChart()
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "My Published Packages (${userApps.size})",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = Color.White
            )
            Spacer(modifier = Modifier.height(10.dp))
        }

        if (userApps.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("You haven't published any applications yet.", color = Color(0xFF94A3B8), style = MaterialTheme.typography.bodyMedium)
                        Spacer(modifier = Modifier.height(12.dp))
                        PillButton(text = "Publish Your First App", onClick = onUploadNewClick)
                    }
                }
            }
        } else {
            items(userApps) { app ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFF0F152E))
                        .border(1.dp, Color(0xFF1E284D), RoundedCornerShape(16.dp))
                        .padding(14.dp)
                ) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(46.dp)
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

                            Column(modifier = Modifier.weight(1f)) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text(
                                        text = app.name,
                                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                        color = Color.White
                                    )

                                    // Status Badge
                                    Box(
                                        modifier = Modifier
                                            .clip(CircleShape)
                                            .background(
                                                when (app.status) {
                                                    AppStatus.PUBLISHED -> NeonEmerald.copy(alpha = 0.2f)
                                                    AppStatus.IN_REVIEW -> Color(0xFFFFB020).copy(alpha = 0.2f)
                                                    AppStatus.DRAFT -> Color(0xFF64748B).copy(alpha = 0.2f)
                                                }
                                            )
                                            .padding(horizontal = 8.dp, vertical = 2.dp)
                                    ) {
                                        Text(
                                            text = app.status.displayName,
                                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                            color = when (app.status) {
                                                AppStatus.PUBLISHED -> NeonEmerald
                                                AppStatus.IN_REVIEW -> Color(0xFFFFB020)
                                                AppStatus.DRAFT -> Color(0xFFCBD5E1)
                                            },
                                            fontSize = 10.sp
                                        )
                                    }
                                }

                                Text(
                                    text = "${app.platform.displayName} • v${app.version} • ${app.downloadCount} downloads",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color(0xFF94A3B8)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Action Buttons row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            TextButton(onClick = { versionDialogApp = app }) {
                                Icon(Icons.Default.Upload, contentDescription = null, tint = NeonCyan, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("New Version", color = NeonCyan, style = MaterialTheme.typography.labelSmall)
                            }

                            TextButton(onClick = { editingApp = app }) {
                                Icon(Icons.Default.Edit, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Edit", color = Color.White, style = MaterialTheme.typography.labelSmall)
                            }

                            IconButton(onClick = { ZainXRepository.deleteApp(app.id) }) {
                                Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color(0xFFFF4864), modifier = Modifier.size(18.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}
