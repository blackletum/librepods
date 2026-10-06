package me.kavishdevar.librepods.data.app

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import kotlinx.serialization.Serializable

@Serializable
data class IslandSettings(
    val enableExpanded: Boolean = true,
    val keepCompactVisible: Boolean = false,

    val showListeningModeControl: Boolean = false,

    val expandedHeight: Float = 300f,
    val expandedWidth: Float = 800f,
    val expandedBorderRadius: Float = 85f,
    val expandedBorderWidth: Float = 0f,
    val expandedBackgroundColor: IslandColor = IslandColor.SurfaceContainer,
    val expandedBackgroundAlpha: Float = 0.95f,
    val expandedUseMonochrome: Boolean = false,
    val expandedOpenAppOnTap: Boolean = false,
    val expandedBatteryStyle: IslandBatteryStyle = IslandBatteryStyle.CIRCLE_TEXT,

    val fontSize: Float = 20f,

    val compactHeight: Float = 85f,
    val compactWidth: Float = 300f,
    val compactBorderRadius: Float = 100f,
    val compactBorderWidth: Float = 1f,
    val compactBackgroundColor: IslandColor = IslandColor.SurfaceContainer,
    val compactBackgroundAlpha: Float = 1f,
    val compactUseMonochrome: Boolean = false,
    val compactOpenAppOnTap: Boolean = true,
    val compactBatteryStyle: IslandBatteryStyle = IslandBatteryStyle.CIRCLE,

    val offsetY: Float = 32f,
    val offsetX: Float = 0f
)

sealed interface IslandColor {
    data object Surface : IslandColor
    data object SurfaceContainer : IslandColor
    data class Custom(val colorValue: Long) : IslandColor
}

@Composable
fun IslandColor.resolve(): Color {
    return when (this) {
        IslandColor.Surface -> MaterialTheme.colorScheme.surface
        IslandColor.SurfaceContainer -> MaterialTheme.colorScheme.surfaceContainer
        is IslandColor.Custom -> Color(colorValue.toULong())
    }
}

enum class IslandBatteryStyle {
    NONE,
    TEXT,
    CIRCLE,
    CIRCLE_TEXT,
}
