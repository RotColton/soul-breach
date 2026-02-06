package com.romina.combat.infrastructure.driven.adapter.persistence.combat

import com.romina.combat.application.domain.model.CombatState
import com.romina.player.infrastructure.driven.adapter.persistence.players.PlayerTable
import org.jetbrains.exposed.v1.core.dao.id.java.UUIDTable


object CombatTable : UUIDTable("combats") {
    val player1 = reference("player1_id", PlayerTable)
    val player2 = reference("player2_id", PlayerTable)
    val turnOrder = text("turn_order")
    val currentTurn = varchar("current_turn", 36)
    val state = enumerationByName("state", 20, CombatState::class)
    val winnerId = varchar("winner", 36).nullable()
}