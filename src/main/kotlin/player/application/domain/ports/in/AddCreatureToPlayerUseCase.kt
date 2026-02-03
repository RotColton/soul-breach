package com.romina.player.application.domain.ports.`in`

import com.romina.player.application.domain.model.Player
import com.romina.player.infrastructure.drive.request.AddCreatureRequest

interface AddCreatureToPlayerUseCase {
    suspend fun addCreature(command: AddCreatureToPlayerCommand) : Player
}