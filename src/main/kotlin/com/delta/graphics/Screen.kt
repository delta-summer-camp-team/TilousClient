package com.delta.graphics

// Внешние библиотеки требующиеся для отрисовки
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Polygon
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.ScreenViewport
import ktx.app.KtxScreen


import com.delta.*
import com.delta.graphics.geometry.Camera
import com.delta.graphics.geometry.Cell


/**
 * Основной класс, ответственный за рисовку игры. Наследует интерфейс [KtxScreen],
 * с переопределением нужных методов.
 * @property gameState текущее состояние игры. Не модифицировать его здесь!
 */
class Screen(
    private val gameState: GameState
) : KtxScreen {
    /** Инструмент для рисования сложных текстурированных объектов. Нам нужен только для отрисовки текста **/
    private val batch = SpriteBatch()

    /** Инструмент для отрисовки примитивных объектов (линии, многоугольники) **/
    private val shapeRenderer = ShapeRenderer()

    /**
     * На одном экране отображается несколько элементов с разных "ракурсов".
     * [viewport] и [uiViewport] отвечают именно за это. Для каждого из них существует своя камера.
     * Для [viewport] она настроена так, чтобы игровая доска помещалась на экран (или вообще является подвижной и управляется игроком).
     **/
    private val viewport = FitViewport(10f * Gdx.graphics.width / Gdx.graphics.height, 10f)

    /**
     * Для [uiViewport] камера настроена дефолтным образом, т.е. не происходит никакого масштабирования.
     * Нужна для отображения текста.
     * @see viewport
     **/
    private val uiViewport = ScreenViewport()

    var cells: List<Cell>? = null
        private set

    // настройки цвета и дополнительные параметры отрисовки.
    private var shouldBeMyBackgroundColor: Boolean = false
    private var myColor: Color? = null
    private val currentBackgroundColor: Color = Color.BLACK.cpy()

    val camera = Camera() // По-хорошему должно быть приватным, но пока это сложно

    // ui messages
    private var text = "Welcome!"

    /**
     * Смотрит на текущее состояние игры и обновляет цвет бэкграунда, клеток,
     * а так же устанавливает текст. Если игра только началась, генерирует массив клеток [cells].
     */
    private fun updateInfo() {
        //TODO
    }

    /**
     * Основная функция отрисовки. Вызывается автоматически приложением.
     * @param delta Время, прошедшее с предыдущего момента отрисовки.
     * Должна обновлять состояние компонентов с помощью [updateInfo], а затем
     * рисовать всё.
     */
    override fun render(delta: Float) {
        // TODO
    }

    /**
     * Вызывается, когда окно показывается. Единственное действие, которое нам тут нужно -- правильно установить камеру
     */
    override fun show() { camera.setToOrtho(viewport) }

    /**
     * Вызывается, когда пользователь меняет размер окна. Тут должны меняться [uiViewport] и [viewport],
     * чтобы правильно сохранять пропорции. [width], [height] -- новая ширина и высота окна в пикселях.
     */
    override fun resize(width: Int, height: Int) {
        // TODO
    }

    /**
     * По данным координатам узнаёт какого цвета должна быть клетка.
     */
    private fun getCellColor(raw: Int, col: Int): Color {
        // TODO
        return Color.BLACK.cpy()
    }

    /**
     * Рисует данный полигон используя [shapeRenderer].
     */
    private fun drawPolygon(polygon: Polygon, color: Color) {
        // TODO
    }

    /**
     * Эта функция нужна для того, чтобы показать, как работает отрисовка. Рисует квадратную сетку.
     */
    private fun drawCartesianGrid(color: Color, numLines: Int = 10) {
        shapeRenderer.projectionMatrix = camera.projMatrix()
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line)
        shapeRenderer.color = color

        // Draw vertical lines
        for (i in 0..numLines) {
            val x = i - numLines / 2f
            shapeRenderer.line(x, -numLines / 2f, x, numLines / 2f)
        }

        // Draw horizontal lines
        for (i in 0..numLines) {
            val y = i - numLines / 2f
            shapeRenderer.line(-numLines / 2f, y, numLines / 2f, y)
        }

        shapeRenderer.end()
    }

    /**
     * Функция отрисовки текста
     */
    private fun drawTextTopLeft(text: String) {
        val font = BitmapFont()

        batch.projectionMatrix = uiViewport.camera.combined

        batch.begin()

        font.color = Color.YELLOW
        font.draw(batch, text, 10f, uiViewport.worldHeight - 30f)

        batch.end()
    }
}