package com.romina.combat.infrastructure.driven.adapter.persistence.combat

import com.romina.combat.application.domain.model.Combat
import com.romina.player.infrastructure.driven.adapter.persistence.players.PlayerDAO
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.java.UUIDEntity
import org.jetbrains.exposed.v1.dao.java.UUIDEntityClass
import java.util.UUID

class CombatDAO (id : EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<CombatDAO>(CombatTable)
    var player1 by PlayerDAO referencedOn CombatTable.player1Id
    var player2 by PlayerDAO referencedOn CombatTable.player2Id
    var turnOrderRaw by CombatTable.turnOrder
    var currentTurnId by CombatTable.currentTurn
    var state by CombatTable.state
    var winnerId by CombatTable.winnerId

    fun toModel() = Combat(
        id = this.id.value,
        player1 = this.player1.toModel(),
        player2 = this.player2.toModel(),
        turnOrder = turnOrderRawToList(this.turnOrderRaw) as MutableList<UUID>,
        currentTurn = UUID.fromString(this.currentTurnId),
        state = this.state,
        winner = this.winnerId,
        )

    private fun turnOrderRawToList(data: String): List<UUID> {
        return if (data.isBlank()) {
            emptyList()
        } else {
            data.split(",")
                .map { UUID.fromString(it.trim()) }
        }
    }
}
