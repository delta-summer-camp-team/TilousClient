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
    val cellSize: Float = 1.0f
    val polygon = createCellPolygon()

    /**
     * Возвращает квадрат на плоскости, представляющий эту клетку.
     * См. [Polygon] чтобы узнать как его создавать и какие методы вам могут помочь.
     * Он должен зависеть от координат клетки и её размера
     */
    private fun createCellPolygon(): Polygon {
        val margin = cellSize * 0.1f // Adjust the margin to control the size of the cells
        val arrayOfPoints = floatArrayOf(
            (0 + row) * cellSize + margin, (0 + col) * cellSize + margin,
            (1 + row) * cellSize - margin, (0 + col) * cellSize + margin,
            (1 + row) * cellSize - margin, (1 + col) * cellSize - margin,
            (0 + row) * cellSize + margin, (1 + col) * cellSize - margin
        )
        return Polygon(arrayOfPoints)
    }

}