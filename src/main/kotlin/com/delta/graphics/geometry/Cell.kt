package com.delta.graphics.geometry

import com.badlogic.gdx.math.Polygon
import com.delta.graphics.config.ColorSettings


/**
 * Представляет собой реализацию "клетки" на доске.
 * @property raw номер строки, в которой клетка находится.
 * @property col номер столбца, в которой клетка находится.
 * @property color текущий цвет клетки.
 * @property polygon полигон (точнее, квадрат), который представляет эту клетку.
 * @property cellSize размер клетки.
 */
class Cell(val raw: Int, val col: Int) {
    val color = ColorSettings.EmptyCellColor
    val cellSize: Float = 1.0f
    val polygon = createCellPolygon()

    /**
     * Возвращает квадрат на плоскости, представляющий эту клетку.
     * См. [Polygon] чтобы узнать как его создавать и какие методы вам могут помочь.
     * Он должен зависеть от координат клетки и её размера
     */
    private fun createCellPolygon(): Polygon {
        TODO()
    }
}