package com.tariqnaeemdeveloper.colorlink.model

data class ColorPair(
    val id: Int,
    val color: DotColor,
    val start: Position,
    val end: Position
) {
    fun isEndpoint(position: Position): Boolean {
        return position == start || position == end
    }

    fun getOtherEndpoint(position: Position): Position? {
        return when (position) {
            start -> end
            end -> start
            else -> null
        }
    }
}
