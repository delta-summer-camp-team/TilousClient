package com.delta

data class GameState(
    var playerID: PlayerID? = null,
    var game: Tilous? = null,
    var phase: GamePhase = GamePhase.NOT_STARTED
) {
    // Может быть вам захочется написать здесь несколько методов,
    // которые сделают вашу жизнь удобнее.

//    fun gameStarted() = ...
//    fun isMyTurn() = ...
//    fun isOtherTurn() = ...
//    fun isNotStarted() = ...
}