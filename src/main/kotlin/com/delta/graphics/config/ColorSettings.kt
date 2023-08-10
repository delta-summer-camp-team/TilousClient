package com.delta.graphics.config

import com.badlogic.gdx.graphics.Color
import com.delta.PlayerID

object ColorSettings {
    // Может быть другие цвета какие-нибудь?

    private val Player1Color = Color(0.94f, 0.33f, 0.31f, 1f)
    private val Player2Color = Color(0.29f, 0.73f, 0.47f, 1f)
    private val Player3Color = Color(0.73f, 0.52f, 0.98f, 1.0f)
    private val Player4Color = Color(0.96f, 0.68f, 0.18f, 1f)

    val EmptyCellColor = Color(0.2f, 0.2f, 0.2f, 1.0f)
    val BackgroundColor = Color(0.1f, 0.1f, 0.1f, 1.0f)

    val colorMap : Map<PlayerID?, Color> = mapOf(PlayerID.PLAYER_1 to Player1Color, PlayerID.PLAYER_2 to Player2Color,PlayerID.PLAYER_3 to Player3Color,PlayerID.PLAYER_4 to Player4Color, null to EmptyCellColor) //TODO

}
