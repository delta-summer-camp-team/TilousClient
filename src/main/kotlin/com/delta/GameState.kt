package com.delta

data class GameState(
    var playerID: PlayerID? = null,
    var game: Tilous? = null,
    var phase: GamePhase = GamePhase.NOT_STARTED
) {
    private fun gameStarted(): Boolean {
        return game != null && phase != GamePhase.NOT_STARTED
    }

    fun isMyTurn(myPlayerID: PlayerID): Boolean {
        return gameStarted() && playerID == myPlayerID && !game!!.gameIsOver && game!!.currentPlayer == myPlayerID
    }

    fun isOtherTurn(myPlayerID: PlayerID): Boolean {
        return gameStarted() && playerID != myPlayerID && !game!!.gameIsOver && game!!.currentPlayer != myPlayerID
    }

    fun isNotStarted(): Boolean {
        return phase == GamePhase.NOT_STARTED
    }
}
