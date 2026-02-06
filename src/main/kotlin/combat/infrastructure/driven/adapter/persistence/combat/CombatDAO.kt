package com.romina.combat.infrastructure.driven.adapter.persistence.combat

import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.java.UUIDEntity
import org.jetbrains.exposed.v1.dao.java.UUIDEntityClass
import java.util.UUID

class CombatDAO (id : EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<CombatDAO>(CombatTable)
    var player1 by CombatTable.player1
    var player2 by CombatTable.player2
    var turnOrderRaw by CombatTable.turnOrder
    var currentTurnId by CombatTable.currentTurn
    var state by CombatTable.state
    var winnerId by CombatTable.winnerId
}