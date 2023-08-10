package com.delta.graphics

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.delta.*
import com.delta.graphics.controllers.CameraController
import com.delta.network.GameController
import ktx.app.KtxGame
import ktx.app.KtxScreen

class GraphicsComponent(
    private val gameState: GameState,
    private val gameController: GameController
) : KtxGame<KtxScreen>() {

    /**
     * Вызывается автоматически когда игра создаётся.
     */
    override fun create() {
        val screen = Screen(gameState)

        addScreen(screen)
        setScreen<Screen>()

       /* *
        * TODO
        * Для того чтобы действия игрока обрабатывались, нужно создать контроллер, который будет
        * принимать сигналы от приложения (такие как нажатие мыши).
        * */
        val cameraController = CameraController(screen.camera)


        val inputMultiplexer = InputMultiplexer()
        inputMultiplexer.addProcessor(cameraController)

        /* *
         * TODO
         * После того, как вы создали нужный контроллер, вам нужно его добавить в InputMultiplexer
         * чтобы потом зарегистрировать вместе с inputProcessor. Попробуйте найти подходящий метод для этого.
         * */

        Gdx.input.inputProcessor = inputMultiplexer
    }


    override fun dispose() {
        gameController.shutdown()
        super.dispose()
    }
}