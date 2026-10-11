package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.ZainXRepository
import com.example.model.AppItem
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.NeonEmerald
import com.example.ui.theme.NeonPurple

@Composable
fun PricingDialog(
    isOpen: Boolean,
    onDismiss: () -> Unit
) {
    if (!isOpen) return

    val plans = listOf(
        Triple("Community", "Free", listOf("Unlimited APK & EXE downloads", "Basic community reviews", "Up to 5 public app uploads", "Standard security scan")),
        Triple("Pro Creator", "$12 / mo", listOf("Priority global CDN downloads", "Unlimited app uploads (up to 500MB)", "Advanced analytics & download logs", "Automated malware check badge", "Featured developer badge")),
        Triple("Studio Enterprise", "$49 / mo", listOf("Team permissions & multi-dev access", "Custom mirror hosting", "Dedicated API & webhook integrations", "24/7 Priority verification queue"))
    )

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = Color(0xFF0C1024),
            border = BorderStroke(1.dp, NeonPurple.copy(alpha = 0.4f)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "ZainX Developer Plans",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = Color.White
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = Color(0xFF94A3B8))
                    }
                }

                Text(
                    text = "Transparent pricing for indie developers and production software studios.",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF94A3B8)
                )

                Spacer(modifier = Modifier.height(16.dp))

                plans.forEachIndexed { index, plan ->
                    val isPopular = index == 1
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(if (isPopular) Color(0xFF161E40) else Color(0xFF111630))
                            .padding(14.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = plan.first,
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                color = if (isPopular) NeonCyan else Color.White
                            )
                            Text(
                                text = plan.second,
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Black),
                                color = if (isPopular) NeonEmerald else Color(0xFFE2E8F0)
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        plan.third.forEach { feature ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                                modifier = Modifier.padding(vertical = 2.dp)
                            ) {
                                Icon(Icons.Default.Check, contentDescription = null, tint = NeonEmerald, modifier = Modifier.size(14.dp))
                                Text(feature, style = MaterialTheme.typography.bodySmall, color = Color(0xFFCBD5E1))
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        PillButton(
                            text = if (isPopular) "Start 14-Day Pro Trial" else "Select ${plan.first}",
                            onClick = onDismiss,
                            modifier = Modifier.fillMaxWidth(),
                            isPrimary = isPopular
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }
    }
}

@Composable
fun AboutDialog(
    isOpen: Boolean,
    onDismiss: () -> Unit
) {
    if (!isOpen) return

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = Color(0xFF0C1024),
            border = BorderStroke(1.dp, NeonCyan.copy(alpha = 0.4f)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(22.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ZainXLogo(size = 32.dp, showWordmark = true)
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = Color(0xFF94A3B8))
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "Where apps become tangible.",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = NeonCyan
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "ZainX AppHub was founded with a singular mission: to democratize software distribution across modern platforms without the friction, walled gardens, or arbitrary restrictions of legacy stores.\n\nWhether you are packaging Android applications (APK, AAB), high-performance desktop tools (EXE, MSI), or cross-platform utilities (ZIP), ZainX delivers instant binary verification, zero-latency downloads, and decentralized developer ownership.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFFCBD5E1),
                    lineHeight = 22.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Architecture & Guarantees:",
                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(6.dp))

                listOf(
                    "100% SHA-256 Checksum Validation",
                    "Automated Cloud Malware Sandboxing",
                    "Direct Developer-to-User Distribution",
                    "Zero Forced Advertisements or Bloatware"
                ).forEach { bullet ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.padding(vertical = 3.dp)
                    ) {
                        Icon(Icons.Default.Check, contentDescription = null, tint = NeonEmerald, modifier = Modifier.size(16.dp))
                        Text(bullet, style = MaterialTheme.typography.bodySmall, color = Color(0xFFE2E8F0))
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                PillButton(
                    text = "Explore AppHub",
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth(),
                    isPrimary = true
                )
            }
        }
    }
}

@Composable
fun ReportAppDialog(
    app: AppItem,
    isOpen: Boolean,
    onDismiss: () -> Unit
) {
    if (!isOpen) return

    var reason by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("reporter@domain.com") }
    var submitted by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = Color(0xFF0F152C),
            border = BorderStroke(1.dp, Color(0xFFFF4864).copy(alpha = 0.5f)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Text(
                    text = "Report \"${app.name}\"",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = Color.White
                )

                Text(
                    text = "Help maintain ZainX safety. Reports are investigated by admins within 24 hours.",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF94A3B8)
                )

                Spacer(modifier = Modifier.height(16.dp))

                if (submitted) {
                    Text(
                        text = "Thank you! Your report has been submitted to the moderation queue.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = NeonEmerald
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    PillButton(text = "Close", onClick = onDismiss, modifier = Modifier.fillMaxWidth())
                } else {
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Your Email") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = reason,
                        onValueChange = { reason = it },
                        label = { Text("Reason for Report (Malware, Fake info, Copyright)") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        OutlinedButton(
                            onClick = onDismiss,
                            modifier = Modifier.weight(1f),
                            shape = CircleShape
                        ) {
                            Text("Cancel", color = Color(0xFFCBD5E1))
                        }

                        PillButton(
                            text = "Submit Report",
                            onClick = {
                                if (reason.isNotBlank()) {
                                    ZainXRepository.submitReport(app.id, app.name, email, reason)
                                    submitted = true
                                }
                            },
                            modifier = Modifier.weight(1f),
                            isPrimary = true
                        )
                    }
                }
            }
        }
    }
}
