package com.romina.application.domain.service

import com.romina.application.domain.model.Creature
import com.romina.application.domain.model.CreatureClass
import com.romina.application.domain.model.Player
import com.romina.application.domain.ports.`in`.AddCreatureToPlayerUseCase
import com.romina.application.domain.ports.`in`.CreatePlayerUseCase
import com.romina.infrastructure.driven.rest.routes.player.request.AddCreatureRequest
import com.romina.infrastructure.driven.rest.routes.player.request.CreatePlayerRequest


class PlayerService : CreatePlayerUseCase, AddCreatureToPlayerUseCase {

    override fun createPlayer(command: CreatePlayerRequest): Player {
       // TODO("persistence not yet implemented")
        // TODO: implement choice initial Creature logic
        val player = Player(name = command.playerName)
        player.addCreature(creatureName = "Amund", creatureClass = CreatureClass.DEFENDER)
        return player
    }

    override fun addCreature(command: AddCreatureRequest): Player {
       // TODO("persistence not yet implemented")
        val player = Player(name = "PlayerMockForAddCreature")
        player.addCreature(creatureName = command.creatureName, creatureClass = command.creatureClass)
        return player
    }
}