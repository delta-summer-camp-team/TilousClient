package com.delta.graphics.geometry

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.viewport.Viewport

/**
 * Класс-обёртка для уже существующей ортогональной камеры.
 */
class Camera() {
    private val camera = OrthographicCamera()

    /**
     * Устанавливает камеру в данную позицию
     */
    fun updatePosition(position: Vector3) {
        camera.position.set(position)
    }

    /**
     * Обновляет камеру. Используйте этот метод каждый раз после
     * того как поменяли какие-нибудь параметры (например позицию или зум)
     */
    fun update() = camera.update()

    /**
     * Возвращает матрицу проекции, которая нужна, например, для [ShapeRenderer].
     */
    fun projMatrix(): Matrix4? = camera.combined

    fun getPosition(): Vector3 = camera.position

    fun translate(vec: Vector3) {
        camera.translate(vec)
        camera.update()
    }

    /**
     * Переводит пиксельные координаты в мировые.
     */
    fun screenToWorld(screenX: Int, screenY: Int): Vector3 {
        val vec = Vector3(screenX.toFloat(), screenY.toFloat(), 0f)
        return camera.unproject(vec)
    }

    /**
     * Аналогично [screenToWorld], но возвращает 2D вектор
     */
    fun screenToWorld2D(screenX: Int, screenY: Int): Vector2 {
        val vec = Vector3(screenX.toFloat(), screenY.toFloat(), 0f)
        camera.unproject(vec)
        return Vector2(vec.x, vec.y)
    }

    /**
     * Адаптирует камеру под данный [viewport].
     */
    fun setToOrtho(viewport: Viewport) {
        camera.setToOrtho(true, viewport.worldWidth, viewport.worldHeight)
    }
}
