package com.romina.player.application.domain.ports.`in`

import java.util.UUID

interface CreatePlayerUseCase {
    suspend fun createPlayer(command: CreatePlayerCommand) : UUID;
}