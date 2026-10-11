package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import com.example.model.AppCategory
import com.example.model.AppPlatform
import com.example.model.AppStatus
import com.example.ui.components.PillButton
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.NeonEmerald
import com.example.ui.theme.NeonPurple
import kotlinx.coroutines.delay

@Composable
fun UploadScreen(
    onPublishSuccess: () -> Unit
) {
    var appName by remember { mutableStateOf("") }
    var tagline by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedPlatform by remember { mutableStateOf(AppPlatform.APK) }
    var selectedCategory by remember { mutableStateOf(AppCategory.DEVELOPER_TOOLS) }
    var versionStr by remember { mutableStateOf("1.0.0") }
    var changelog by remember { mutableStateOf("Initial release on ZainX AppHub.") }
    var websiteUrl by remember { mutableStateOf("") }
    var githubUrl by remember { mutableStateOf("") }
    var isPublic by remember { mutableStateOf(true) }

    // File selection simulation
    var selectedFileName by remember { mutableStateOf<String?>(null) }
    var simulatedFileSizeMb by remember { mutableDoubleStateOf(34.8) }
    var isUploadingFile by remember { mutableStateOf(false) }
    var uploadProgress by remember { mutableFloatStateOf(0f) }
    var fileUploadedSuccessfully by remember { mutableStateOf(false) }
    var statusMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(isUploadingFile) {
        if (isUploadingFile) {
            uploadProgress = 0f
            while (uploadProgress < 1f) {
                delay(60)
                uploadProgress += 0.05f
                if (uploadProgress >= 1f) {
                    uploadProgress = 1f
                    isUploadingFile = false
                    fileUploadedSuccessfully = true
                }
            }
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 14.dp),
        contentPadding = PaddingValues(bottom = 36.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Publish Software to ZainX",
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Black),
                color = Color.White
            )
            Text(
                text = "Package your APK, EXE, AAB, MSI, or ZIP archive. Direct distribution with zero walled gardens.",
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF94A3B8)
            )

            Spacer(modifier = Modifier.height(18.dp))
        }

        // 1. FILE UPLOAD BOX (DRAG & DROP SIMULATOR)
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(18.dp))
                    .background(Color(0xFF0F152E))
                    .border(
                        BorderStroke(1.dp, if (fileUploadedSuccessfully) NeonEmerald else Color(0xFF263366)),
                        RoundedCornerShape(18.dp)
                    )
                    .clickable {
                        if (!isUploadingFile) {
                            selectedFileName = "binary_package_${selectedPlatform.name.lowercase()}${selectedPlatform.extension}"
                            isUploadingFile = true
                            fileUploadedSuccessfully = false
                        }
                    }
                    .padding(20.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(54.dp)
                            .clip(CircleShape)
                            .background(
                                if (fileUploadedSuccessfully) NeonEmerald.copy(alpha = 0.2f) else NeonCyan.copy(alpha = 0.15f)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = if (fileUploadedSuccessfully) Icons.Default.CheckCircle else Icons.Default.CloudUpload,
                            contentDescription = null,
                            tint = if (fileUploadedSuccessfully) NeonEmerald else NeonCyan,
                            modifier = Modifier.size(28.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    if (selectedFileName == null) {
                        Text(
                            text = "Tap or Drop Binary Package Here",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = Color.White
                        )
                        Text(
                            text = "Supported: APK, EXE, AAB, MSI, ZIP (Up to 500MB)",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF94A3B8)
                        )
                    } else if (isUploadingFile) {
                        Text(
                            text = "Uploading & Stamping Hash...",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = NeonCyan
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        LinearProgressIndicator(
                            progress = { uploadProgress },
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                                .height(6.dp)
                                .clip(CircleShape),
                            color = NeonCyan,
                            trackColor = Color(0xFF1E284D)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "${(uploadProgress * 100).toInt()}% • $selectedFileName",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color(0xFFCBD5E1)
                        )
                    } else {
                        Text(
                            text = "Package Verified: $selectedFileName",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = NeonEmerald
                        )
                        Text(
                            text = "${simulatedFileSizeMb} MB • SHA-256 Calculated",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFFCBD5E1)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))
        }

        // 2. METADATA INPUTS
        item {
            Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                // Platform Select
                Text(
                    text = "Target Platform:",
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                    color = Color.White
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    AppPlatform.entries.forEach { platform ->
                        FilterChip(
                            selected = selectedPlatform == platform,
                            onClick = { selectedPlatform = platform },
                            label = { Text(platform.extension.uppercase().removePrefix(".")) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = Color(platform.badgeColorHex),
                                selectedLabelColor = Color.Black
                            )
                        )
                    }
                }

                OutlinedTextField(
                    value = appName,
                    onValueChange = { appName = it },
                    label = { Text("App Name *") },
                    placeholder = { Text("e.g. Zenith Audio Engine") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = NeonCyan,
                        unfocusedBorderColor = Color(0xFF263366)
                    )
                )

                OutlinedTextField(
                    value = tagline,
                    onValueChange = { tagline = it },
                    label = { Text("Short Tagline *") },
                    placeholder = { Text("e.g. Low-latency modular spatial synthesizer") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = NeonCyan,
                        unfocusedBorderColor = Color(0xFF263366)
                    )
                )

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Full Description *") },
                    placeholder = { Text("Describe features, architecture, and system requirements...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = NeonCyan,
                        unfocusedBorderColor = Color(0xFF263366)
                    )
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedTextField(
                        value = versionStr,
                        onValueChange = { versionStr = it },
                        label = { Text("Version") },
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = "$simulatedFileSizeMb MB",
                        onValueChange = { },
                        readOnly = true,
                        label = { Text("File Size") },
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                }

                OutlinedTextField(
                    value = changelog,
                    onValueChange = { changelog = it },
                    label = { Text("Release Notes / Changelog") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                )

                OutlinedTextField(
                    value = websiteUrl,
                    onValueChange = { websiteUrl = it },
                    label = { Text("Website or GitHub URL (Optional)") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                // Visibility Toggle
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFF0F152E))
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = if (isPublic) "Public (Store Live)" else "Draft / Unlisted",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = Color.White
                        )
                        Text(
                            text = if (isPublic) "Discoverable in ZainX Store immediately" else "Only accessible to you and collaborators",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF94A3B8)
                        )
                    }

                    Switch(
                        checked = isPublic,
                        onCheckedChange = { isPublic = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.Black,
                            checkedTrackColor = NeonCyan
                        )
                    )
                }

                if (statusMessage != null) {
                    Text(
                        text = statusMessage ?: "",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFFFF4864)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = {
                            if (appName.isNotBlank()) {
                                ZainXRepository.publishApp(
                                    name = appName,
                                    tagline = tagline.ifBlank { "Next-generation software package" },
                                    description = description.ifBlank { "Full release description for $appName." },
                                    platform = selectedPlatform,
                                    category = selectedCategory,
                                    version = versionStr,
                                    fileSizeMb = simulatedFileSizeMb,
                                    changelog = changelog,
                                    websiteUrl = websiteUrl,
                                    githubUrl = githubUrl,
                                    status = AppStatus.DRAFT
                                )
                                onPublishSuccess()
                            } else {
                                statusMessage = "Please provide an App Name."
                            }
                        },
                        modifier = Modifier.weight(1f),
                        shape = CircleShape,
                        border = BorderStroke(1.dp, Color(0xFF263366))
                    ) {
                        Text("Save Draft", color = Color.White)
                    }

                    PillButton(
                        text = "Publish to Store",
                        onClick = {
                            if (appName.isNotBlank() && tagline.isNotBlank()) {
                                ZainXRepository.publishApp(
                                    name = appName,
                                    tagline = tagline,
                                    description = description.ifBlank { "Software published via ZainX AppHub." },
                                    platform = selectedPlatform,
                                    category = selectedCategory,
                                    version = versionStr,
                                    fileSizeMb = simulatedFileSizeMb,
                                    changelog = changelog,
                                    websiteUrl = websiteUrl,
                                    githubUrl = githubUrl,
                                    status = AppStatus.PUBLISHED
                                )
                                onPublishSuccess()
                            } else {
                                statusMessage = "App Name and Tagline are required."
                            }
                        },
                        modifier = Modifier.weight(1.2f),
                        isPrimary = true
                    )
                }
            }
        }
    }
}
