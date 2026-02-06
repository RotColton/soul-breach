package com.romina.player.infrastructure.driven.adapter.persistence.creatures

import com.romina.player.application.domain.model.Creature
import com.romina.player.application.domain.model.Player
import com.romina.player.application.domain.ports.out.CreaturePort
import com.romina.player.infrastructure.driven.adapter.persistence.players.PlayerDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.v1.jdbc.transactions.suspendTransaction
import java.util.UUID

class CreaturePostgresAdapter : CreaturePort{
    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        withContext(Dispatchers.IO) {
            suspendTransaction { block() }
        }

    override suspend fun save(creature: Creature): Player = dbQuery{
        val playerId = creature.owner
        val playerDAO = PlayerDAO.findById(playerId)
            ?: throw NoSuchElementException("Could not find player with ID: $playerId")

        CreatureDAO.new(creature.id) {
            name = creature.name
            owner = playerDAO
            creatureClass = creature.creatureClass.name
            level = creature.level
            xp = creature.xp
            hp = creature.attributes.hp
            attack = creature.attributes.attack
            speed = creature.attributes.speed
        }
        //TODO: doble consulta?
        val playerDAOUpdated = PlayerDAO.findById(playerId)
            ?: throw NoSuchElementException("Could not find player with ID: $playerId")

        playerDAOUpdated.toModel()
    }

    override suspend fun findById(creatureId: UUID): Creature = dbQuery{
        val creatureDAO = CreatureDAO.findById(creatureId)
            ?: throw NoSuchElementException("Creature not found: $creatureId")
        creatureDAO.toModel()
    }

    override suspend fun update(creature : Creature): Creature = dbQuery{
        val creatureDAO = CreatureDAO.findById(creature.id)
            ?: throw NoSuchElementException("Creature not found: $creature.id")

        creatureDAO.apply {
            level = creature.level
            hp = creature.attributes.hp
            attack = creature.attributes.attack
            speed = creature.attributes.speed
        }
        creatureDAO.toModel()
    }

}