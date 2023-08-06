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
        if (gameState.phase == GamePhase.PLAYING_MYTURN) {
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
        TODO()
    }

    /**
     * Основная функция, которая обновляет состояние игры.
     * Вызывается внутри [listener], когда ему приходит сигнал от сервера.
     */
    private fun updateGameState(gameState: Tilous) {
        // TODO()
        // 1. Обновите состояние игры (this.gameState.game)

        // 2. Если игра еще не началась И попытка узнать свой id успешна
        //      -- поменять фазу на ожидание остальных игроков

        // 3. Если пришло время своего хода -- поменять фазу на MY_TURN

        // 4. Если игра закончилась -- на FINISHED

        // 5. Если фаза -- мой ход И у меня не осталось ресурсов -- автоматически
        // попросить завершить ход
    }

    /**
     * Закрывает все соединения и уничтожается.
     */
    fun shutdown() {
        listener.shutdown()
        httpClient.shutdown()
    }
}

