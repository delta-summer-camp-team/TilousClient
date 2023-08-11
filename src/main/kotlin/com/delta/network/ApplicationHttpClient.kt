package com.delta.network

import com.delta.AppConfig
import com.delta.GamePhase
import com.delta.GameState
import com.delta.PlayerID
import com.google.gson.Gson
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.client.utils.EmptyContent.contentType
import io.ktor.http.*
import io.ktor.serialization.gson.*
import io.ktor.util.*
import kotlinx.coroutines.runBlocking

/**
 * Этот класс осуществляет все запросы к серверу.
 * Все его методы возвращают true если действие было удачным и false если нет
 * @property [client] Делает всю работу. См. [tryLogin] чтобы узнать типичное использование
 *
 */
class ApplicationHttpClient(
    private val gameState: GameState, private val appConfig: AppConfig = AppConfig()
) {
    private val fullServerAddress = "${appConfig.serverAddress}:${AppConfig.httpPort}"

    private var player: Player? = null

    private val client = HttpClient(OkHttp) {
        install(ContentNegotiation) { gson() }
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
        }
    }

    /**
     * Запрос зарегистрировать игрока
     * @return `true` если удачно, `false` если нет
     */
    fun tryLogin(): Boolean {
        /*
        * runBlocking используется для того, чтобы убрать здесь асинхронность.
        * Если её убрать, результат может вернуться раньше, чем сервер ответит.
        * Потенциальная ошибка здесь -- что если сервер не отвечает?
        * Тогда мы вечно будем ждать ответа на запрос
        * */
        return runBlocking {
            /*
            * client.post делает post запрос к серверу
            * url задаёт точку входа на сервер.
            * parameter позволяет установить параметры запроса и их значения.
            */
            val response: HttpResponse = client.get {
                url("$fullServerAddress/login")
                parameter("id", appConfig.playersName)
                parameter("server_pwd", "Delta!!!")
            }

            if (response.status.isSuccess()) {
                /*
                * Мы договорились, что ответ на этот запрос будет структура игрока -- Player.
                * Запрос всегда возвращается в виде строки (в данном случае в формате json),
                * Поэтому мы должны декодировать его обратно в класс
                */
                player = Gson().fromJson(response.body<String>(), Player::class.java)

                return@runBlocking true
            } else {
                println(response.body<String>())
                return@runBlocking false
            }
        }
    }

    fun tryLogout(): Boolean {
        // TODO()
        return false
        // Всё делается по образу и подобию tryLogin
    }


    suspend fun tryAskPlayerId(): PlayerID? {
        try {
            val response: HttpResponse = client.get{
                url("$fullServerAddress/playerId")
                parameter("id", player?.id)
                parameter("pwd", player?.pwd)
            }

            return if (response.status == HttpStatusCode.OK) {
                val playerId: PlayerID? = Gson().fromJson(response.body<String>(), PlayerID::class.java)
                playerId
            } else {
                println("Server returned an error: ${response.status}")
                null
            }
        } catch (e: Exception) {
            println("Error occurred while trying to ask for PlayerID: ${e.message}")
            return null
        }
    }

    fun askToPlaceCell(row: Int, col: Int): Boolean {
        return runBlocking {
            val response: HttpResponse = client.get("$fullServerAddress/placeCell") {
                parameter(key = "PlayerID", value = player!!.id)
                parameter(key = "pwd", value = player!!.pwd)
                parameter(key = "row", value = row.toString())
                parameter(key = "col", value = col.toString())
            }

            return@runBlocking response.status == HttpStatusCode.OK
        }
    }

    fun askToEndTurn(): Boolean {
        return runBlocking {
            val response: HttpResponse = client.get {
                url("$fullServerAddress/endPlayersTurn")
                parameter("id", player?.id)
                parameter("pwd", player?.pwd)
            }

            if (response.status.isSuccess()) {
                return@runBlocking true
            } else {
                println(response.body<String>())
                return@runBlocking false
            }
        }
    }

    fun shutdown() {
        try {
            client.use { print("Result of try logout: ${tryLogout()}") }
        } catch (e: Exception) {
            println("EXCEPTION:\n${e.message}")
        }
    }
}
