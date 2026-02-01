package com.romina.application.domain.ports.`in`

import com.romina.application.domain.model.Player
import com.romina.infrastructure.driver.rest.routes.player.request.AddCreatureRequest

interface AddCreatureToPlayerUseCase {
    fun addCreature(command: AddCreatureRequest) : Player
}