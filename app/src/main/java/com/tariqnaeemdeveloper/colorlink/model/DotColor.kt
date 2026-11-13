package com.tariqnaeemdeveloper.colorlink.model

import androidx.compose.ui.graphics.Color

enum class DotColor(
    val id: Int,
    val colorName: String,
    val color: Color,
    val glowColor: Color
) {
    RED(
        id = 1,
        colorName = "Red",
        color = Color(0xFFFF3B30),
        glowColor = Color(0x66FF3B30)
    ),
    BLUE(
        id = 2,
        colorName = "Blue",
        color = Color(0xFF007AFF),
        glowColor = Color(0x66007AFF)
    ),
    GREEN(
        id = 3,
        colorName = "Green",
        color = Color(0xFF34C759),
        glowColor = Color(0x6634C759)
    ),
    YELLOW(
        id = 4,
        colorName = "Yellow",
        color = Color(0xFFFFCC00),
        glowColor = Color(0x66FFCC00)
    ),
    ORANGE(
        id = 5,
        colorName = "Orange",
        color = Color(0xFFFF9500),
        glowColor = Color(0x66FF9500)
    ),
    PURPLE(
        id = 6,
        colorName = "Purple",
        color = Color(0xFFAF52DE),
        glowColor = Color(0x66AF52DE)
    ),
    CYAN(
        id = 7,
        colorName = "Cyan",
        color = Color(0xFF5AC8FA),
        glowColor = Color(0x665AC8FA)
    ),
    PINK(
        id = 8,
        colorName = "Pink",
        color = Color(0xFFFF2D55),
        glowColor = Color(0x66FF2D55)
    );

    companion object {
        fun fromId(id: Int): DotColor = entries.firstOrNull { it.id == id } ?: RED
    }
}
