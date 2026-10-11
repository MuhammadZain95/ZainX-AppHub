package com.example.ui.screens

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
import com.example.model.User
import com.example.model.UserRole
import com.example.ui.components.PillButton
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.NeonEmerald
import com.example.ui.theme.NeonPurple

@Composable
fun SettingsScreen(
    user: User,
    isDarkTheme: Boolean,
    onToggleTheme: () -> Unit,
    onSignOut: () -> Unit
) {
    var notificationsEnabled by remember { mutableStateOf(true) }
    var autoScanDownloads by remember { mutableStateOf(true) }
    var strictSignatureCheck by remember { mutableStateOf(true) }
    var toastMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(toastMessage) {
        if (toastMessage != null) {
            kotlinx.coroutines.delay(2000)
            toastMessage = null
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
                text = "Preferences & Settings",
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Black),
                color = Color.White
            )
            Text(
                text = "Account credentials, theme, security audits, and sandbox rules.",
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF94A3B8)
            )

            Spacer(modifier = Modifier.height(18.dp))
        }

        // ROLE SWITCHER (Demo Feature for testing User / Developer / Admin roles)
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFF0F152E))
                    .border(1.dp, NeonPurple.copy(alpha = 0.4f), RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(Icons.Default.AdminPanelSettings, contentDescription = null, tint = NeonPurple)
                    Text("Role Switcher (Demo Simulator)", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold), color = Color.White)
                }

                Text(
                    text = "Switch active permission level to test Admin panel, developer publishing, or standard consumer views.",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFFCBD5E1),
                    modifier = Modifier.padding(vertical = 6.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    UserRole.entries.forEach { role ->
                        FilterChip(
                            selected = user.role == role,
                            onClick = {
                                ZainXRepository.switchRole(role)
                                toastMessage = "Switched to ${role.displayName}"
                            },
                            label = { Text(role.displayName.substringBefore(" ")) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = NeonCyan,
                                selectedLabelColor = Color.Black
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        // APPEARANCE & THEME
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFF0F152E))
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Text(
                    text = "Appearance & Interface",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = Color.White
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Dark Holographic Theme", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold), color = Color.White)
                        Text("Cyberpunk neon navy contrast (Recommended)", style = MaterialTheme.typography.bodySmall, color = Color(0xFF94A3B8))
                    }

                    Switch(
                        checked = isDarkTheme,
                        onCheckedChange = { onToggleTheme() },
                        colors = SwitchDefaults.colors(checkedThumbColor = Color.Black, checkedTrackColor = NeonCyan)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Push Notifications", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold), color = Color.White)
                        Text("Alerts on new version releases and review feedback", style = MaterialTheme.typography.bodySmall, color = Color(0xFF94A3B8))
                    }

                    Switch(
                        checked = notificationsEnabled,
                        onCheckedChange = { notificationsEnabled = it },
                        colors = SwitchDefaults.colors(checkedThumbColor = Color.Black, checkedTrackColor = NeonCyan)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        // SECURITY SETTINGS
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFF0F152E))
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Text(
                    text = "Security & Sandboxing Engine",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = Color.White
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Automatic Sandbox Scan on Download", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold), color = Color.White)
                        Text("Simulate execution in cloud VM before saving", style = MaterialTheme.typography.bodySmall, color = Color(0xFF94A3B8))
                    }

                    Switch(
                        checked = autoScanDownloads,
                        onCheckedChange = { autoScanDownloads = it },
                        colors = SwitchDefaults.colors(checkedThumbColor = Color.Black, checkedTrackColor = NeonEmerald)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Enforce Strict SHA-256 Checksums", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold), color = Color.White)
                        Text("Reject any package mismatching publisher digest", style = MaterialTheme.typography.bodySmall, color = Color(0xFF94A3B8))
                    }

                    Switch(
                        checked = strictSignatureCheck,
                        onCheckedChange = { strictSignatureCheck = it },
                        colors = SwitchDefaults.colors(checkedThumbColor = Color.Black, checkedTrackColor = NeonEmerald)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        // ACTIVE SESSIONS
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFF0F152E))
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "Active Sessions",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = Color.White
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Icon(Icons.Default.PhoneAndroid, contentDescription = null, tint = NeonCyan, modifier = Modifier.size(20.dp))
                        Column {
                            Text("Android Client (This Device)", color = Color.White, style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold))
                            Text("Active now • Asia/Southeast", color = Color(0xFF94A3B8), style = MaterialTheme.typography.labelSmall)
                        }
                    }
                    Text("Current", color = NeonEmerald, style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold))
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }

        // DANGER ZONE
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFF1F111E))
                    .border(1.dp, Color(0xFFFF4864).copy(alpha = 0.4f), RoundedCornerShape(16.dp))
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text("Danger Zone", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold), color = Color(0xFFFF4864))
                Text("Signing out clears cached binary tokens from this device.", style = MaterialTheme.typography.bodySmall, color = Color(0xFFCBD5E1))

                PillButton(
                    text = "Sign Out of ZainX",
                    onClick = onSignOut,
                    modifier = Modifier.fillMaxWidth(),
                    isPrimary = false
                )
            }
        }
    }
}
