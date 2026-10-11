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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.ZainXRepository
import com.example.model.AppItem
import com.example.model.User
import com.example.ui.components.PillButton
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.NeonEmerald
import com.example.ui.theme.NeonPurple

enum class ProfileTab(val label: String) {
    PUBLISHED("My Software"),
    FAVORITES("Saved Wishlist")
}

@Composable
fun ProfileScreen(
    user: User,
    userApps: List<AppItem>,
    wishlistApps: List<AppItem>,
    onAppClick: (AppItem) -> Unit,
    onOpenSettings: () -> Unit
) {
    var selectedTab by remember { mutableStateOf(ProfileTab.PUBLISHED) }
    var showEditProfileDialog by remember { mutableStateOf(false) }

    if (showEditProfileDialog) {
        var editName by remember { mutableStateOf(user.name) }
        var editBio by remember { mutableStateOf(user.bio) }
        var editLocation by remember { mutableStateOf(user.location) }
        var editWebsite by remember { mutableStateOf(user.website) }

        AlertDialog(
            onDismissRequest = { showEditProfileDialog = false },
            containerColor = Color(0xFF0F152E),
            title = {
                Text("Edit Profile Details", color = Color.White, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
            },
            text = {
                Column {
                    OutlinedTextField(
                        value = editName,
                        onValueChange = { editName = it },
                        label = { Text("Display Name") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = editBio,
                        onValueChange = { editBio = it },
                        label = { Text("Bio") },
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 3
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = editLocation,
                        onValueChange = { editLocation = it },
                        label = { Text("Location") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = editWebsite,
                        onValueChange = { editWebsite = it },
                        label = { Text("Website Link") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                }
            },
            confirmButton = {
                PillButton(
                    text = "Save Profile",
                    onClick = {
                        ZainXRepository.updateUser(editName, editBio, editLocation, editWebsite)
                        showEditProfileDialog = false
                    },
                    isPrimary = true
                )
            },
            dismissButton = {
                TextButton(onClick = { showEditProfileDialog = false }) {
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
        // COVER & AVATAR BANNER
        item {
            Spacer(modifier = Modifier.height(10.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        Brush.linearGradient(
                            listOf(Color(0xFF1E2854), Color(0xFF381B68), Color(0xFF0B1936))
                        )
                    )
            )

            // Overlapping Avatar & Action Button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp)
                    .offset(y = (-36).dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Box(
                    modifier = Modifier
                        .size(76.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF0A0D1F))
                        .border(3.dp, NeonCyan, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = user.name.take(1).uppercase(),
                        fontWeight = FontWeight.Black,
                        fontSize = 32.sp,
                        color = Color.White
                    )
                }

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedButton(
                        onClick = { showEditProfileDialog = true },
                        shape = CircleShape,
                        border = BorderStroke(1.dp, Color(0xFF263366))
                    ) {
                        Text("Edit Profile", color = Color.White, style = MaterialTheme.typography.labelSmall)
                    }

                    IconButton(
                        onClick = onOpenSettings,
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(Color(0xFF161F3F))
                            .size(38.dp)
                    ) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings", tint = Color.White, modifier = Modifier.size(18.dp))
                    }
                }
            }
        }

        // USER DETAILS
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = (-20).dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = user.name,
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                        color = Color.White
                    )
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(NeonPurple.copy(alpha = 0.25f))
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = user.role.displayName,
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = NeonPurple,
                            fontSize = 10.sp
                        )
                    }
                }

                Text(
                    text = "@${user.username} • Joined ${user.joinDate}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF94A3B8)
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = user.bio,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFFCBD5E1),
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        Icon(Icons.Default.LocationOn, contentDescription = null, tint = NeonCyan, modifier = Modifier.size(14.dp))
                        Text(user.location, style = MaterialTheme.typography.labelSmall, color = Color(0xFF94A3B8))
                    }

                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        Icon(Icons.Default.Link, contentDescription = null, tint = NeonEmerald, modifier = Modifier.size(14.dp))
                        Text(user.website, style = MaterialTheme.typography.labelSmall, color = NeonEmerald)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // STATS ROW
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color(0xFF0F152E))
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("${userApps.size}", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold), color = NeonCyan)
                        Text("Published Apps", style = MaterialTheme.typography.labelSmall, color = Color(0xFF94A3B8))
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        val totalDls = userApps.sumOf { it.downloadCount }
                        Text("${totalDls / 1000}K+", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold), color = NeonEmerald)
                        Text("Total Downloads", style = MaterialTheme.typography.labelSmall, color = Color(0xFF94A3B8))
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("${user.followersCount}", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold), color = Color(0xFFFFB020))
                        Text("Followers", style = MaterialTheme.typography.labelSmall, color = Color(0xFF94A3B8))
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
        }

        // TABS
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(CircleShape)
                    .background(Color(0xFF0F152E))
                    .padding(4.dp)
            ) {
                ProfileTab.entries.forEach { tab ->
                    val isSelected = selectedTab == tab
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(CircleShape)
                            .background(if (isSelected) NeonCyan else Color.Transparent)
                            .clickable { selectedTab = tab }
                            .padding(vertical = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = tab.label,
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                            color = if (isSelected) Color.Black else Color.White
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))
        }

        // TAB CONTENT
        val displayApps = if (selectedTab == ProfileTab.PUBLISHED) userApps else wishlistApps

        if (displayApps.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (selectedTab == ProfileTab.PUBLISHED) "No software published yet." else "No apps saved to wishlist.",
                        color = Color(0xFF94A3B8),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        } else {
            items(displayApps) { app ->
                StoreAppListItem(
                    app = app,
                    onClick = { onAppClick(app) },
                    onQuickDownload = { onAppClick(app) }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}
