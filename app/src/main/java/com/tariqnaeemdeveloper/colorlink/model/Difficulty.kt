package com.tariqnaeemdeveloper.colorlink.model

enum class Difficulty(
    val id: String,
    val title: String,
    val gridSize: Int,
    val description: String
) {
    EASY("easy", "Beginner 5x5", 5, "Perfect for getting started"),
    MEDIUM("medium", "Intermediate 6x6", 6, "A balanced puzzle challenge"),
    HARD("hard", "Advanced 7x7", 7, "For master color linkers"),
    EXPERT("expert", "Expert 8x8", 8, "Ultimate color link challenge");

    companion object {
        fun fromId(id: String): Difficulty = entries.firstOrNull { it.id == id } ?: EASY
    }
}
