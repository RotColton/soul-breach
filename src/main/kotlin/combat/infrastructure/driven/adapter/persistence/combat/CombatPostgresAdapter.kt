package com.romina.combat.infrastructure.driven.adapter.persistence.combat

import com.romina.combat.application.domain.model.Combat
import com.romina.combat.application.domain.out.CombatPort
import com.romina.player.infrastructure.driven.adapter.persistence.players.PlayerTable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.jdbc.transactions.suspendTransaction
import java.util.UUID

class CombatPostgresAdapter : CombatPort {
    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        withContext(Dispatchers.IO) {
            suspendTransaction { block() }
        }

    override suspend fun save(combat: Combat): UUID = dbQuery{
        val combatDAO = CombatDAO.new(combat.id) {
            player1 = EntityID(combat.player1.id, PlayerTable)
            player2 = EntityID(combat.player2.id, PlayerTable)
            turnOrderRaw = combat.turnOrder.joinToString(",") { it.toString() }
            currentTurnId = combat.currentTurn.toString()
            state = combat.state
            winnerId = combat.winner
        }
        combatDAO.id.value
    }
}