package com.romina.application.domain.ports.`in`

import com.romina.infrastructure.driver.rest.routes.player.request.CreatePlayerRequest

data class CreatePlayerCommand(
    val playerName: CreatePlayerRequest,
){
}