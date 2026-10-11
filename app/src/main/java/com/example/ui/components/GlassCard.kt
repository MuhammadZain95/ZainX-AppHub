package com.example.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(20.dp),
    borderColor: Color = GlassBorder,
    backgroundColor: Color = DeepNavySurfaceElevated.copy(alpha = 0.65f),
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .clip(shape)
            .background(backgroundColor)
            .border(
                BorderStroke(
                    1.dp,
                    Brush.linearGradient(
                        colors = listOf(
                            borderColor.copy(alpha = 0.7f),
                            NeonPurple.copy(alpha = 0.3f),
                            borderColor.copy(alpha = 0.2f)
                        )
                    )
                ),
                shape
            )
            .padding(16.dp),
        content = content
    )
}

@Composable
fun PillButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isPrimary: Boolean = true,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    enabled: Boolean = true
) {
    val backgroundBrush = if (isPrimary) {
        Brush.horizontalGradient(
            colors = listOf(NeonCyan, NeonPurple)
        )
    } else {
        Brush.horizontalGradient(
            colors = listOf(
                Color.White.copy(alpha = 0.12f),
                Color.White.copy(alpha = 0.06f)
            )
        )
    }

    val contentColor = if (isPrimary) Color.Black else MaterialTheme.colorScheme.onBackground
    val borderStroke = if (isPrimary) {
        null
    } else {
        BorderStroke(1.dp, Brush.horizontalGradient(listOf(NeonCyan.copy(alpha = 0.5f), NeonPurple.copy(alpha = 0.5f))))
    }

    Surface(
        modifier = modifier
            .clip(CircleShape)
            .clickable(enabled = enabled, onClick = onClick),
        shape = CircleShape,
        color = Color.Transparent,
        border = borderStroke
    ) {
        Box(
            modifier = Modifier
                .background(backgroundBrush)
                .padding(horizontal = 22.dp, vertical = 13.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (leadingIcon != null) {
                    leadingIcon()
                }
                Text(
                    text = text,
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.3.sp
                    ),
                    color = contentColor
                )
                if (trailingIcon != null) {
                    trailingIcon()
                }
            }
        }
    }
}

/**
 * 3D Holographic ambient background with animated neon glow orbs,
 * simulating floating crystals, torus and cyber space geometry.
 */
@Composable
fun HolographicAmbientBackground(
    modifier: Modifier = Modifier,
    isDark: Boolean = true
) {
    val infiniteTransition = rememberInfiniteTransition(label = "ambient_motion")
    val animOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 60f,
        animationSpec = infiniteRepeatable(
            animation = tween(6000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "ambient_offset"
    )

    Canvas(modifier = modifier.fillMaxSize()) {
        val w = size.width
        val h = size.height

        if (isDark) {
            // Dark navy space base
            drawRect(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        DeepNavyBackground,
                        Color(0xFF090D21),
                        Color(0xFF050711)
                    )
                )
            )

            // Neon Purple / Magenta Orb (top right)
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        NeonPurple.copy(alpha = 0.28f),
                        Color.Transparent
                    ),
                    center = Offset(w * 0.85f, h * 0.18f + animOffset),
                    radius = w * 0.7f
                ),
                radius = w * 0.7f,
                center = Offset(w * 0.85f, h * 0.18f + animOffset)
            )

            // Neon Cyan Orb (top left / center)
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        NeonCyan.copy(alpha = 0.22f),
                        Color.Transparent
                    ),
                    center = Offset(w * 0.15f, h * 0.35f - animOffset),
                    radius = w * 0.65f
                ),
                radius = w * 0.65f,
                center = Offset(w * 0.15f, h * 0.35f - animOffset)
            )

            // Emerald Core Glow (bottom center)
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        NeonEmerald.copy(alpha = 0.14f),
                        Color.Transparent
                    ),
                    center = Offset(w * 0.5f, h * 0.75f + animOffset * 0.5f),
                    radius = w * 0.8f
                ),
                radius = w * 0.8f,
                center = Offset(w * 0.5f, h * 0.75f + animOffset * 0.5f)
            )
        } else {
            // Light theme background with high-tech cyan/purple soft accents
            drawRect(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        LightBackground,
                        Color(0xFFF1F5F9),
                        Color(0xFFE2E8F0)
                    )
                )
            )
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        NeonCyan.copy(alpha = 0.12f),
                        Color.Transparent
                    ),
                    center = Offset(w * 0.8f, h * 0.2f),
                    radius = w * 0.6f
                ),
                radius = w * 0.6f,
                center = Offset(w * 0.8f, h * 0.2f)
            )
        }
    }
}
