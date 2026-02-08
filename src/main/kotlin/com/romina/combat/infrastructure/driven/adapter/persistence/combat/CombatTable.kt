package com.romina.combat.infrastructure.driven.adapter.persistence.combat

import com.romina.combat.application.domain.model.CombatState
import com.romina.player.infrastructure.driven.adapter.persistence.players.PlayerTable
import org.jetbrains.exposed.v1.core.dao.id.java.UUIDTable

object CombatTable : UUIDTable("combats") {

    val playerId = reference("player_id", PlayerTable)
    val enemyId = reference("enemy_id", PlayerTable)
    val turnOrder = text("turn_order")
    val currentTurn = varchar("current_turn", 36)
    val state = enumerationByName("state", 20, CombatState::class)
    val winner = varchar("winner", 36)
}