import com.delta.AppConfig
import com.delta.Application

fun main(args: Array<String>) {
    val serverAddress = "http://127.0.1.1"

    val app = Application(AppConfig("D", serverAddress))
    app.start()
}
