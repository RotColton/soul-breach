package com.romina.application.domain.ports.`in`

import com.romina.infrastructure.driven.rest.routes.player.request.CreatePlayerRequest

data class CreatePlayerCommand(
    val playerName: CreatePlayerRequest,
){
}