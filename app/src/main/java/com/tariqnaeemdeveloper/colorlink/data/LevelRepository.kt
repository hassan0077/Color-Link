package com.tariqnaeemdeveloper.colorlink.data

import com.tariqnaeemdeveloper.colorlink.model.ColorPair
import com.tariqnaeemdeveloper.colorlink.model.Difficulty
import com.tariqnaeemdeveloper.colorlink.model.DotColor
import com.tariqnaeemdeveloper.colorlink.model.Level
import com.tariqnaeemdeveloper.colorlink.model.Position

object LevelRepository {

    val difficulties = listOf(
        Difficulty.EASY,
        Difficulty.MEDIUM,
        Difficulty.HARD,
        Difficulty.EXPERT
    )

    fun getLevelsForDifficulty(difficulty: Difficulty): List<Level> {
        return when (difficulty) {
            Difficulty.EASY -> easyLevels
            Difficulty.MEDIUM -> mediumLevels
            Difficulty.HARD -> hardLevels
            Difficulty.EXPERT -> expertLevels
        }
    }

    fun getLevelById(id: String): Level? {
        return (easyLevels + mediumLevels + hardLevels + expertLevels).firstOrNull { it.id == id }
    }

    private fun createLevel(
        id: String,
        levelNumber: Int,
        difficulty: Difficulty,
        solutionMap: List<Pair<DotColor, List<Position>>>
    ): Level {
        var idCounter = 1
        val pairs = mutableListOf<ColorPair>()
        val solutionPaths = mutableMapOf<Int, List<Position>>()

        solutionMap.forEach { (color, path) ->
            val colorId = idCounter++
            val start = path.first()
            val end = path.last()
            pairs.add(ColorPair(colorId, color, start, end))
            solutionPaths[colorId] = path
        }

        return Level(
            id = id,
            levelNumber = levelNumber,
            difficulty = difficulty,
            colorPairs = pairs,
            solutionPaths = solutionPaths
        )
    }

    // Helper for position lists
    private fun p(row: Int, col: Int) = Position(row, col)

    // ------------------- 5x5 EASY LEVELS -------------------
    private val easyLevels = listOf(
        // easy_1
        createLevel("easy_1", 1, Difficulty.EASY, listOf(
            DotColor.RED to listOf(p(0,0), p(0,1), p(0,2), p(0,3), p(0,4), p(1,4), p(2,4)),
            DotColor.BLUE to listOf(p(1,0), p(1,1), p(1,2), p(1,3)),
            DotColor.GREEN to listOf(p(2,0), p(2,1), p(2,2), p(2,3), p(3,3), p(4,3), p(4,4), p(3,4)),
            DotColor.YELLOW to listOf(p(3,0), p(4,0), p(4,1), p(3,1), p(3,2), p(4,2))
        )),
        // easy_2
        createLevel("easy_2", 2, Difficulty.EASY, listOf(
            DotColor.RED to listOf(p(0,0), p(0,1), p(0,2), p(0,3), p(0,4)),
            DotColor.BLUE to listOf(p(1,0), p(2,0), p(3,0), p(4,0), p(4,1), p(3,1), p(2,1), p(1,1)),
            DotColor.GREEN to listOf(p(1,2), p(2,2), p(3,2), p(4,2)),
            DotColor.YELLOW to listOf(p(1,3), p(2,3), p(3,3), p(4,3)),
            DotColor.ORANGE to listOf(p(1,4), p(2,4), p(3,4), p(4,4))
        )),
        // easy_3
        createLevel("easy_3", 3, Difficulty.EASY, listOf(
            DotColor.RED to listOf(p(0,0), p(1,0), p(2,0), p(2,1), p(1,1), p(0,1), p(0,2), p(1,2), p(2,2)),
            DotColor.BLUE to listOf(p(0,3), p(0,4), p(1,4), p(1,3), p(2,3), p(2,4)),
            DotColor.GREEN to listOf(p(3,0), p(3,1), p(3,2), p(3,3), p(3,4)),
            DotColor.YELLOW to listOf(p(4,0), p(4,1)),
            DotColor.ORANGE to listOf(p(4,2), p(4,3), p(4,4))
        )),
        // easy_4
        createLevel("easy_4", 4, Difficulty.EASY, listOf(
            DotColor.RED to listOf(p(0,0), p(0,1), p(0,2), p(1,2), p(1,1), p(1,0), p(2,0), p(3,0), p(4,0)),
            DotColor.BLUE to listOf(p(0,3), p(0,4), p(1,4), p(2,4), p(2,3), p(1,3)),
            DotColor.GREEN to listOf(p(2,1), p(2,2), p(3,2), p(3,1)),
            DotColor.YELLOW to listOf(p(4,1), p(4,2), p(4,3)),
            DotColor.ORANGE to listOf(p(3,3), p(3,4), p(4,4))
        )),
        // easy_5
        createLevel("easy_5", 5, Difficulty.EASY, listOf(
            DotColor.RED to listOf(p(0,0), p(0,1), p(0,2), p(0,3), p(0,4), p(1,4), p(1,3), p(1,2), p(1,1), p(1,0)),
            DotColor.BLUE to listOf(p(2,0), p(3,0), p(4,0), p(4,1), p(3,1), p(2,1)),
            DotColor.GREEN to listOf(p(2,2), p(3,2), p(4,2)),
            DotColor.YELLOW to listOf(p(2,3), p(3,3), p(4,3)),
            DotColor.ORANGE to listOf(p(2,4), p(3,4), p(4,4))
        )),
        // easy_6
        createLevel("easy_6", 6, Difficulty.EASY, listOf(
            DotColor.RED to listOf(p(0,0), p(1,0), p(2,0), p(3,0), p(4,0), p(4,1), p(4,2), p(4,3), p(4,4)),
            DotColor.BLUE to listOf(p(0,1), p(0,2), p(0,3), p(0,4), p(1,4), p(2,4), p(3,4)),
            DotColor.GREEN to listOf(p(1,1), p(1,2), p(1,3), p(2,3), p(3,3)),
            DotColor.YELLOW to listOf(p(2,1), p(2,2)),
            DotColor.ORANGE to listOf(p(3,1), p(3,2))
        )),
        // easy_7
        createLevel("easy_7", 7, Difficulty.EASY, listOf(
            DotColor.RED to listOf(p(0,0), p(0,1), p(1,1), p(1,0), p(2,0), p(3,0), p(3,1), p(2,1), p(2,2), p(1,2), p(0,2)),
            DotColor.BLUE to listOf(p(0,3), p(0,4), p(1,4), p(1,3), p(2,3), p(2,4)),
            DotColor.GREEN to listOf(p(3,2), p(3,3), p(3,4)),
            DotColor.YELLOW to listOf(p(4,0), p(4,1), p(4,2)),
            DotColor.ORANGE to listOf(p(4,3), p(4,4))
        )),
        // easy_8
        createLevel("easy_8", 8, Difficulty.EASY, listOf(
            DotColor.RED to listOf(p(0,0), p(0,1), p(0,2), p(0,3), p(0,4), p(1,4), p(1,3), p(1,2), p(1,1)),
            DotColor.BLUE to listOf(p(1,0), p(2,0), p(2,1), p(2,2), p(2,3), p(2,4)),
            DotColor.GREEN to listOf(p(3,0), p(3,1), p(3,2), p(3,3), p(3,4)),
            DotColor.YELLOW to listOf(p(4,0), p(4,1)),
            DotColor.ORANGE to listOf(p(4,2), p(4,3), p(4,4))
        )),
        // easy_9
        createLevel("easy_9", 9, Difficulty.EASY, listOf(
            DotColor.RED to listOf(p(0,0), p(1,0), p(2,0), p(3,0), p(3,1), p(2,1), p(1,1), p(0,1), p(0,2), p(0,3), p(0,4)),
            DotColor.BLUE to listOf(p(1,2), p(1,3), p(1,4), p(2,4), p(2,3), p(2,2)),
            DotColor.GREEN to listOf(p(3,2), p(3,3), p(3,4)),
            DotColor.YELLOW to listOf(p(4,0), p(4,1), p(4,2)),
            DotColor.ORANGE to listOf(p(4,3), p(4,4))
        )),
        // easy_10
        createLevel("easy_10", 10, Difficulty.EASY, listOf(
            DotColor.RED to listOf(p(0,0), p(0,1), p(0,2), p(1,2), p(1,1), p(1,0), p(2,0), p(2,1), p(2,2), p(2,3), p(1,3), p(0,3), p(0,4), p(1,4), p(2,4)),
            DotColor.BLUE to listOf(p(3,0), p(4,0), p(4,1), p(3,1)),
            DotColor.GREEN to listOf(p(3,2), p(4,2)),
            DotColor.YELLOW to listOf(p(3,3), p(4,3)),
            DotColor.ORANGE to listOf(p(3,4), p(4,4))
        ))
    )

    // ------------------- 6x6 MEDIUM LEVELS -------------------
    private val mediumLevels = listOf(
        // medium_1
        createLevel("medium_1", 1, Difficulty.MEDIUM, listOf(
            DotColor.RED to listOf(p(0,0), p(0,1), p(0,2), p(0,3), p(0,4), p(0,5), p(1,5), p(2,5), p(3,5), p(4,5), p(5,5)),
            DotColor.BLUE to listOf(p(1,0), p(1,1), p(1,2), p(1,3), p(1,4)),
            DotColor.GREEN to listOf(p(2,0), p(2,1), p(2,2), p(2,3), p(2,4)),
            DotColor.YELLOW to listOf(p(3,0), p(3,1), p(3,2), p(3,3), p(3,4)),
            DotColor.ORANGE to listOf(p(4,0), p(5,0), p(5,1), p(4,1), p(4,2), p(5,2), p(5,3), p(4,3), p(4,4), p(5,4))
        )),
        // medium_2
        createLevel("medium_2", 2, Difficulty.MEDIUM, listOf(
            DotColor.RED to listOf(p(0,0), p(0,1), p(0,2), p(0,3), p(0,4), p(0,5), p(1,5), p(1,4), p(1,3), p(1,2), p(1,1), p(1,0)),
            DotColor.BLUE to listOf(p(2,0), p(2,1), p(2,2), p(2,3), p(2,4), p(2,5)),
            DotColor.GREEN to listOf(p(3,0), p(3,1), p(3,2), p(3,3), p(3,4), p(3,5)),
            DotColor.YELLOW to listOf(p(4,0), p(4,1), p(4,2), p(4,3)),
            DotColor.ORANGE to listOf(p(5,0), p(5,1), p(5,2), p(5,3)),
            DotColor.PURPLE to listOf(p(4,4), p(4,5), p(5,5), p(5,4))
        )),
        // medium_3
        createLevel("medium_3", 3, Difficulty.MEDIUM, listOf(
            DotColor.RED to listOf(p(0,0), p(1,0), p(2,0), p(3,0), p(4,0), p(5,0), p(5,1), p(4,1), p(3,1), p(2,1), p(1,1), p(0,1)),
            DotColor.BLUE to listOf(p(0,2), p(0,3), p(0,4), p(0,5), p(1,5), p(2,5), p(3,5), p(4,5), p(5,5)),
            DotColor.GREEN to listOf(p(1,2), p(2,2), p(3,2), p(4,2), p(5,2)),
            DotColor.YELLOW to listOf(p(1,3), p(2,3), p(3,3), p(4,3), p(5,3)),
            DotColor.ORANGE to listOf(p(1,4), p(2,4), p(3,4)),
            DotColor.PURPLE to listOf(p(4,4), p(5,4))
        )),
        // medium_4
        createLevel("medium_4", 4, Difficulty.MEDIUM, listOf(
            DotColor.RED to listOf(p(0,0), p(0,1), p(0,2), p(1,2), p(1,1), p(1,0), p(2,0), p(2,1), p(2,2), p(3,2), p(3,1), p(3,0)),
            DotColor.BLUE to listOf(p(0,3), p(0,4), p(0,5), p(1,5), p(1,4), p(1,3), p(2,3), p(2,4), p(2,5)),
            DotColor.GREEN to listOf(p(3,3), p(3,4), p(3,5), p(4,5), p(5,5)),
            DotColor.YELLOW to listOf(p(4,0), p(4,1), p(4,2), p(5,2), p(5,1), p(5,0)),
            DotColor.ORANGE to listOf(p(4,3), p(5,3)),
            DotColor.PURPLE to listOf(p(4,4), p(5,4))
        )),
        // medium_5
        createLevel("medium_5", 5, Difficulty.MEDIUM, listOf(
            DotColor.RED to listOf(p(0,0), p(0,1), p(0,2), p(0,3), p(0,4), p(0,5), p(1,5), p(1,4), p(1,3), p(1,2), p(1,1), p(1,0)),
            DotColor.BLUE to listOf(p(2,0), p(2,1), p(2,2), p(2,3), p(2,4), p(2,5)),
            DotColor.GREEN to listOf(p(3,0), p(3,1), p(3,2), p(3,3), p(3,4), p(3,5)),
            DotColor.YELLOW to listOf(p(4,0), p(4,1), p(4,2), p(4,3)),
            DotColor.ORANGE to listOf(p(5,0), p(5,1), p(5,2), p(5,3)),
            DotColor.PURPLE to listOf(p(4,4), p(5,4), p(5,5), p(4,5))
        )),
        // medium_6
        createLevel("medium_6", 6, Difficulty.MEDIUM, listOf(
            DotColor.RED to listOf(p(0,0), p(0,1), p(0,2), p(0,3), p(0,4), p(0,5), p(1,5), p(2,5), p(3,5), p(4,5), p(5,5), p(5,4)),
            DotColor.BLUE to listOf(p(1,0), p(2,0), p(3,0), p(4,0), p(5,0), p(5,1), p(4,1), p(3,1), p(2,1), p(1,1)),
            DotColor.GREEN to listOf(p(1,2), p(1,3), p(1,4), p(2,4)),
            DotColor.YELLOW to listOf(p(2,2), p(3,2), p(4,2), p(5,2), p(5,3), p(4,3)),
            DotColor.ORANGE to listOf(p(2,3), p(3,3)),
            DotColor.PURPLE to listOf(p(3,4), p(4,4))
        )),
        // medium_7
        createLevel("medium_7", 7, Difficulty.MEDIUM, listOf(
            DotColor.RED to listOf(p(0,0), p(1,0), p(2,0), p(3,0), p(4,0), p(5,0), p(5,1), p(4,1), p(3,1), p(2,1), p(1,1), p(0,1)),
            DotColor.BLUE to listOf(p(0,2), p(0,3), p(0,4), p(0,5), p(1,5), p(2,5)),
            DotColor.GREEN to listOf(p(1,2), p(1,3), p(1,4), p(2,4), p(2,3), p(2,2)),
            DotColor.YELLOW to listOf(p(3,2), p(3,3), p(3,4), p(3,5)),
            DotColor.ORANGE to listOf(p(4,2), p(4,3), p(4,4), p(4,5)),
            DotColor.PURPLE to listOf(p(5,2), p(5,3), p(5,4), p(5,5))
        )),
        // medium_8
        createLevel("medium_8", 8, Difficulty.MEDIUM, listOf(
            DotColor.RED to listOf(p(0,0), p(0,1), p(0,2), p(0,3), p(0,4), p(0,5), p(1,5), p(2,5), p(3,5), p(4,5), p(5,5)),
            DotColor.BLUE to listOf(p(1,0), p(1,1), p(1,2), p(1,3), p(1,4)),
            DotColor.GREEN to listOf(p(2,0), p(2,1), p(2,2), p(2,3), p(2,4)),
            DotColor.YELLOW to listOf(p(3,0), p(3,1), p(3,2), p(3,3), p(3,4)),
            DotColor.ORANGE to listOf(p(4,0), p(4,1), p(4,2), p(4,3), p(4,4)),
            DotColor.PURPLE to listOf(p(5,0), p(5,1), p(5,2), p(5,3), p(5,4))
        )),
        // medium_9
        createLevel("medium_9", 9, Difficulty.MEDIUM, listOf(
            DotColor.RED to listOf(p(0,0), p(0,1), p(1,1), p(1,0), p(2,0), p(3,0), p(3,1), p(2,1), p(2,2), p(1,2), p(0,2)),
            DotColor.BLUE to listOf(p(0,3), p(0,4), p(0,5), p(1,5), p(1,4), p(1,3)),
            DotColor.GREEN to listOf(p(2,3), p(2,4), p(2,5), p(3,5), p(3,4), p(3,3), p(3,2)),
            DotColor.YELLOW to listOf(p(4,0), p(4,1), p(4,2), p(5,2), p(5,1), p(5,0)),
            DotColor.ORANGE to listOf(p(4,3), p(4,4), p(4,5)),
            DotColor.PURPLE to listOf(p(5,3), p(5,4), p(5,5))
        )),
        // medium_10
        createLevel("medium_10", 10, Difficulty.MEDIUM, listOf(
            DotColor.RED to listOf(p(0,0), p(0,1), p(0,2), p(1,2), p(1,1), p(1,0), p(2,0), p(2,1), p(2,2), p(2,3), p(1,3), p(0,3), p(0,4), p(1,4), p(2,4), p(2,5), p(1,5), p(0,5)),
            DotColor.BLUE to listOf(p(3,0), p(3,1), p(3,2), p(3,3), p(3,4), p(3,5)),
            DotColor.GREEN to listOf(p(4,0), p(4,1), p(4,2), p(4,3)),
            DotColor.YELLOW to listOf(p(5,0), p(5,1), p(5,2), p(5,3)),
            DotColor.ORANGE to listOf(p(4,4), p(4,5)),
            DotColor.PURPLE to listOf(p(5,4), p(5,5))
        ))
    )

    // ------------------- 7x7 HARD LEVELS -------------------
    private val hardLevels = listOf(
        // hard_1
        createLevel("hard_1", 1, Difficulty.HARD, listOf(
            DotColor.RED to listOf(p(0,0), p(0,1), p(0,2), p(0,3), p(0,4), p(0,5), p(0,6), p(1,6), p(2,6), p(3,6), p(4,6), p(5,6), p(6,6)),
            DotColor.BLUE to listOf(p(1,0), p(1,1), p(1,2), p(1,3), p(1,4), p(1,5)),
            DotColor.GREEN to listOf(p(2,0), p(2,1), p(2,2), p(2,3), p(2,4), p(2,5)),
            DotColor.YELLOW to listOf(p(3,0), p(3,1), p(3,2), p(3,3), p(3,4), p(3,5)),
            DotColor.ORANGE to listOf(p(4,0), p(4,1), p(4,2), p(4,3), p(4,4), p(4,5)),
            DotColor.PURPLE to listOf(p(5,0), p(5,1), p(5,2), p(5,3), p(5,4), p(5,5)),
            DotColor.CYAN to listOf(p(6,0), p(6,1), p(6,2), p(6,3), p(6,4), p(6,5))
        )),
        // hard_2
        createLevel("hard_2", 2, Difficulty.HARD, listOf(
            DotColor.RED to listOf(p(0,0), p(1,0), p(2,0), p(3,0), p(4,0), p(5,0), p(6,0), p(6,1), p(5,1), p(4,1), p(3,1), p(2,1), p(1,1), p(0,1)),
            DotColor.BLUE to listOf(p(0,2), p(0,3), p(0,4), p(0,5), p(0,6), p(1,6), p(2,6), p(3,6), p(4,6), p(5,6), p(6,6)),
            DotColor.GREEN to listOf(p(1,2), p(2,2), p(3,2), p(4,2), p(5,2), p(6,2)),
            DotColor.YELLOW to listOf(p(1,3), p(2,3), p(3,3), p(4,3), p(5,3), p(6,3)),
            DotColor.ORANGE to listOf(p(1,4), p(2,4), p(3,4), p(4,4), p(5,4), p(6,4)),
            DotColor.PURPLE to listOf(p(1,5), p(2,5), p(3,5), p(4,5)),
            DotColor.CYAN to listOf(p(5,5), p(6,5))
        )),
        // hard_3
        createLevel("hard_3", 3, Difficulty.HARD, listOf(
            DotColor.RED to listOf(p(0,0), p(0,1), p(0,2), p(0,3), p(0,4), p(0,5), p(0,6), p(1,6), p(1,5), p(1,4), p(1,3), p(1,2), p(1,1), p(1,0)),
            DotColor.BLUE to listOf(p(2,0), p(2,1), p(2,2), p(2,3), p(2,4), p(2,5), p(2,6)),
            DotColor.GREEN to listOf(p(3,0), p(3,1), p(3,2), p(3,3), p(3,4), p(3,5), p(3,6)),
            DotColor.YELLOW to listOf(p(4,0), p(4,1), p(4,2), p(4,3), p(4,4), p(4,5), p(4,6)),
            DotColor.ORANGE to listOf(p(5,0), p(5,1), p(5,2), p(5,3), p(5,4), p(5,5), p(5,6)),
            DotColor.PURPLE to listOf(p(6,0), p(6,1), p(6,2), p(6,3)),
            DotColor.CYAN to listOf(p(6,4), p(6,5), p(6,6))
        )),
        // hard_4
        createLevel("hard_4", 4, Difficulty.HARD, listOf(
            DotColor.RED to listOf(p(0,0), p(0,1), p(0,2), p(1,2), p(1,1), p(1,0), p(2,0), p(2,1), p(2,2), p(3,2), p(3,1), p(3,0), p(4,0), p(4,1), p(4,2)),
            DotColor.BLUE to listOf(p(0,3), p(0,4), p(0,5), p(0,6), p(1,6), p(1,5), p(1,4), p(1,3), p(2,3), p(2,4), p(2,5), p(2,6)),
            DotColor.GREEN to listOf(p(3,3), p(3,4), p(3,5), p(3,6), p(4,6), p(5,6), p(6,6)),
            DotColor.YELLOW to listOf(p(5,0), p(5,1), p(5,2), p(6,2), p(6,1), p(6,0)),
            DotColor.ORANGE to listOf(p(4,3), p(4,4), p(4,5)),
            DotColor.PURPLE to listOf(p(5,3), p(5,4), p(5,5)),
            DotColor.CYAN to listOf(p(6,3), p(6,4), p(6,5))
        )),
        // hard_5
        createLevel("hard_5", 5, Difficulty.HARD, listOf(
            DotColor.RED to listOf(p(0,0), p(0,1), p(0,2), p(0,3), p(0,4), p(0,5), p(0,6), p(1,6), p(2,6), p(3,6), p(4,6), p(5,6), p(6,6), p(6,5)),
            DotColor.BLUE to listOf(p(1,0), p(2,0), p(3,0), p(4,0), p(5,0), p(6,0), p(6,1), p(5,1), p(4,1), p(3,1), p(2,1), p(1,1)),
            DotColor.GREEN to listOf(p(1,2), p(1,3), p(1,4), p(1,5), p(2,5), p(3,5), p(4,5), p(5,5)),
            DotColor.YELLOW to listOf(p(2,2), p(3,2), p(4,2), p(5,2), p(6,2)),
            DotColor.ORANGE to listOf(p(2,3), p(3,3), p(4,3), p(5,3), p(6,3)),
            DotColor.PURPLE to listOf(p(2,4), p(3,4), p(4,4)),
            DotColor.CYAN to listOf(p(5,4), p(6,4))
        )),
        // hard_6
        createLevel("hard_6", 6, Difficulty.HARD, listOf(
            DotColor.RED to listOf(p(0,0), p(1,0), p(2,0), p(3,0), p(4,0), p(5,0), p(6,0), p(6,1), p(6,2), p(6,3), p(6,4), p(6,5), p(6,6)),
            DotColor.BLUE to listOf(p(0,1), p(0,2), p(0,3), p(0,4), p(0,5), p(0,6), p(1,6), p(2,6), p(3,6), p(4,6), p(5,6)),
            DotColor.GREEN to listOf(p(1,1), p(1,2), p(1,3), p(1,4), p(1,5), p(2,5), p(3,5), p(4,5), p(5,5)),
            DotColor.YELLOW to listOf(p(2,1), p(2,2), p(2,3), p(2,4), p(3,4), p(4,4), p(5,4)),
            DotColor.ORANGE to listOf(p(3,1), p(3,2), p(3,3), p(4,3), p(5,3)),
            DotColor.PURPLE to listOf(p(4,1), p(4,2)),
            DotColor.CYAN to listOf(p(5,1), p(5,2))
        )),
        // hard_7
        createLevel("hard_7", 7, Difficulty.HARD, listOf(
            DotColor.RED to listOf(p(0,0), p(0,1), p(0,2), p(0,3), p(0,4), p(0,5), p(0,6), p(1,6), p(1,5), p(1,4), p(1,3), p(1,2), p(1,1), p(1,0)),
            DotColor.BLUE to listOf(p(2,0), p(2,1), p(2,2), p(2,3), p(2,4), p(2,5), p(2,6)),
            DotColor.GREEN to listOf(p(3,0), p(3,1), p(3,2), p(3,3), p(3,4), p(3,5), p(3,6)),
            DotColor.YELLOW to listOf(p(4,0), p(4,1), p(4,2), p(4,3), p(4,4), p(4,5), p(4,6)),
            DotColor.ORANGE to listOf(p(5,0), p(5,1), p(5,2), p(5,3), p(5,4), p(5,5), p(5,6)),
            DotColor.PURPLE to listOf(p(6,0), p(6,1), p(6,2), p(6,3)),
            DotColor.CYAN to listOf(p(6,4), p(6,5), p(6,6))
        )),
        // hard_8
        createLevel("hard_8", 8, Difficulty.HARD, listOf(
            DotColor.RED to listOf(p(0,0), p(0,1), p(1,1), p(1,0), p(2,0), p(3,0), p(3,1), p(2,1), p(2,2), p(1,2), p(0,2), p(0,3), p(1,3), p(2,3), p(3,3), p(3,2)),
            DotColor.BLUE to listOf(p(0,4), p(0,5), p(0,6), p(1,6), p(1,5), p(1,4), p(2,4), p(2,5), p(2,6), p(3,6), p(3,5), p(3,4)),
            DotColor.GREEN to listOf(p(4,0), p(4,1), p(4,2), p(4,3), p(4,4), p(4,5), p(4,6)),
            DotColor.YELLOW to listOf(p(5,0), p(5,1), p(5,2), p(5,3)),
            DotColor.ORANGE to listOf(p(6,0), p(6,1), p(6,2), p(6,3)),
            DotColor.PURPLE to listOf(p(5,4), p(5,5), p(5,6)),
            DotColor.CYAN to listOf(p(6,4), p(6,5), p(6,6))
        )),
        // hard_9
        createLevel("hard_9", 9, Difficulty.HARD, listOf(
            DotColor.RED to listOf(p(0,0), p(0,1), p(0,2), p(0,3), p(0,4), p(0,5), p(0,6), p(1,6), p(2,6), p(3,6), p(4,6), p(5,6), p(6,6)),
            DotColor.BLUE to listOf(p(1,0), p(1,1), p(1,2), p(1,3), p(1,4), p(1,5)),
            DotColor.GREEN to listOf(p(2,0), p(2,1), p(2,2), p(2,3), p(2,4), p(2,5)),
            DotColor.YELLOW to listOf(p(3,0), p(3,1), p(3,2), p(3,3), p(3,4), p(3,5)),
            DotColor.ORANGE to listOf(p(4,0), p(4,1), p(4,2), p(4,3), p(4,4), p(4,5)),
            DotColor.PURPLE to listOf(p(5,0), p(5,1), p(5,2), p(5,3), p(5,4), p(5,5)),
            DotColor.CYAN to listOf(p(6,0), p(6,1), p(6,2), p(6,3), p(6,4), p(6,5))
        )),
        // hard_10
        createLevel("hard_10", 10, Difficulty.HARD, listOf(
            DotColor.RED to listOf(p(0,0), p(0,1), p(0,2), p(1,2), p(1,1), p(1,0), p(2,0), p(2,1), p(2,2), p(2,3), p(1,3), p(0,3), p(0,4), p(1,4), p(2,4), p(2,5), p(1,5), p(0,5), p(0,6), p(1,6), p(2,6)),
            DotColor.BLUE to listOf(p(3,0), p(3,1), p(3,2), p(3,3), p(3,4), p(3,5), p(3,6)),
            DotColor.GREEN to listOf(p(4,0), p(4,1), p(4,2), p(4,3), p(4,4), p(4,5), p(4,6)),
            DotColor.YELLOW to listOf(p(5,0), p(5,1), p(5,2), p(5,3)),
            DotColor.ORANGE to listOf(p(6,0), p(6,1), p(6,2), p(6,3)),
            DotColor.PURPLE to listOf(p(5,4), p(5,5), p(5,6)),
            DotColor.CYAN to listOf(p(6,4), p(6,5), p(6,6))
        ))
    )

    // ------------------- 8x8 EXPERT LEVELS -------------------
    private val expertLevels = listOf(
        // expert_1
        createLevel("expert_1", 1, Difficulty.EXPERT, listOf(
            DotColor.RED to listOf(p(0,0), p(0,1), p(0,2), p(0,3), p(0,4), p(0,5), p(0,6), p(0,7), p(1,7), p(2,7), p(3,7), p(4,7), p(5,7), p(6,7), p(7,7)),
            DotColor.BLUE to listOf(p(1,0), p(1,1), p(1,2), p(1,3), p(1,4), p(1,5), p(1,6)),
            DotColor.GREEN to listOf(p(2,0), p(2,1), p(2,2), p(2,3), p(2,4), p(2,5), p(2,6)),
            DotColor.YELLOW to listOf(p(3,0), p(3,1), p(3,2), p(3,3), p(3,4), p(3,5), p(3,6)),
            DotColor.ORANGE to listOf(p(4,0), p(4,1), p(4,2), p(4,3), p(4,4), p(4,5), p(4,6)),
            DotColor.PURPLE to listOf(p(5,0), p(5,1), p(5,2), p(5,3), p(5,4), p(5,5), p(5,6)),
            DotColor.CYAN to listOf(p(6,0), p(6,1), p(6,2), p(6,3), p(6,4), p(6,5), p(6,6)),
            DotColor.PINK to listOf(p(7,0), p(7,1), p(7,2), p(7,3), p(7,4), p(7,5), p(7,6))
        )),
        // expert_2
        createLevel("expert_2", 2, Difficulty.EXPERT, listOf(
            DotColor.RED to listOf(p(0,0), p(1,0), p(2,0), p(3,0), p(4,0), p(5,0), p(6,0), p(7,0), p(7,1), p(6,1), p(5,1), p(4,1), p(3,1), p(2,1), p(1,1), p(0,1)),
            DotColor.BLUE to listOf(p(0,2), p(0,3), p(0,4), p(0,5), p(0,6), p(0,7), p(1,7), p(2,7), p(3,7), p(4,7), p(5,7), p(6,7), p(7,7)),
            DotColor.GREEN to listOf(p(1,2), p(2,2), p(3,2), p(4,2), p(5,2), p(6,2), p(7,2)),
            DotColor.YELLOW to listOf(p(1,3), p(2,3), p(3,3), p(4,3), p(5,3), p(6,3), p(7,3)),
            DotColor.ORANGE to listOf(p(1,4), p(2,4), p(3,4), p(4,4), p(5,4), p(6,4), p(7,4)),
            DotColor.PURPLE to listOf(p(1,5), p(2,5), p(3,5), p(4,5), p(5,5), p(6,5), p(7,5)),
            DotColor.CYAN to listOf(p(1,6), p(2,6), p(3,6), p(4,6)),
            DotColor.PINK to listOf(p(5,6), p(6,6), p(7,6))
        )),
        // expert_3
        createLevel("expert_3", 3, Difficulty.EXPERT, listOf(
            DotColor.RED to listOf(p(0,0), p(0,1), p(0,2), p(0,3), p(0,4), p(0,5), p(0,6), p(0,7), p(1,7), p(1,6), p(1,5), p(1,4), p(1,3), p(1,2), p(1,1), p(1,0)),
            DotColor.BLUE to listOf(p(2,0), p(2,1), p(2,2), p(2,3), p(2,4), p(2,5), p(2,6), p(2,7)),
            DotColor.GREEN to listOf(p(3,0), p(3,1), p(3,2), p(3,3), p(3,4), p(3,5), p(3,6), p(3,7)),
            DotColor.YELLOW to listOf(p(4,0), p(4,1), p(4,2), p(4,3), p(4,4), p(4,5), p(4,6), p(4,7)),
            DotColor.ORANGE to listOf(p(5,0), p(5,1), p(5,2), p(5,3), p(5,4), p(5,5), p(5,6), p(5,7)),
            DotColor.PURPLE to listOf(p(6,0), p(6,1), p(6,2), p(6,3), p(6,4), p(6,5), p(6,6), p(6,7)),
            DotColor.CYAN to listOf(p(7,0), p(7,1), p(7,2), p(7,3)),
            DotColor.PINK to listOf(p(7,4), p(7,5), p(7,6), p(7,7))
        )),
        // expert_4
        createLevel("expert_4", 4, Difficulty.EXPERT, listOf(
            DotColor.RED to listOf(p(0,0), p(0,1), p(0,2), p(1,2), p(1,1), p(1,0), p(2,0), p(2,1), p(2,2), p(3,2), p(3,1), p(3,0), p(4,0), p(4,1), p(4,2), p(5,2), p(5,1), p(5,0), p(6,0), p(6,1), p(6,2)),
            DotColor.BLUE to listOf(p(0,3), p(0,4), p(0,5), p(0,6), p(0,7), p(1,7), p(1,6), p(1,5), p(1,4), p(1,3), p(2,3), p(2,4), p(2,5), p(2,6), p(2,7)),
            DotColor.GREEN to listOf(p(3,3), p(3,4), p(3,5), p(3,6), p(3,7), p(4,7), p(5,7), p(6,7), p(7,7)),
            DotColor.YELLOW to listOf(p(7,0), p(7,1), p(7,2), p(7,3)),
            DotColor.ORANGE to listOf(p(4,3), p(4,4), p(4,5), p(4,6)),
            DotColor.PURPLE to listOf(p(5,3), p(5,4), p(5,5), p(5,6)),
            DotColor.CYAN to listOf(p(6,3), p(6,4), p(6,5), p(6,6)),
            DotColor.PINK to listOf(p(7,4), p(7,5), p(7,6))
        )),
        // expert_5
        createLevel("expert_5", 5, Difficulty.EXPERT, listOf(
            DotColor.RED to listOf(p(0,0), p(0,1), p(0,2), p(0,3), p(0,4), p(0,5), p(0,6), p(0,7), p(1,7), p(2,7), p(3,7), p(4,7), p(5,7), p(6,7), p(7,7), p(7,6)),
            DotColor.BLUE to listOf(p(1,0), p(2,0), p(3,0), p(4,0), p(5,0), p(6,0), p(7,0), p(7,1), p(6,1), p(5,1), p(4,1), p(3,1), p(2,1), p(1,1)),
            DotColor.GREEN to listOf(p(1,2), p(1,3), p(1,4), p(1,5), p(1,6), p(2,6), p(3,6), p(4,6), p(5,6), p(6,6)),
            DotColor.YELLOW to listOf(p(2,2), p(3,2), p(4,2), p(5,2), p(6,2), p(7,2)),
            DotColor.ORANGE to listOf(p(2,3), p(3,3), p(4,3), p(5,3), p(6,3), p(7,3)),
            DotColor.PURPLE to listOf(p(2,4), p(3,4), p(4,4), p(5,4), p(6,4), p(7,4)),
            DotColor.CYAN to listOf(p(2,5), p(3,5), p(4,5)),
            DotColor.PINK to listOf(p(5,5), p(6,5), p(7,5))
        )),
        // expert_6
        createLevel("expert_6", 6, Difficulty.EXPERT, listOf(
            DotColor.RED to listOf(p(0,0), p(1,0), p(2,0), p(3,0), p(4,0), p(5,0), p(6,0), p(7,0), p(7,1), p(7,2), p(7,3), p(7,4), p(7,5), p(7,6), p(7,7)),
            DotColor.BLUE to listOf(p(0,1), p(0,2), p(0,3), p(0,4), p(0,5), p(0,6), p(0,7), p(1,7), p(2,7), p(3,7), p(4,7), p(5,7), p(6,7)),
            DotColor.GREEN to listOf(p(1,1), p(1,2), p(1,3), p(1,4), p(1,5), p(1,6), p(2,6), p(3,6), p(4,6), p(5,6), p(6,6)),
            DotColor.YELLOW to listOf(p(2,1), p(2,2), p(2,3), p(2,4), p(2,5), p(3,5), p(4,5), p(5,5), p(6,5)),
            DotColor.ORANGE to listOf(p(3,1), p(3,2), p(3,3), p(3,4), p(4,4), p(5,4), p(6,4)),
            DotColor.PURPLE to listOf(p(4,1), p(4,2), p(4,3), p(5,3), p(6,3)),
            DotColor.CYAN to listOf(p(5,1), p(5,2)),
            DotColor.PINK to listOf(p(6,1), p(6,2))
        )),
        // expert_7
        createLevel("expert_7", 7, Difficulty.EXPERT, listOf(
            DotColor.RED to listOf(p(0,0), p(0,1), p(0,2), p(0,3), p(0,4), p(0,5), p(0,6), p(0,7), p(1,7), p(1,6), p(1,5), p(1,4), p(1,3), p(1,2), p(1,1), p(1,0)),
            DotColor.BLUE to listOf(p(2,0), p(2,1), p(2,2), p(2,3), p(2,4), p(2,5), p(2,6), p(2,7)),
            DotColor.GREEN to listOf(p(3,0), p(3,1), p(3,2), p(3,3), p(3,4), p(3,5), p(3,6), p(3,7)),
            DotColor.YELLOW to listOf(p(4,0), p(4,1), p(4,2), p(4,3), p(4,4), p(4,5), p(4,6), p(4,7)),
            DotColor.ORANGE to listOf(p(5,0), p(5,1), p(5,2), p(5,3), p(5,4), p(5,5), p(5,6), p(5,7)),
            DotColor.PURPLE to listOf(p(6,0), p(6,1), p(6,2), p(6,3), p(6,4), p(6,5), p(6,6), p(6,7)),
            DotColor.CYAN to listOf(p(7,0), p(7,1), p(7,2), p(7,3)),
            DotColor.PINK to listOf(p(7,4), p(7,5), p(7,6), p(7,7))
        )),
        // expert_8
        createLevel("expert_8", 8, Difficulty.EXPERT, listOf(
            DotColor.RED to listOf(p(0,0), p(0,1), p(1,1), p(1,0), p(2,0), p(3,0), p(3,1), p(2,1), p(2,2), p(1,2), p(0,2), p(0,3), p(1,3), p(2,3), p(3,3), p(3,2)),
            DotColor.BLUE to listOf(p(0,4), p(0,5), p(0,6), p(0,7), p(1,7), p(1,6), p(1,5), p(1,4), p(2,4), p(2,5), p(2,6), p(2,7), p(3,7), p(3,6), p(3,5), p(3,4)),
            DotColor.GREEN to listOf(p(4,0), p(4,1), p(4,2), p(4,3), p(4,4), p(4,5), p(4,6), p(4,7)),
            DotColor.YELLOW to listOf(p(5,0), p(5,1), p(5,2), p(5,3), p(5,4), p(5,5), p(5,6), p(5,7)),
            DotColor.ORANGE to listOf(p(6,0), p(6,1), p(6,2), p(6,3)),
            DotColor.PURPLE to listOf(p(6,4), p(6,5), p(6,6), p(6,7)),
            DotColor.CYAN to listOf(p(7,0), p(7,1), p(7,2), p(7,3)),
            DotColor.PINK to listOf(p(7,4), p(7,5), p(7,6), p(7,7))
        )),
        // expert_9
        createLevel("expert_9", 9, Difficulty.EXPERT, listOf(
            DotColor.RED to listOf(p(0,0), p(0,1), p(0,2), p(0,3), p(0,4), p(0,5), p(0,6), p(0,7), p(1,7), p(2,7), p(3,7), p(4,7), p(5,7), p(6,7), p(7,7)),
            DotColor.BLUE to listOf(p(1,0), p(1,1), p(1,2), p(1,3), p(1,4), p(1,5), p(1,6)),
            DotColor.GREEN to listOf(p(2,0), p(2,1), p(2,2), p(2,3), p(2,4), p(2,5), p(2,6)),
            DotColor.YELLOW to listOf(p(3,0), p(3,1), p(3,2), p(3,3), p(3,4), p(3,5), p(3,6)),
            DotColor.ORANGE to listOf(p(4,0), p(4,1), p(4,2), p(4,3), p(4,4), p(4,5), p(4,6)),
            DotColor.PURPLE to listOf(p(5,0), p(5,1), p(5,2), p(5,3), p(5,4), p(5,5), p(5,6)),
            DotColor.CYAN to listOf(p(6,0), p(6,1), p(6,2), p(6,3), p(6,4), p(6,5), p(6,6)),
            DotColor.PINK to listOf(p(7,0), p(7,1), p(7,2), p(7,3), p(7,4), p(7,5), p(7,6))
        )),
        // expert_10
        createLevel("expert_10", 10, Difficulty.EXPERT, listOf(
            DotColor.RED to listOf(p(0,0), p(0,1), p(0,2), p(1,2), p(1,1), p(1,0), p(2,0), p(2,1), p(2,2), p(2,3), p(1,3), p(0,3), p(0,4), p(1,4), p(2,4), p(2,5), p(1,5), p(0,5), p(0,6), p(1,6), p(2,6), p(2,7), p(1,7), p(0,7)),
            DotColor.BLUE to listOf(p(3,0), p(3,1), p(3,2), p(3,3), p(3,4), p(3,5), p(3,6), p(3,7)),
            DotColor.GREEN to listOf(p(4,0), p(4,1), p(4,2), p(4,3), p(4,4), p(4,5), p(4,6), p(4,7)),
            DotColor.YELLOW to listOf(p(5,0), p(5,1), p(5,2), p(5,3), p(5,4), p(5,5), p(5,6), p(5,7)),
            DotColor.ORANGE to listOf(p(6,0), p(6,1), p(6,2), p(6,3)),
            DotColor.PURPLE to listOf(p(6,4), p(6,5), p(6,6), p(6,7)),
            DotColor.CYAN to listOf(p(7,0), p(7,1), p(7,2), p(7,3)),
            DotColor.PINK to listOf(p(7,4), p(7,5), p(7,6), p(7,7))
        ))
    )
}
