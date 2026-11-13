package com.tariqnaeemdeveloper.colorlink.engine

import com.tariqnaeemdeveloper.colorlink.model.ColorPair
import com.tariqnaeemdeveloper.colorlink.model.Level
import com.tariqnaeemdeveloper.colorlink.model.PathState
import com.tariqnaeemdeveloper.colorlink.model.Position

class ColorLinkEngine(initialLevel: Level) {

    var level: Level = initialLevel
        private set

    var paths: Map<Int, PathState> = emptyMap()
        private set

    var activeDrawingColorId: Int? = null
        private set

    var moveCount: Int = 0
        private set

    private val history = mutableListOf<Map<Int, PathState>>()

    init {
        loadLevel(initialLevel)
    }

    fun loadLevel(newLevel: Level) {
        level = newLevel
        paths = newLevel.colorPairs.associate { pair ->
            pair.id to PathState(colorId = pair.id, points = emptyList(), isCompleted = false)
        }
        activeDrawingColorId = null
        moveCount = 0
        history.clear()
    }

    fun onCellTouchDown(pos: Position) {
        if (!isValidPosition(pos)) return

        val clickedPair = level.findPairForPosition(pos)
        val existingPathWithPos = paths.values.firstOrNull { it.contains(pos) }

        if (clickedPair != null) {
            // Started touching a dot endpoint
            saveHistorySnapshot()
            val colorId = clickedPair.id
            
            // Start a new path from this endpoint
            paths = paths.toMutableMap().apply {
                this[colorId] = PathState(
                    colorId = colorId,
                    points = listOf(pos),
                    isCompleted = false
                )
            }
            activeDrawingColorId = colorId
        } else if (existingPathWithPos != null) {
            // Touched an existing line path: trim line up to touched position
            saveHistorySnapshot()
            val colorId = existingPathWithPos.colorId
            val points = existingPathWithPos.points
            val posIndex = points.indexOf(pos)
            if (posIndex >= 0) {
                val trimmedPoints = points.subList(0, posIndex + 1)
                paths = paths.toMutableMap().apply {
                    this[colorId] = PathState(
                        colorId = colorId,
                        points = trimmedPoints,
                        isCompleted = false
                    )
                }
                activeDrawingColorId = colorId
            }
        }
    }

    fun onCellTouchMove(pos: Position) {
        val activeId = activeDrawingColorId ?: return
        if (!isValidPosition(pos)) return

        val currentPath = paths[activeId] ?: return
        val currentPoints = currentPath.points
        if (currentPoints.isEmpty()) return

        val lastPos = currentPoints.last()
        if (pos == lastPos) return // No move to a new cell

        // Check adjacency
        if (!lastPos.isAdjacentTo(pos)) return

        val colorPair = level.findPairById(activeId) ?: return

        // Backtracking check (user dragged back to previous point)
        if (currentPoints.size >= 2 && pos == currentPoints[currentPoints.size - 2]) {
            val newPoints = currentPoints.dropLast(1)
            updatePath(activeId, newPoints, isCompleted = false)
            return
        }

        // Self-collision check (dragged into earlier point of own path)
        if (currentPoints.contains(pos)) {
            val posIndex = currentPoints.indexOf(pos)
            val newPoints = currentPoints.subList(0, posIndex + 1)
            updatePath(activeId, newPoints, isCompleted = false)
            return
        }

        // Endpoint collision check for OTHER colors
        val otherPairEndpoint = level.colorPairs.firstOrNull { pair ->
            pair.id != activeId && (pair.start == pos || pair.end == pos)
        }
        if (otherPairEndpoint != null) {
            // Cannot cross into another color's endpoint
            return
        }

        // Path collision check with OTHER colors (crossing another color's line segment)
        val conflictingPathEntry = paths.entries.firstOrNull { (colorId, path) ->
            colorId != activeId && path.contains(pos)
        }
        if (conflictingPathEntry != null) {
            // Cut/break the conflicting path before 'pos'
            val otherColorId = conflictingPathEntry.key
            val otherPoints = conflictingPathEntry.value.points
            val conflictIndex = otherPoints.indexOf(pos)
            if (conflictIndex >= 0) {
                val remainingOtherPoints = otherPoints.subList(0, conflictIndex)
                paths = paths.toMutableMap().apply {
                    this[otherColorId] = PathState(
                        colorId = otherColorId,
                        points = remainingOtherPoints,
                        isCompleted = false
                    )
                }
            }
        }

        // Check if 'pos' is the target endpoint for current color
        val targetEndpoint = if (currentPoints.first() == colorPair.start) colorPair.end else colorPair.start
        val isTargetReached = (pos == targetEndpoint)

        val newPoints = currentPoints + pos
        updatePath(activeId, newPoints, isCompleted = isTargetReached)

        if (isTargetReached) {
            activeDrawingColorId = null
            moveCount++
        }
    }

    fun onCellTouchUp() {
        if (activeDrawingColorId != null) {
            activeDrawingColorId = null
            moveCount++
        }
    }

    fun undo() {
        if (history.isNotEmpty()) {
            paths = history.removeAt(history.size - 1)
            activeDrawingColorId = null
            if (moveCount > 0) moveCount--
        }
    }

    fun canUndo(): Boolean = history.isNotEmpty()

    fun reset() {
        if (paths.values.any { it.points.isNotEmpty() }) {
            saveHistorySnapshot()
            paths = level.colorPairs.associate { pair ->
                pair.id to PathState(colorId = pair.id, points = emptyList(), isCompleted = false)
            }
            activeDrawingColorId = null
            moveCount = 0
        }
    }

    fun applyHint(): Boolean {
        // Find first color pair that is not yet completed
        val uncompletedPair = level.colorPairs.firstOrNull { pair ->
            paths[pair.id]?.isCompleted != true
        } ?: return false

        // Prefer ground truth solution path if available
        val solutionPath = level.solutionPaths[uncompletedPair.id]
            ?: findPathForPair(uncompletedPair)
            ?: return false

        saveHistorySnapshot()

        // Remove conflicting paths of other colors along solutionPath
        val newPaths = paths.toMutableMap()
        solutionPath.forEach { pos ->
            newPaths.forEach { (otherId, pathState) ->
                if (otherId != uncompletedPair.id && pathState.contains(pos)) {
                    val otherPoints = pathState.points
                    val conflictIndex = otherPoints.indexOf(pos)
                    if (conflictIndex >= 0) {
                        newPaths[otherId] = PathState(
                            colorId = otherId,
                            points = otherPoints.subList(0, conflictIndex),
                            isCompleted = false
                        )
                    }
                }
            }
        }

        // Apply hint path for the uncompleted pair
        newPaths[uncompletedPair.id] = PathState(
            colorId = uncompletedPair.id,
            points = solutionPath,
            isCompleted = true
        )

        paths = newPaths
        activeDrawingColorId = null
        moveCount++
        return true
    }

    private fun findPathForPair(pair: ColorPair): List<Position>? {
        val start = pair.start
        val target = pair.end
        val gridSize = level.gridSize

        // Block endpoints of all OTHER color pairs
        val blockedEndpoints = level.colorPairs
            .filter { it.id != pair.id }
            .flatMap { listOf(it.start, it.end) }
            .toSet()

        // Block completed paths of OTHER color pairs
        val blockedPathCells = paths.entries
            .filter { it.key != pair.id && it.value.isCompleted }
            .flatMap { it.value.points }
            .toSet()

        val blocked = blockedEndpoints + blockedPathCells

        // BFS pathfinder
        val queue = ArrayDeque<List<Position>>()
        val visited = mutableSetOf<Position>()

        queue.add(listOf(start))
        visited.add(start)

        val directions = listOf(
            Position(-1, 0), Position(1, 0),
            Position(0, -1), Position(0, 1)
        )

        while (queue.isNotEmpty()) {
            val currentPath = queue.removeFirst()
            val last = currentPath.last()

            if (last == target) {
                return currentPath
            }

            for (dir in directions) {
                val next = Position(last.row + dir.row, last.col + dir.col)
                if (next.row in 0 until gridSize && next.col in 0 until gridSize) {
                    if (next !in visited && next !in blocked) {
                        visited.add(next)
                        queue.add(currentPath + next)
                    }
                }
            }
        }

        return null
    }

    private fun updatePath(colorId: Int, points: List<Position>, isCompleted: Boolean) {
        paths = paths.toMutableMap().apply {
            this[colorId] = PathState(colorId = colorId, points = points, isCompleted = isCompleted)
        }
    }

    private fun saveHistorySnapshot() {
        if (history.size >= 20) {
            history.removeAt(0)
        }
        history.add(paths)
    }

    private fun isValidPosition(pos: Position): Boolean {
        return pos.row in 0 until level.gridSize && pos.col in 0 until level.gridSize
    }

    fun getOccupiedCellsCount(): Int {
        val occupied = mutableSetOf<Position>()
        paths.values.forEach { path ->
            occupied.addAll(path.points)
        }
        return occupied.size
    }

    fun getCoveragePercentage(): Int {
        val total = level.totalCells
        if (total == 0) return 0
        val count = getOccupiedCellsCount()
        return ((count.toFloat() / total) * 100).toInt()
    }

    fun isAllConnected(): Boolean {
        return level.colorPairs.all { pair ->
            val path = paths[pair.id]
            path != null && path.isCompleted
        }
    }

    fun isLevelCompleted(): Boolean {
        return isAllConnected()
    }

    fun calculateStars(): Int {
        if (!isAllConnected()) return 0
        val coverage = getCoveragePercentage()
        return when {
            coverage >= 100 -> 3
            coverage >= 85 -> 2
            else -> 1
        }
    }
}
