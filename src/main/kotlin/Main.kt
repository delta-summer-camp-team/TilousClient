import com.delta.AppConfig
import com.delta.Application

fun main() {
    val serverAddress = "http://192.168.178.48"


    val app = Application(AppConfig("A", serverAddress))
    app.start()
}

