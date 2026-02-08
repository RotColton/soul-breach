package com.romina.combat.infrastructure.driven.adapter.persistence.combat

import com.romina.combat.application.domain.model.Combat
import com.romina.combat.application.ports.out.CombatPort
import com.romina.player.infrastructure.driven.adapter.persistence.players.PlayerDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.v1.jdbc.transactions.suspendTransaction
import java.util.UUID

class CombatPostgresAdapter : CombatPort {
    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        withContext(Dispatchers.IO) {
            suspendTransaction { block() }
        }

    override suspend fun save(combat: Combat): Combat = dbQuery{
        val p1DAO = findPlayer(combat.player.id)
        val p2DAO = findPlayer(combat.enemy.id)

        CombatDAO.new(combat.id) {
            player = p1DAO
            enemy = p2DAO
            turnOrderRaw = combat.turnOrder.joinToString(",") { it.toString() }
            currentTurnId = combat.currentTurn.toString()
            state = combat.state
            winner = combat.winner.name
        }.toModel()
    }

    override suspend fun getById(id: UUID): Combat = dbQuery{
        findCombat(id).toModel()
    }

    override suspend fun update(combat: Combat): Combat = dbQuery{
        val combatDAO = findCombat(combat.id)
        val playerDAO = findPlayer(combat.player.id)
        val enemy = findPlayer(combat.enemy.id)

        combatDAO.apply {
            player = playerDAO
            this.enemy = enemy
            turnOrderRaw = combat.turnOrder.joinToString(",") { it.toString() }
            currentTurnId = combat.currentTurn.toString()
            state = combat.state
            winner = combat.winner.name
        }.toModel()
    }

    private suspend fun findPlayer(id : UUID) : PlayerDAO = dbQuery{
        PlayerDAO.findById(id)
            ?: throw NoSuchElementException("Could not find player with ID: $id")
    }
    private suspend fun findCombat(id : UUID) : CombatDAO = dbQuery {
        CombatDAO.findById(id)
            ?: throw NoSuchElementException("Could not find combat with ID: $id")
    }

}