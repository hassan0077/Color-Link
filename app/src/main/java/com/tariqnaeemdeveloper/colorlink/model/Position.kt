package com.tariqnaeemdeveloper.colorlink.model

data class Position(
    val row: Int,
    val col: Int
) {
    fun isAdjacentTo(other: Position): Boolean {
        val rowDiff = Math.abs(row - other.row)
        val colDiff = Math.abs(col - other.col)
        return (rowDiff == 1 && colDiff == 0) || (rowDiff == 0 && colDiff == 1)
    }
}
