package com.romina.player.application.ports.`in`

import java.util.UUID

interface CreatePlayerUseCase {
    suspend fun createPlayer(command: CreatePlayerCommand) : UUID;
}