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

        val p1DAO = PlayerDAO.findById(combat.player1.id)
            ?: throw NoSuchElementException("Could not find player with ID: $combat.player1.id")
        val p2DAO = PlayerDAO.findById(combat.player2.id)
            ?: throw NoSuchElementException("Could not find player with ID: $combat.player2.id")

        val combatDAO = CombatDAO.new(combat.id) {
            player1 = p1DAO
            player2 = p2DAO
            turnOrderRaw = combat.turnOrder.joinToString(",") { it.toString() }
            currentTurnId = combat.currentTurn.toString()
            state = combat.state
            winner = combat.winner.name
        }
        combatDAO.toModel()
    }

    override suspend fun getById(id: UUID): Combat = dbQuery{
        val combatDAO = CombatDAO.findById(id)
            ?: throw NoSuchElementException("Could not find combat with ID: $id")
        combatDAO.toModel()
    }

    override suspend fun update(combat: Combat): Combat = dbQuery{
        val combatDAO = CombatDAO.findById(combat.id)
            ?: throw NoSuchElementException("Could not find combat with ID: $combat.id")

        val p1DAO = PlayerDAO.findById(combat.player1.id)
            ?: throw NoSuchElementException("Could not find player with ID: $combat.player1.id")
        val p2DAO = PlayerDAO.findById(combat.player2.id)
            ?: throw NoSuchElementException("Could not find player with ID: $combat.player2.id")

        combatDAO.apply {
            player1 = p1DAO
            player2 = p2DAO
            turnOrderRaw = combat.turnOrder.joinToString(",") { it.toString() }
            currentTurnId = combat.currentTurn.toString()
            state = combat.state
            winner = combat.winner.name
        }
        combatDAO.toModel()
    }
}