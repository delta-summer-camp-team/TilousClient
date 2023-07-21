package com.delta.network

import com.delta.AppConfig
import com.delta.Tilous
import com.google.gson.Gson
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.websocket.*
import io.ktor.http.*
import io.ktor.websocket.*
import java.net.URI
import kotlin.reflect.KFunction1


/**
 * Главная цель этого класса -- слушать определённый веб-сокет сервер и получать от него состояние игры
 * @property [updateFunction] Функция, которая будет вызываться каждый раз, когда сервер получил состояние игры
 */
class WebSocketListener(
    private val appConfig: AppConfig,
    private val updateFunction: KFunction1<Tilous, Unit>
) {
    private val client = HttpClient(OkHttp) {
        install(WebSockets)
    }

    suspend fun start() {
        val serverUri = URI.create("${appConfig.serverAddress}:${AppConfig.webSocketPort}/game")

        client.ws(
            method = HttpMethod.Get,
            host = serverUri.host,
            port = serverUri.port,
            path = serverUri.path + "?id=${appConfig.playersName}",
        ) {
            try {
                // Connection succeeded
                println("Connected to WebSocket server")

                // Read messages from the server
                for (message in incoming) if (message is Frame.Text) {
                    val text = message.readText()

                    try {
                        val gameState = Gson().fromJson(text, Tilous::class.java) // Пытается привести json к Tilous.
                        updateFunction(gameState) // Обновляет состояние игры
                    } catch (_: Exception) { }
                }
            } finally { }
        }

        client.close()
    }

    fun shutdown() {
        client.close()
    }
}