package com.tariqnaeemdeveloper.colorlink.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import com.tariqnaeemdeveloper.colorlink.model.Level
import com.tariqnaeemdeveloper.colorlink.model.PathState
import com.tariqnaeemdeveloper.colorlink.model.Position

@Composable
fun ColorLinkBoard(
    level: Level,
    paths: Map<Int, PathState>,
    onCellTouchDown: (Position) -> Unit,
    onCellTouchMove: (Position) -> Unit,
    onCellTouchUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    val gridSize = level.gridSize
    val haptic = LocalHapticFeedback.current
    val tileColor = MaterialTheme.colorScheme.surface
    val strokeColor = MaterialTheme.colorScheme.surfaceVariant

    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .padding(16.dp)
            .clip(RoundedCornerShape(16.dp))
    ) {
        Canvas(
            modifier = Modifier
                .matchParentSize()
                .pointerInput(gridSize) {
                    awaitEachGesture {
                        val down = awaitFirstDown()
                        val cellSizePx = size.width.toFloat() / gridSize
                        val initialPos = getCellPosition(down.position, gridSize, cellSizePx)
                        if (initialPos != null) {
                            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                            onCellTouchDown(initialPos)
                        }
                        var lastPos = initialPos
                        do {
                            val event = awaitPointerEvent()
                            val change = event.changes.firstOrNull()
                            if (change != null && change.pressed) {
                                val currentPos = getCellPosition(change.position, gridSize, cellSizePx)
                                if (currentPos != null && currentPos != lastPos) {
                                    haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                    onCellTouchMove(currentPos)
                                    lastPos = currentPos
                                }
                            }
                        } while (event.changes.any { it.pressed })
                        onCellTouchUp()
                    }
                }
        ) {
            val canvasWidth = size.width
            val cellSize = canvasWidth / gridSize

            // 1. Draw Grid Tiles
            val tilePadding = cellSize * 0.04f
            val cornerRadius = CornerRadius(cellSize * 0.15f)
            for (r in 0 until gridSize) {
                for (c in 0 until gridSize) {
                    val left = c * cellSize + tilePadding
                    val top = r * cellSize + tilePadding
                    val tileSize = cellSize - (tilePadding * 2)

                    drawRoundRect(
                        color = tileColor,
                        topLeft = Offset(left, top),
                        size = Size(tileSize, tileSize),
                        cornerRadius = cornerRadius
                    )

                    drawRoundRect(
                        color = strokeColor,
                        topLeft = Offset(left, top),
                        size = Size(tileSize, tileSize),
                        cornerRadius = cornerRadius,
                        style = Stroke(width = 2f)
                    )
                }
            }

            // 2. Draw Pipe Lines
            val lineWidth = cellSize * 0.38f
            val glowWidth = cellSize * 0.52f

            paths.values.forEach { pathState ->
                val points = pathState.points
                if (points.size >= 2) {
                    val pair = level.findPairById(pathState.colorId)
                    val color = pair?.color?.color ?: Color.White
                    val glowColor = pair?.color?.glowColor ?: Color.White.copy(alpha = 0.3f)

                    val linePath = Path().apply {
                        val startCenter = getCellCenter(points.first(), cellSize)
                        moveTo(startCenter.x, startCenter.y)
                        for (i in 1 until points.size) {
                            val center = getCellCenter(points[i], cellSize)
                            lineTo(center.x, center.y)
                        }
                    }

                    // Glow line behind
                    drawPath(
                        path = linePath,
                        color = glowColor,
                        style = Stroke(
                            width = glowWidth,
                            cap = StrokeCap.Round,
                            join = StrokeJoin.Round
                        )
                    )

                    // Core line
                    drawPath(
                        path = linePath,
                        color = color,
                        style = Stroke(
                            width = lineWidth,
                            cap = StrokeCap.Round,
                            join = StrokeJoin.Round
                        )
                    )
                }
            }

            // 3. Draw Endpoint Dots
            level.colorPairs.forEach { pair ->
                val color = pair.color.color
                val glowColor = pair.color.glowColor
                val isCompleted = paths[pair.id]?.isCompleted == true

                listOf(pair.start, pair.end).forEach { endpoint ->
                    val center = getCellCenter(endpoint, cellSize)
                    val outerRadius = cellSize * 0.36f
                    val innerRadius = cellSize * 0.26f

                    // Outer Glow / Ring
                    drawCircle(
                        color = glowColor,
                        radius = outerRadius,
                        center = center
                    )

                    // Completed Indicator Ring
                    if (isCompleted) {
                        drawCircle(
                            color = Color.White,
                            radius = outerRadius,
                            center = center,
                            style = Stroke(width = 4f)
                        )
                    }

                    // Inner Dot
                    drawCircle(
                        color = color,
                        radius = innerRadius,
                        center = center
                    )

                    drawCircle(
                        color = Color.White.copy(alpha = 0.6f),
                        radius = innerRadius * 0.4f,
                        center = Offset(center.x - innerRadius * 0.3f, center.y - innerRadius * 0.3f)
                    )
                }
            }
        }
    }
}

private fun getCellPosition(offset: Offset, gridSize: Int, cellSizePx: Float): Position? {
    val col = (offset.x / cellSizePx).toInt()
    val row = (offset.y / cellSizePx).toInt()
    if (row in 0 until gridSize && col in 0 until gridSize) {
        return Position(row, col)
    }
    return null
}

private fun getCellCenter(pos: Position, cellSize: Float): Offset {
    return Offset(
        x = (pos.col + 0.5f) * cellSize,
        y = (pos.row + 0.5f) * cellSize
    )
}
