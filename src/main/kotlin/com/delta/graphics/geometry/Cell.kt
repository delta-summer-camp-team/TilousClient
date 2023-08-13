package com.delta.graphics.geometry

import com.badlogic.gdx.math.Polygon
import com.delta.graphics.config.ColorSettings


/**
 * Представляет собой реализацию "клетки" на доске.
 * @property row номер строки, в которой клетка находится. (x)
 * @property col номер столбца, в которой клетка находится. (y)
 * @property color текущий цвет клетки.
 * @property polygon полигон (точнее, квадрат), который представляет эту клетку.
 * @property cellSize размер клетки.
 */
class Cell(val row: Int, val col: Int) {
    var color = ColorSettings.EmptyCellColor
    val cellSize: Float = 0.6f
    val polygon = createCellPolygon()
    val prodPolygon = createCellPolygon(0.25f)
    val stablePolygon = createStablePolygon(0.3f)

    /**
     * Возвращает квадрат на плоскости, представляющий эту клетку.
     * См. [Polygon] чтобы узнать как его создавать и какие методы вам могут помочь.
     * Он должен зависеть от координат клетки и её размера
     */
    private fun createCellPolygon(mg: Float = 0.1f): Polygon {
        val margin = cellSize * mg // Adjust the margin to control the size of the cells
        val arrayOfPoints = floatArrayOf(
            (0 + row) * cellSize + margin, (0 + col) * cellSize + margin,
            (1 + row) * cellSize - margin, (0 + col) * cellSize + margin,
            (1 + row) * cellSize - margin, (1 + col) * cellSize - margin,
            (0 + row) * cellSize + margin, (1 + col) * cellSize - margin
        )
        return Polygon(arrayOfPoints)
    }
    private fun createStablePolygon(mg: Float = 0.1f): Polygon {
        val margin = cellSize * mg;
        val arrayOfPoints = floatArrayOf(
            (0 + row) * cellSize + margin, (0 + col) * cellSize + cellSize / 2,
            (1 + row) * cellSize - cellSize / 2, (0 + col) * cellSize + margin,
            (1 + row) * cellSize - margin, (1 + col) * cellSize - cellSize / 2,
            (0 + row) * cellSize + cellSize / 2, (1 + col) * cellSize - margin
        )

        return Polygon(arrayOfPoints)
    }
}