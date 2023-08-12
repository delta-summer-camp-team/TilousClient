package com.delta.network

import com.delta.*
import kotlinx.coroutines.*

/**
 * Контролирует всё, что касается обновления игры.
 */
class GameController(
    private val gameState: GameState = GameState(),
    appConfig: AppConfig = AppConfig()
) {
    private val listener = WebSocketListener(appConfig, this::updateGameState)
    private val httpClient = ApplicationHttpClient(gameState, appConfig)

    suspend fun start() {
        httpClient.tryLogin()
        coroutineScope {
            launch {
                listener.start()
            }
        }
    }

    /**
     * Эта функция будет вызываться пользовательским интерфейсом
     * при попытке поставить клетку.
     * @return `true` если получилось,  `false` если нет
     */
    fun handlePlaceCellUserRequest(row: Int, col: Int): Boolean {
        return httpClient.askToPlaceCell(row, col)
    }

    /**
     * Эта функция будет вызываться пользовательским интерфейсом
     * при попытке завершить ход или автоматически если у игрока не осталось ресурсов.
     * @return `true` если получилось,  `false` если нет
     */
    fun handleFinishTurnRequest(): Boolean {
        return httpClient.askToEndTurn()
    }

    /**
     * Основная функция, которая обновляет состояние игры.
     * Вызывается внутри [listener], когда ему приходит сигнал от сервера.
     */
    private fun updateGameState(game: Tilous) {
        // 1. Update the game state
        this.gameState.game = game

        if (gameState.playerID == null) {
            gameState.playerID = httpClient.tryAskPlayerId()
        }

        // Possible logic error. If the game state is
        // 2. If the game has not started AND the attempt to find out the ID is successful

        // 3. If it's time for the player's turn
        this.gameState.playerID?.let { playerID ->
            println(playerID)
            if (this.gameState.isMyTurn(playerID)) {
                gameState.phase = GamePhase.PLAYER_TURN
            } else if (gameState.isOtherTurn(playerID)) {
                gameState.phase = GamePhase.OPPONENT_TURN
            }
        }

         // 4. If the game has ended
        if (game.gameIsOver) {
            this.gameState.phase = GamePhase.FINISHED
        }

        // 5. If it's the player's turn and they have no resources left
        gameState.playerID?.let { playerID ->
            val playerResources = game.getPlayerResources()[playerID]
            if (gameState.phase == GamePhase.PLAYER_TURN && playerResources == 0) {
                // Automatically request to end the turn
                handleFinishTurnRequest()
            }
        }
        println("Current game state:")
        println(this.gameState.playerID)
        println(this.gameState.phase)
    }


    /**
     * Закрывает все соединения и уничтожается.
     */
    fun shutdown() {
        listener.shutdown()
        httpClient.shutdown()
    }
}

