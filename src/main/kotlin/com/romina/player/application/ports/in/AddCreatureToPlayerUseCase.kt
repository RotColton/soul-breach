package com.romina.player.application.ports.`in`

import com.romina.player.application.domain.model.Player
import java.util.UUID

interface AddCreatureToPlayerUseCase {

    data class Command(
        val playerId : UUID,
        val creatureName: String,
        val creatureClass: String
    )

    suspend fun addCreature(command: Command) : Player
}