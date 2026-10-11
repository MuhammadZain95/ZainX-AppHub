package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import com.example.model.User
import com.example.model.UserRole
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.NeonEmerald
import com.example.ui.theme.NeonPurple

enum class NavScreen(val label: String) {
    LANDING("Home"),
    STORE("Store"),
    UPLOAD("Publish"),
    DASHBOARD("My Apps"),
    PROFILE("Profile"),
    SETTINGS("Settings"),
    ADMIN("Admin")
}

@Composable
fun ZainXNavbar(
    currentScreen: NavScreen,
    onNavigate: (NavScreen) -> Unit,
    currentUser: User,
    isDarkTheme: Boolean,
    onToggleTheme: () -> Unit,
    onOpenAuth: () -> Unit,
    onOpenPricing: () -> Unit,
    onOpenAbout: () -> Unit
) {
    var showMenu by remember { mutableStateOf(false) }

    Surface(
        color = Color(0xCC090D1C),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color(0x2019B8FF))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Brand Logo
                Box(
                    modifier = Modifier.clickable { onNavigate(NavScreen.LANDING) }
                ) {
                    ZainXLogo(size = 32.dp, showWordmark = true)
                }

                // Action Controls on right
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Theme Toggle
                    IconButton(
                        onClick = onToggleTheme,
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = if (isDarkTheme) Icons.Default.LightMode else Icons.Default.DarkMode,
                            contentDescription = "Toggle Theme",
                            tint = if (isDarkTheme) NeonCyan else Color(0xFF0F172A),
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    // User Profile / Sign In Pill
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(Color(0xFF161F3F))
                            .clickable { onNavigate(NavScreen.PROFILE) }
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(20.dp)
                                    .clip(CircleShape)
                                    .background(NeonCyan),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = currentUser.name.take(1).uppercase(),
                                    fontWeight = FontWeight.Black,
                                    fontSize = 11.sp,
                                    color = Color.Black
                                )
                            }
                            Text(
                                text = currentUser.username.take(10),
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                color = Color.White
                            )
                        }
                    }

                    // Mobile Menu Toggle
                    IconButton(
                        onClick = { showMenu = !showMenu },
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = if (showMenu) Icons.Default.Close else Icons.Default.Menu,
                            contentDescription = "Menu",
                            tint = Color.White,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }
            }

            // Quick Nav Pills Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val screens = mutableListOf(
                    NavScreen.LANDING,
                    NavScreen.STORE,
                    NavScreen.UPLOAD,
                    NavScreen.DASHBOARD
                )
                if (currentUser.role == UserRole.ADMIN) {
                    screens.add(NavScreen.ADMIN)
                }

                screens.forEach { screen ->
                    val isSelected = currentScreen == screen
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(
                                if (isSelected) NeonCyan else Color(0xFF131830)
                            )
                            .clickable {
                                onNavigate(screen)
                                showMenu = false
                            }
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = screen.label,
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = if (isSelected) Color.Black else Color(0xFFCBD5E1)
                        )
                    }
                }
            }

            // Dropdown Menu Drawer for additional links
            if (showMenu) {
                Spacer(modifier = Modifier.height(10.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFF0F152C))
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onNavigate(NavScreen.PROFILE)
                                showMenu = false
                            }
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(Icons.Default.Person, contentDescription = null, tint = NeonCyan, modifier = Modifier.size(18.dp))
                        Text("My Profile & Badges", color = Color.White, style = MaterialTheme.typography.bodyMedium)
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onNavigate(NavScreen.SETTINGS)
                                showMenu = false
                            }
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(Icons.Default.Settings, contentDescription = null, tint = NeonPurple, modifier = Modifier.size(18.dp))
                        Text("Account & Security Settings", color = Color.White, style = MaterialTheme.typography.bodyMedium)
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onOpenPricing()
                                showMenu = false
                            }
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(Icons.Default.Star, contentDescription = null, tint = NeonEmerald, modifier = Modifier.size(18.dp))
                        Text("Developer Pricing Plans", color = Color.White, style = MaterialTheme.typography.bodyMedium)
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onOpenAbout()
                                showMenu = false
                            }
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(Icons.Default.Info, contentDescription = null, tint = NeonCyan, modifier = Modifier.size(18.dp))
                        Text("About ZainX AppHub", color = Color.White, style = MaterialTheme.typography.bodyMedium)
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onOpenAuth()
                                showMenu = false
                            }
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(Icons.Default.Lock, contentDescription = null, tint = Color(0xFFFFB020), modifier = Modifier.size(18.dp))
                        Text("Switch Account / Sign In", color = Color.White, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }
    }
}
