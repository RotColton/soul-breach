package com.romina.player.application.domain.service

import com.romina.player.application.domain.model.CreatureClassFactory
import com.romina.player.application.domain.model.Defender
import com.romina.player.application.domain.model.Player
import com.romina.player.application.domain.ports.`in`.AddCreatureToPlayerUseCase
import com.romina.player.application.domain.ports.`in`.CreatePlayerUseCase
import com.romina.player.application.domain.ports.`in`.GetPlayerDetailsUseCase
import com.romina.player.application.domain.ports.`in`.PlayerDetailsQuery
import com.romina.player.infrastructure.adapters.driver.rest.routes.player.request.AddCreatureRequest
import com.romina.player.infrastructure.adapters.driver.rest.routes.player.request.CreatePlayerRequest


class PlayerService : CreatePlayerUseCase, AddCreatureToPlayerUseCase, GetPlayerDetailsUseCase{

    override fun createPlayer(command: CreatePlayerRequest): Player {
       // TODO("persistence not yet implemented")
        // TODO: implement choice initial Creature logic
        val player = Player(name = command.playerName)
        player.addCreature(creatureName = "Amund", creatureClass = Defender)
        return player
    }

    override fun addCreature(command: AddCreatureRequest): Player {
       // TODO("persistence not yet implemented")
        val player = Player(name = "PlayerMockForAddCreature")
        player.addCreature(creatureName = command.creatureName,
            creatureClass = CreatureClassFactory.fromString(command.creatureClass))
        return player
    }

    override fun getPlayerDetails(query: PlayerDetailsQuery): Player {
        TODO("Not yet implemented")
    }
}