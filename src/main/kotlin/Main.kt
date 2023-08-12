import com.delta.AppConfig
import com.delta.Application

fun main(args: Array<String>) {
    val serverAddress = "http://192.168.195.231"

    val app = Application(AppConfig("Mark", serverAddress))
    app.start()
}