package com.delta.graphics.controllers

import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.math.Vector2
import com.delta.graphics.Screen
import com.delta.graphics.geometry.Camera
import com.delta.graphics.geometry.Cell
import com.delta.PlayerID

/**
 *  Контролирует действия игрока. Принимает, приходящие от приложения, такие как
 *  клик мыши или нажатие клавиши и обрабатывает их. Методы возвращают `true`, если событие
 *  считается успешно обработанным и `false` иначе.
 *
 *  @property cells коллекция клеток.
 *  @property screenToWorld вспомогательная функция, которая переводит координаты пикселей в координаты игры.
 *  @property placeCellHandler функция, которая должна вызваться, если игрок хочет поставить клетку.
 *  @property finishTurnHandler функция, которая должна вызваться, если игрок хочет завершить ход.
 */
class BoardActionsController(
    private val cells: List<Cell>?,
    private val screenToWorld: (Int, Int) -> Vector2,
    private val placeCellHandler: (row: Int, col: Int) -> Boolean,
    private val finishTurnHandler: () -> Boolean,

    ) : InputAdapter() {

    /**
     * Вызывается, когда пользователь отпускает кнопку мыши.
     */
    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        // TODO
        return true
    }

    /**
     * Вызывается, когда пользователь нажимает клавишу на клавиатуре.
     */
    override fun keyDown(keycode: Int): Boolean {
        // TODO
        if (keycode == KeyEvent.KEYCODE_C) {
            chooseNextColor()
        }
        return true
    }

    private fun chooseNextColor(){
        val colors=list[Color(0.94f, 0.33f, 0.31f, 1f), Color(0.29f, 0.73f, 0.47f, 1f), Color(0.73f, 0.52f, 0.98f, 1.0f), Color(0.96f, 0.68f, 0.18f, 1f), Color(0.71f, 0.80f, 0.80f, 1f), Color(0.93f, 0.46f, 0.13f, 1f), Color(0.13f, 0.70f, 0.67f, 1f), Color(1.00f, 0.50f, 0.00f, 1f)]
        val usedColors = ColorSettings.ColorMap.values
        val playerID = gameState.playerID
        if playerID != null{
            var i = 0
            var currentColor = colors[0]
            while (currentColor in usedColors){
                currentColor = colors[(i + 1) mod colors.size]
                i += 1
            }
            updateColor(playerID, currentColor)
        }
    }

    private fun updateColor(playerID: PlayerID, currentColor: Color) {
        if (playerID == PlayerID.PLAYER_1)
            colorSettings.player1Color = currentColor
        if (playerID == PlayerID.PLAYER_2)
            colorSettings.player2Color = currentColor
        if (playerID == PlayerID.PLAYER_3)
            colorSettings.player3Color = currentColor
        if (playerID == PlayerID.PLAYER_4)
            colorSettings.player4Color = currentColor
        colorSettings.colorMap[playerID] = currentColor
    }
}