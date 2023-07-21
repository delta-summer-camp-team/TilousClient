package com.delta.graphics.controllers

import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.math.Vector2
import com.delta.graphics.Screen
import com.delta.graphics.geometry.Camera
import com.delta.graphics.geometry.Cell

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
    private val placeCellHandler: (raw: Int, col: Int) -> Boolean,
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
        return true
    }

}