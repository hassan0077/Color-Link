package com.tariqnaeemdeveloper.colorlink.model

data class Level(
    val id: String,
    val levelNumber: Int,
    val difficulty: Difficulty,
    val colorPairs: List<ColorPair>,
    val solutionPaths: Map<Int, List<Position>> = emptyMap()
) {
    val gridSize: Int get() = difficulty.gridSize
    val totalCells: Int get() = gridSize * gridSize

    fun findPairForPosition(position: Position): ColorPair? {
        return colorPairs.firstOrNull { it.start == position || it.end == position }
    }

    fun findPairById(colorId: Int): ColorPair? {
        return colorPairs.firstOrNull { it.id == colorId }
    }
}
