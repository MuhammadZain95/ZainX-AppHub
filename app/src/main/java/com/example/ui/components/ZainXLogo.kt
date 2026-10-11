package com.example.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.NeonEmerald
import com.example.ui.theme.NeonPurple

/**
 * Recreates the exact ZainX Logo:
 * A bold 'X' made of thick rounded outline strokes with a gradient
 * (cyan #19B8FF to purple #8B3DFF, with a green #00E5A0 glow in the center diagonal).
 */
@Composable
fun ZainXLogo(
    modifier: Modifier = Modifier,
    size: Dp = 38.dp,
    showWordmark: Boolean = true,
    animated: Boolean = false
) {
    val infiniteTransition = rememberInfiniteTransition(label = "logo_anim")
    val glowPulse by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glow_pulse"
    )

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Canvas(modifier = Modifier.size(size)) {
            val strokeW = this.size.minDimension * 0.22f
            val w = this.size.width
            val h = this.size.height
            val pad = strokeW * 0.6f

            // Emerald Center Glow Radial behind the intersection
            val centerOffset = Offset(w / 2f, h / 2f)
            val glowRadius = (w * 0.45f) * if (animated) glowPulse else 1f
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        NeonEmerald.copy(alpha = 0.65f),
                        NeonCyan.copy(alpha = 0.3f),
                        Color.Transparent
                    ),
                    center = centerOffset,
                    radius = glowRadius
                ),
                radius = glowRadius,
                center = centerOffset
            )

            // Primary diagonal gradient: Cyan (#19B8FF) -> Emerald Glow (#00E5A0) -> Purple (#8B3DFF)
            val gradientMain = Brush.linearGradient(
                colors = listOf(NeonCyan, NeonEmerald, NeonPurple),
                start = Offset(pad, pad),
                end = Offset(w - pad, h - pad)
            )

            val gradientCross = Brush.linearGradient(
                colors = listOf(NeonPurple, NeonEmerald, NeonCyan),
                start = Offset(w - pad, pad),
                end = Offset(pad, h - pad)
            )

            // Back diagonal stroke
            drawLine(
                brush = gradientCross,
                start = Offset(w - pad, pad),
                end = Offset(pad, h - pad),
                strokeWidth = strokeW,
                cap = StrokeCap.Round
            )

            // Front diagonal stroke
            drawLine(
                brush = gradientMain,
                start = Offset(pad, pad),
                end = Offset(w - pad, h - pad),
                strokeWidth = strokeW,
                cap = StrokeCap.Round
            )

            // Center neon core highlight dot for high-tech aesthetic
            drawCircle(
                color = Color.White.copy(alpha = 0.9f),
                radius = strokeW * 0.16f,
                center = centerOffset
            )
        }

        if (showWordmark) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Zain",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Black,
                            letterSpacing = (-0.5).sp
                        ),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = "X",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Black,
                            letterSpacing = (-0.5).sp
                        ),
                        color = NeonCyan
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "AppHub",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            letterSpacing = (-0.2).sp
                        ),
                        color = NeonPurple
                    )
                }
            }
        }
    }
}
