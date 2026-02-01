package com.romina.application.domain.ports.`in`

import com.romina.application.domain.model.Player
import com.romina.infrastructure.driven.rest.routes.player.request.CreatePlayerRequest

interface CreatePlayerUseCase {
    fun createPlayer(command: CreatePlayerRequest) : Player;
}