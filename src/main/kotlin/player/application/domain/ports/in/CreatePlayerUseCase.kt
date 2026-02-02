package com.romina.player.application.domain.ports.`in`

import com.romina.player.application.domain.model.Player
import com.romina.player.infrastructure.adapters.driver.rest.routes.player.request.CreatePlayerRequest

interface CreatePlayerUseCase {
    fun createPlayer(command: CreatePlayerRequest) : Player;
}