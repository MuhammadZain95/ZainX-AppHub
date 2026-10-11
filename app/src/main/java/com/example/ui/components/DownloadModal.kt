package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.model.AppItem
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.NeonEmerald
import com.example.ui.theme.NeonPurple
import kotlinx.coroutines.delay

@Composable
fun DownloadSecurityModal(
    app: AppItem,
    onDismiss: () -> Unit,
    onDownloadComplete: () -> Unit
) {
    var isDownloading by remember { mutableStateOf(false) }
    var downloadProgress by remember { mutableFloatStateOf(0f) }
    var downloadFinished by remember { mutableStateOf(false) }

    LaunchedEffect(isDownloading) {
        if (isDownloading) {
            downloadProgress = 0f
            while (downloadProgress < 1f) {
                delay(70)
                downloadProgress += 0.045f
                if (downloadProgress >= 1f) {
                    downloadProgress = 1f
                    downloadFinished = true
                    onDownloadComplete()
                }
            }
        }
    }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = Color(0xFF0D1226),
            border = BorderStroke(1.dp, NeonCyan.copy(alpha = 0.4f)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(22.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(NeonEmerald.copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Security,
                                contentDescription = null,
                                tint = NeonEmerald,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Text(
                            text = "Security Verification",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = Color.White
                        )
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = Color(0xFF94A3B8)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // App card preview
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFF141A36))
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .clip(RoundedCornerShape(10.dp))
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
                        Text(
                            text = app.name,
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = Color.White
                        )
                        Text(
                            text = "${app.platform.displayName} • v${app.version} • ${app.fileSizeMb} MB",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF94A3B8)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Malware scanner report
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0x2000E5A0))
                        .border(1.dp, NeonEmerald.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = NeonEmerald,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "VirusTotal Scan: 0/72 Threats Detected",
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                            color = NeonEmerald
                        )
                    }
                    Text(
                        text = "Package signature verified by ZainX Cloud Sandboxing Engine. Binary matches official developer build hash.",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFFE2E8F0)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // SHA256 Checksum preview
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFF090D1C))
                        .padding(8.dp)
                ) {
                    Text(
                        text = "SHA-256 CHECKSUM",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = Color(0xFF64748B)
                    )
                    Text(
                        text = app.checksumSha256,
                        style = MaterialTheme.typography.labelSmall.copy(fontFamily = FontFamily.Monospace),
                        color = NeonCyan,
                        maxLines = 1
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Mandatory safety disclaimer
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = null,
                        tint = Color(0xFFFFB020),
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = "Always inspect permissions before installation. Do not install unknown software from non-verified mirrors.",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF94A3B8)
                    )
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Download Progress / Action
                if (isDownloading) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        LinearProgressIndicator(
                            progress = { downloadProgress },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .clip(CircleShape),
                            color = NeonCyan,
                            trackColor = Color(0xFF1E284D)
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            val downloadedMb = ((app.fileSizeMb * downloadProgress) * 10).toInt() / 10.0
                            Text(
                                text = if (!downloadFinished) "Downloading: ${downloadedMb} MB of ${app.fileSizeMb} MB" else "Download Complete!",
                                style = MaterialTheme.typography.bodySmall,
                                color = if (downloadFinished) NeonEmerald else Color(0xFFCBD5E1)
                            )
                            Text(
                                text = "${(downloadProgress * 100).toInt()}%",
                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                color = NeonCyan
                            )
                        }

                        if (downloadFinished) {
                            Text(
                                text = "File saved to your Downloads directory. Follow system instructions to launch.",
                                style = MaterialTheme.typography.bodySmall,
                                color = NeonEmerald
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            PillButton(
                                text = "Done",
                                onClick = onDismiss,
                                modifier = Modifier.fillMaxWidth(),
                                isPrimary = true
                            )
                        }
                    }
                } else {
                    PillButton(
                        text = "Accept & Download (${app.fileSizeMb} MB)",
                        onClick = { isDownloading = true },
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
    }
}
