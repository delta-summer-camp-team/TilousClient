import com.delta.AppConfig
import com.delta.Application

fun main(args: Array<String>) {
    val serverAddress = "http://192.168.199.121"
    //val playersName = args[0]

    val app = Application(AppConfig("D", serverAddress))
    app.start()
}