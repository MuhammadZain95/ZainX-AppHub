package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.NeonEmerald

@Composable
fun ZainXFooter(
    onOpenAbout: () -> Unit,
    onOpenPricing: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF060914))
            .border(1.dp, Color(0x1A19B8FF))
            .padding(24.dp)
    ) {
        ZainXLogo(size = 32.dp, showWordmark = true)

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Where apps become tangible. Decentralized app distribution across mobile & desktop with verified sandboxing and SHA-256 integrity.",
            style = MaterialTheme.typography.bodySmall,
            color = Color(0xFF94A3B8),
            lineHeight = 18.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "PLATFORMS",
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                    color = NeonCyan
                )
                Text("Android (APK / AAB)", style = MaterialTheme.typography.bodySmall, color = Color(0xFFCBD5E1))
                Text("Windows (EXE / MSI)", style = MaterialTheme.typography.bodySmall, color = Color(0xFFCBD5E1))
                Text("Universal (ZIP)", style = MaterialTheme.typography.bodySmall, color = Color(0xFFCBD5E1))
            }

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "COMMUNITY",
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                    color = NeonCyan
                )
                Text("Developer Pricing", style = MaterialTheme.typography.bodySmall, color = Color(0xFFCBD5E1), modifier = Modifier.clickable { onOpenPricing() })
                Text("About Platform", style = MaterialTheme.typography.bodySmall, color = Color(0xFFCBD5E1), modifier = Modifier.clickable { onOpenAbout() })
                Text("Security Scanner", style = MaterialTheme.typography.bodySmall, color = Color(0xFFCBD5E1))
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        Divider(color = Color(0xFF1E284D), thickness = 1.dp)
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
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
                    text = "Global CDN 99.99% Operational",
                    style = MaterialTheme.typography.labelSmall,
                    color = NeonEmerald
                )
            }

            Text(
                text = "© 2026 ZainX Systems",
                style = MaterialTheme.typography.labelSmall,
                color = Color(0xFF64748B)
            )
        }
    }
}
