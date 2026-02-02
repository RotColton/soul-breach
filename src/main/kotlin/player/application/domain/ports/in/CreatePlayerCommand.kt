package com.romina.player.application.domain.ports.`in`

import com.romina.player.infrastructure.adapters.driver.rest.routes.player.request.CreatePlayerRequest

data class CreatePlayerCommand(
    val playerName: String
)