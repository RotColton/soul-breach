package com.romina.player.infrastructure.driven.adapter.persistence.creatures

import com.romina.player.application.domain.model.Creature
import com.romina.player.application.domain.model.Player
import com.romina.player.application.domain.ports.out.CreatureCommandPort
import com.romina.player.infrastructure.driven.adapter.persistence.mapper.daoToPlayer
import com.romina.player.infrastructure.driven.adapter.persistence.players.PlayerDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.v1.jdbc.transactions.suspendTransaction
import java.util.UUID

class CreaturePostgresAdapter : CreatureCommandPort{

    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        withContext(Dispatchers.IO) {
            suspendTransaction { block() }
        }

    override suspend fun createCreatureWithOwner(
        creature: Creature,
        playerId: UUID
    ): Player = dbQuery{

        val playerDAO = PlayerDAO.findById(playerId)
            ?: throw NoSuchElementException("Could not find player with ID: $playerId")

        val creatureDao = CreatureDAO.new {
            name = creature.name
            owner = playerDAO
            creatureClass = creature.creatureClass.name
            level = creature.level
            xp = creature.xp
            hp = creature.attributes.hp
            attack = creature.attributes.attack
            speed = creature.attributes.speed
        }
        val playerDAOUpdated = PlayerDAO.findById(playerId)
            ?: throw NoSuchElementException("Could not find player with ID: $playerId")

        daoToPlayer(playerDAOUpdated) //TODO DTO
    }

}