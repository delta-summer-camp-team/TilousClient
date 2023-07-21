package com.delta

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import com.delta.graphics.GraphicsComponent
import com.delta.network.GameController
import kotlinx.coroutines.*

/**
 * Создаёт все ресурсы, устанавливает соединения, запускает сервер и создаёт окно приложения.
 */
class Application(appConfig: AppConfig) {
    private var gameController: GameController
    private var graphicsComponent: GraphicsComponent

    init {
        val gameState = GameState()
        gameController = GameController(gameState, appConfig)
        graphicsComponent = GraphicsComponent(gameState, gameController)
    }

    /**
     * Запускает всё!
     */
    fun start() {

        // Создаёт отдельный поток для сервера, иначе графическая компонента всё блокирует.
        val thread = Thread {
            runBlocking {
                try {
                    // Запускаем все серверы!
                    gameController.start()
                } catch (e: Exception) {
                    println(e.message)
                }
            }
        }

        thread.start() // Запускаем поток!

        // Запускает приложение (создаёт окошко, которое умеет рисовать что-то) и обрабатывать сигналы от игрока.
        Lwjgl3Application(graphicsComponent, Lwjgl3ApplicationConfiguration().apply
        {
            setTitle("Tilous")
            setWindowedMode(640, 480)
        })

        thread.interrupt() // Произойдет только если окно закроется.
    }
}