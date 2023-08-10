package com.delta.graphics.controllers

import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.math.Vector2
import com.delta.graphics.Screen
import com.delta.graphics.geometry.Camera
import com.delta.graphics.geometry.Cell

class BoardActionsController(
    private val cells: List<Cell>?,
    private val screenToWorld: (Int, Int) -> Vector2,
    private val placeCellHandler: (row: Int, col: Int) -> Boolean,
    private val finishTurnHandler: () -> Boolean
) : InputAdapter() {

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        // Convert screen coordinates to game coordinates using screenToWorld function
        val gameCoordinates = screenToWorld(screenX, screenY)

        // Iterate through the cells and check if the click is within any cell
        cells?.forEach { cell ->
            if (cell.polygon.contains(gameCoordinates.x, gameCoordinates.y)) {
                // Handle placing a cell at the clicked cell's position
                val row = cell.row
                val col = cell.col
                placeCellHandler(row, col)
                return true
            }
        }

        return false // No cell was clicked
    }

    override fun keyDown(keycode: Int): Boolean {
        if (keycode == com.badlogic.gdx.Input.Keys.ENTER) {
            // Handle finishing the turn when Enter key is pressed
            return finishTurnHandler()
        }
        println (keycode)

        return false
    }
}

