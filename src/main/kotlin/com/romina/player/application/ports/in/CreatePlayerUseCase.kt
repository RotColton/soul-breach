package com.romina.player.application.ports.`in`

import java.util.UUID

interface CreatePlayerUseCase {

    data class Command(
        val playerName: String
    )

    suspend fun createPlayer(command: Command) : UUID;
}