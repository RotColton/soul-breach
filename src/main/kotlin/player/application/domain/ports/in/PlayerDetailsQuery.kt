package com.romina.player.application.domain.ports.`in`

import java.util.UUID

data class PlayerDetailsQuery(
    val playerId : UUID
)