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
        if (gameState.phase == GamePhase.PLAYER_TURN) {
            // Check if the cell is placeable
            return httpClient.askToPlaceCell(row, col)
        } else {
            println("It's not your turn.")
            return false
        }
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
    private fun updateGameState(gameState: Tilous) {

        // 1. Update the game state
        this.gameState.game = gameState
        // Possible logic error. If the game state is
        // 2. If the game has not started AND the attempt to find out the ID is successful
        if (this.gameState.phase == GamePhase.NOT_STARTED && this.gameState.playerID != null) {
            this.gameState.phase = GamePhase.OPPONENT_TURN
        }

        // 3. If it's time for the player's turn
        if (this.gameState.isMyTurn(this.gameState.playerID!!)) {
            this.gameState.phase = GamePhase.PLAYER_TURN
        } else if (this.gameState.isOtherTurn(this.gameState.playerID!!)) {
            this.gameState.phase = GamePhase.OPPONENT_TURN
        }

        // 4. If the game has ended
        if (gameState.gameIsOver) {
            this.gameState.phase = GamePhase.FINISHED
        }

        // 5. If it's the player's turn and they have no resources left
        val playerResources = gameState.getPlayerResources()[this.gameState.playerID!!]
        if (this.gameState.phase == GamePhase.PLAYER_TURN && playerResources != null && playerResources == 0) {
            // Automatically request to end the turn
            handleFinishTurnRequest()
        }
    }


    /**
     * Закрывает все соединения и уничтожается.
     */
    fun shutdown() {
        listener.shutdown()
        httpClient.shutdown()
    }
}

