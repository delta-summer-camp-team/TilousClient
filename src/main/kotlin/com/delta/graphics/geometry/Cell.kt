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
        val arrayOfPoints = floatArrayOf(
            (0 + row) * cellSize, (0 + col) * cellSize,
            (1 + row) * cellSize, (0 + col) * cellSize,
            (1 + row) * cellSize, (1 + col) * cellSize,
            (0 + row) * cellSize, (1 + col) * cellSize)
        val polygon = Polygon(arrayOfPoints)
        return polygon


    }
}