package com.delta.graphics

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.delta.*
import com.delta.graphics.controllers.CameraController
import com.delta.graphics.controllers.BoardActionsController
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

        val cameraController = CameraController(screen.camera)
        val boardActionsController = BoardActionsController(
            screen::cells,
            screen.camera::screenToWorld2D,
            gameController::handlePlaceCellUserRequest,
            gameController::handleFinishTurnRequest
        )

        val inputMultiplexer = InputMultiplexer()
        inputMultiplexer.addProcessor(boardActionsController)
        inputMultiplexer.addProcessor(cameraController)

        Gdx.input.inputProcessor = inputMultiplexer
    }


    override fun dispose() {
        gameController.shutdown()
        super.dispose()
    }
}