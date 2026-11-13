package com.tariqnaeemdeveloper.colorlink.model

data class PathState(
    val colorId: Int,
    val points: List<Position> = emptyList(),
    val isCompleted: Boolean = false
) {
    val length: Int get() = points.size

    fun contains(position: Position): Boolean = points.contains(position)
}
