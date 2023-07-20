package com.delta

data class GameState(
    var playerID: PlayerID? = null,
    var game: Tilous? = null,
    var phase: GamePhase = GamePhase.NOT_STARTED
)