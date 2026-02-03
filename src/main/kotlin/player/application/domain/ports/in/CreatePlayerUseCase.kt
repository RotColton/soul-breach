package com.romina.player.application.domain.ports.`in`

import com.romina.player.infrastructure.drive.request.CreatePlayerRequest
import java.util.UUID

interface CreatePlayerUseCase {
    suspend fun createPlayer(command: CreatePlayerCommand) : UUID;
}