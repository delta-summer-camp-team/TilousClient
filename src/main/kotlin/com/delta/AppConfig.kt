package com.delta

class AppConfig(
    val playersName: String = "User", // Must be configurable!
    val serverAddress: String = "http://123.123.123.213", // Change this!
) {
    companion object {
        const val httpPort: String = "8080"
        const val webSocketPort: String = "8081"
    }
}