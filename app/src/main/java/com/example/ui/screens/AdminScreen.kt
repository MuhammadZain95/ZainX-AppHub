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
import com.example.model.ReportItem
import com.example.ui.components.PillButton
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.NeonEmerald
import com.example.ui.theme.NeonPurple

enum class AdminTab(val label: String) {
    APPS("App Queue"),
    REPORTS("Reports"),
    SETTINGS("Site Config")
}

@Composable
fun AdminScreen(
    apps: List<AppItem>,
    reports: List<ReportItem>,
    onAppClick: (AppItem) -> Unit
) {
    var selectedTab by remember { mutableStateOf(AdminTab.APPS) }
    val siteName by ZainXRepository.siteName.collectAsState()
    val maintenanceMode by ZainXRepository.maintenanceMode.collectAsState()
    val maxUploadSizeMb by ZainXRepository.maxUploadSizeMb.collectAsState()

    var editSiteName by remember { mutableStateOf(siteName) }
    var editMaintenance by remember { mutableStateOf(maintenanceMode) }
    var editUploadMb by remember { mutableStateOf(maxUploadSizeMb.toString()) }

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
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(Icons.Default.AdminPanelSettings, contentDescription = null, tint = NeonPurple, modifier = Modifier.size(28.dp))
                Column {
                    Text(
                        text = "ZainX Admin Console",
                        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Black),
                        color = Color.White
                    )
                    Text(
                        text = "Platform moderation, binary review pipeline & system health.",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF94A3B8)
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))
        }

        // STATS ROW
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color(0xFF0F152E))
                        .padding(14.dp)
                ) {
                    Column {
                        Text("Total Software", style = MaterialTheme.typography.labelSmall, color = Color(0xFF94A3B8))
                        Text("${apps.size}", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Black), color = NeonCyan)
                    }
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color(0xFF0F152E))
                        .padding(14.dp)
                ) {
                    Column {
                        Text("Active Reports", style = MaterialTheme.typography.labelSmall, color = Color(0xFF94A3B8))
                        val pendingReports = reports.count { !it.isResolved }
                        Text("$pendingReports", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Black), color = Color(0xFFFF4864))
                    }
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color(0xFF0F152E))
                        .padding(14.dp)
                ) {
                    Column {
                        Text("System Uptime", style = MaterialTheme.typography.labelSmall, color = Color(0xFF94A3B8))
                        Text("99.99%", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Black), color = NeonEmerald)
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))
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
                AdminTab.entries.forEach { tab ->
                    val isSelected = selectedTab == tab
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(CircleShape)
                            .background(if (isSelected) NeonPurple else Color.Transparent)
                            .clickable { selectedTab = tab }
                            .padding(vertical = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = tab.label,
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                            color = if (isSelected) Color.White else Color(0xFF94A3B8)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))
        }

        // TAB 1: APPS QUEUE
        if (selectedTab == AdminTab.APPS) {
            items(apps) { app ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color(0xFF0F152E))
                        .border(1.dp, Color(0xFF1E284D), RoundedCornerShape(14.dp))
                        .padding(12.dp)
                ) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(Color(app.iconColor)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(app.iconSymbol, fontWeight = FontWeight.Bold, color = Color.Black)
                            }

                            Column(modifier = Modifier.weight(1f)) {
                                Text(app.name, style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold), color = Color.White)
                                Text("${app.developerName} • ${app.platform.displayName} • v${app.version}", style = MaterialTheme.typography.bodySmall, color = Color(0xFF94A3B8))
                            }

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
                                    color = when (app.status) {
                                        AppStatus.PUBLISHED -> NeonEmerald
                                        AppStatus.IN_REVIEW -> Color(0xFFFFB020)
                                        AppStatus.DRAFT -> Color(0xFFCBD5E1)
                                    },
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                    fontSize = 10.sp
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            if (app.status != AppStatus.PUBLISHED) {
                                TextButton(onClick = { ZainXRepository.setAppStatus(app.id, AppStatus.PUBLISHED) }) {
                                    Icon(Icons.Default.Check, contentDescription = null, tint = NeonEmerald, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Approve", color = NeonEmerald, style = MaterialTheme.typography.labelSmall)
                                }
                            }

                            if (app.status != AppStatus.IN_REVIEW) {
                                TextButton(onClick = { ZainXRepository.setAppStatus(app.id, AppStatus.IN_REVIEW) }) {
                                    Icon(Icons.Default.Pause, contentDescription = null, tint = Color(0xFFFFB020), modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Hold Review", color = Color(0xFFFFB020), style = MaterialTheme.typography.labelSmall)
                                }
                            }

                            IconButton(onClick = { ZainXRepository.deleteApp(app.id) }) {
                                Icon(Icons.Default.Delete, contentDescription = "Remove", tint = Color(0xFFFF4864), modifier = Modifier.size(18.dp))
                            }
                        }
                    }
                }
            }
        }

        // TAB 2: REPORTS
        if (selectedTab == AdminTab.REPORTS) {
            items(reports) { report ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color(0xFF0F152E))
                        .border(
                            1.dp,
                            if (report.isResolved) Color(0xFF1E284D) else Color(0xFFFF4864).copy(alpha = 0.5f),
                            RoundedCornerShape(14.dp)
                        )
                        .padding(14.dp)
                ) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "App: ${report.appName}",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                color = Color.White
                            )

                            Text(
                                text = if (report.isResolved) "RESOLVED" else "PENDING",
                                color = if (report.isResolved) NeonEmerald else Color(0xFFFF4864),
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                            )
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "Reported by: ${report.reporterEmail} • ${report.date}",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color(0xFF94A3B8)
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "\"${report.reason}\"",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFFCBD5E1)
                        )

                        if (!report.isResolved) {
                            Spacer(modifier = Modifier.height(10.dp))
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                                PillButton(
                                    text = "Mark Resolved",
                                    onClick = { ZainXRepository.resolveReport(report.id) },
                                    isPrimary = true
                                )
                            }
                        }
                    }
                }
            }
        }

        // TAB 3: SITE CONFIG
        if (selectedTab == AdminTab.SETTINGS) {
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
                        text = "Global Site Configuration",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = Color.White
                    )

                    OutlinedTextField(
                        value = editSiteName,
                        onValueChange = { editSiteName = it },
                        label = { Text("Site & Brand Name") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = editUploadMb,
                        onValueChange = { editUploadMb = it },
                        label = { Text("Maximum File Upload Limit (MB)") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Maintenance Mode", color = Color.White, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold))
                            Text("Show temporary maintenance disclaimer banner", color = Color(0xFF94A3B8), style = MaterialTheme.typography.bodySmall)
                        }

                        Switch(
                            checked = editMaintenance,
                            onCheckedChange = { editMaintenance = it },
                            colors = SwitchDefaults.colors(checkedThumbColor = Color.Black, checkedTrackColor = Color(0xFFFFB020))
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    PillButton(
                        text = "Save Configuration",
                        onClick = {
                            val uploadInt = editUploadMb.toIntOrNull() ?: 500
                            ZainXRepository.updateSiteSettings(editSiteName, editMaintenance, uploadInt)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        isPrimary = true
                    )
                }
            }
        }
    }
}
