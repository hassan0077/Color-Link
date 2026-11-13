package com.tariqnaeemdeveloper.colorlink

import com.tariqnaeemdeveloper.colorlink.data.LevelRepository
import com.tariqnaeemdeveloper.colorlink.engine.ColorLinkEngine
import com.tariqnaeemdeveloper.colorlink.model.ColorPair
import com.tariqnaeemdeveloper.colorlink.model.Difficulty
import com.tariqnaeemdeveloper.colorlink.model.DotColor
import com.tariqnaeemdeveloper.colorlink.model.Level
import com.tariqnaeemdeveloper.colorlink.model.Position
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ColorLinkEngineTest {

    private lateinit var testLevel: Level
    private lateinit var engine: ColorLinkEngine

    @Before
    fun setUp() {
        // Simple 3x3 test level with 1 color pair
        testLevel = Level(
            id = "test_1",
            levelNumber = 1,
            difficulty = Difficulty.EASY,
            colorPairs = listOf(
                ColorPair(1, DotColor.RED, Position(0, 0), Position(0, 2))
            ),
            solutionPaths = mapOf(
                1 to listOf(Position(0, 0), Position(0, 1), Position(0, 2))
            )
        )
        engine = ColorLinkEngine(testLevel)
    }

    @Test
    fun testTouchDownOnStartDot_startsPath() {
        engine.onCellTouchDown(Position(0, 0))

        val path = engine.paths[1]
        assertFalse(path == null)
        assertEquals(listOf(Position(0, 0)), path?.points)
        assertFalse(path?.isCompleted == true)
    }

    @Test
    fun testDragToAdjacentCell_extendsPath() {
        engine.onCellTouchDown(Position(0, 0))
        engine.onCellTouchMove(Position(0, 1))

        val path = engine.paths[1]
        assertEquals(listOf(Position(0, 0), Position(0, 1)), path?.points)
        assertFalse(path?.isCompleted == true)
    }

    @Test
    fun testDragToEndDot_completesPathAndDetectsVictory() {
        engine.onCellTouchDown(Position(0, 0))
        engine.onCellTouchMove(Position(0, 1))
        engine.onCellTouchMove(Position(0, 2))

        val path = engine.paths[1]
        assertTrue(path?.isCompleted == true)
        assertTrue(engine.isAllConnected())
        assertTrue(engine.isLevelCompleted())
    }

    @Test
    fun testBacktrack_trimsPath() {
        engine.onCellTouchDown(Position(0, 0))
        engine.onCellTouchMove(Position(0, 1))
        engine.onCellTouchMove(Position(0, 0)) // Drag back

        val path = engine.paths[1]
        assertEquals(listOf(Position(0, 0)), path?.points)
    }

    @Test
    fun testReset_clearsAllPaths() {
        engine.onCellTouchDown(Position(0, 0))
        engine.onCellTouchMove(Position(0, 1))
        engine.reset()

        val path = engine.paths[1]
        assertTrue(path?.points?.isEmpty() == true)
    }

    @Test
    fun testApplyHint_solvesUncompletedPair() {
        val applied = engine.applyHint()

        assertTrue(applied)
        val path = engine.paths[1]
        assertTrue(path?.isCompleted == true)
        assertEquals(Position(0, 0), path?.points?.first())
        assertEquals(Position(0, 2), path?.points?.last())
    }

    @Test
    fun testValidateAllLevelsInRepositoryAreSolvableAndCover100Percent() {
        val allLevels = LevelRepository.difficulties
            .flatMap { LevelRepository.getLevelsForDifficulty(it) }

        val errors = mutableListOf<String>()

        for (level in allLevels) {
            val gridSize = level.gridSize
            val expectedTotalCells = gridSize * gridSize
            val coveredCells = mutableSetOf<Position>()

            if (level.solutionPaths.isEmpty()) {
                errors.add("${level.id}: missing solutionPaths")
                continue
            }

            for (pair in level.colorPairs) {
                val solution = level.solutionPaths[pair.id]
                if (solution == null) {
                    errors.add("${level.id}: pair ${pair.id} (${pair.color.colorName}) missing solution path")
                    continue
                }

                if (solution.first() != pair.start) {
                    errors.add("${level.id}: pair ${pair.id} start ${pair.start} does not match solution start ${solution.first()}")
                }

                if (solution.last() != pair.end) {
                    errors.add("${level.id}: pair ${pair.id} end ${pair.end} does not match solution end ${solution.last()}")
                }

                // Check adjacency of consecutive steps
                for (i in 0 until solution.size - 1) {
                    val p1 = solution[i]
                    val p2 = solution[i + 1]
                    if (!p1.isAdjacentTo(p2)) {
                        errors.add("${level.id}: pair ${pair.id} step $p1 to $p2 is not adjacent")
                    }
                }

                // Check cell bounds and overlap
                for (pos in solution) {
                    if (pos.row !in 0 until gridSize || pos.col !in 0 until gridSize) {
                        errors.add("${level.id}: cell $pos out of bounds for grid $gridSize")
                    }
                    if (coveredCells.contains(pos)) {
                        errors.add("${level.id}: cell $pos is covered by multiple solution paths")
                    }
                    coveredCells.add(pos)
                }
            }

            if (coveredCells.size != expectedTotalCells) {
                errors.add("${level.id}: covers ${coveredCells.size}/$expectedTotalCells cells")
            }
        }

        assertTrue(
            "Level validation failed with errors:\n" + errors.joinToString("\n"),
            errors.isEmpty()
        )
    }

    @Test
    fun testHintsSolveAllLevelsToVictory() {
        val allLevels = LevelRepository.difficulties
            .flatMap { LevelRepository.getLevelsForDifficulty(it) }

        for (level in allLevels) {
            val eng = ColorLinkEngine(level)
            var maxSteps = level.colorPairs.size + 1
            while (!eng.isLevelCompleted() && maxSteps > 0) {
                eng.applyHint()
                maxSteps--
            }

            assertTrue("Level ${level.id} could not be solved by hints", eng.isLevelCompleted())
            assertEquals("Level ${level.id} did not reach 100% coverage with solution", 100, eng.getCoveragePercentage())
            assertEquals("Level ${level.id} did not earn 3 stars", 3, eng.calculateStars())
        }
    }
}
