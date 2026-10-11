package com.example.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.NeonPurple

@Composable
fun DownloadStatsChart(
    modifier: Modifier = Modifier,
    title: String = "Weekly Downloads & Traffic",
    stats: List<Pair<String, Int>> = listOf(
        "Mon" to 14200,
        "Tue" to 19800,
        "Wed" to 28400,
        "Thu" to 34900,
        "Fri" to 42100,
        "Sat" to 38600,
        "Sun" to 49300
    )
) {
    var animationTriggered by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        animationTriggered = true
    }
    val animProgress by animateFloatAsState(
        targetValue = if (animationTriggered) 1f else 0f,
        animationSpec = tween(1200),
        label = "chart_anim"
    )

    val maxVal = stats.maxOfOrNull { it.second } ?: 1

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFF0F152E))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = Color.White
            )
            Text(
                text = "+34.2% this week",
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                color = NeonCyan
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(130.dp)
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val totalBars = stats.size
                val barSpacing = size.width / (totalBars * 1.6f)
                val barWidth = barSpacing * 0.7f
                val totalWidthNeeded = (totalBars * barWidth) + ((totalBars - 1) * barSpacing * 0.6f)
                val startX = (size.width - totalWidthNeeded) / 2f

                stats.forEachIndexed { index, pair ->
                    val normalizedHeight = (pair.second.toFloat() / maxVal) * (size.height * 0.82f) * animProgress
                    val x = startX + index * (barWidth + barSpacing * 0.6f)
                    val y = size.height - normalizedHeight

                    // Subtle background slot bar
                    drawRoundRect(
                        color = Color.White.copy(alpha = 0.05f),
                        topLeft = Offset(x, 0f),
                        size = Size(barWidth, size.height),
                        cornerRadius = CornerRadius(8f, 8f)
                    )

                    // Active gradient bar
                    drawRoundRect(
                        brush = Brush.verticalGradient(
                            colors = listOf(NeonCyan, NeonPurple)
                        ),
                        topLeft = Offset(x, y),
                        size = Size(barWidth, normalizedHeight),
                        cornerRadius = CornerRadius(8f, 8f)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            stats.forEach { pair ->
                Text(
                    text = pair.first,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color(0xFF94A3B8)
                )
            }
        }
    }
}
