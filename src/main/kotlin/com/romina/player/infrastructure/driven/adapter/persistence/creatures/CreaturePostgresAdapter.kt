package com.romina.player.infrastructure.driven.adapter.persistence.creatures

import com.romina.player.application.domain.model.Creature
import com.romina.player.application.ports.out.CreaturePort
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

    override suspend fun save(creature: Creature): Creature = dbQuery{
        val playerId = creature.owner
        val playerDAO = findPlayer(playerId)

        CreatureDAO.Companion.new(creature.id) {
            name = creature.name
            owner = playerDAO
            creatureClass = creature.creatureClass.name
            level = creature.level
            xp = creature.xp
            hp = creature.attributes.hp
            attack = creature.attributes.attack
            speed = creature.attributes.speed
        }.toModel()
    }

    override suspend fun findById(id: UUID): Creature = dbQuery{
        findCreature(id).toModel()
    }

    override suspend fun update(creature : Creature): Creature = dbQuery{
        val creatureDAO = findCreature(creature.id)

        creatureDAO.apply {
            level = creature.level
            hp = creature.attributes.hp
            attack = creature.attributes.attack
            speed = creature.attributes.speed
        }.toModel()
    }

    override suspend fun delete(id: UUID) = dbQuery{
        findCreature(id).delete()
    }

    private suspend fun findCreature(id : UUID) = dbQuery{
        CreatureDAO.findById(id)
            ?: throw NoSuchElementException("Creature not found: $id")
    }

    private suspend fun findPlayer(id : UUID) = dbQuery {
        PlayerDAO.findById(id)
            ?: throw NoSuchElementException("Could not find player with ID: $id")
    }

}