package com.romina.player.application.ports.`in`

import java.util.UUID

data class PlayerDetailsQuery(
    val playerId : UUID
)