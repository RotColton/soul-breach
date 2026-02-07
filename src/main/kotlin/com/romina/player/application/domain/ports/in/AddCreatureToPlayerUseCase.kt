package com.romina.player.application.domain.ports.`in`

import com.romina.player.application.domain.model.Player

interface AddCreatureToPlayerUseCase {
    suspend fun addCreature(command: AddCreatureToPlayerCommand) : Player
}