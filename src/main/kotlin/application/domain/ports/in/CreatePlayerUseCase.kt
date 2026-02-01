package com.romina.application.domain.ports.`in`

import com.romina.application.domain.model.Player
import com.romina.infrastructure.driver.rest.routes.player.request.CreatePlayerRequest

interface CreatePlayerUseCase {
    fun createPlayer(command: CreatePlayerRequest) : Player;
}