package com.romina.combat.infrastructure.driven.adapter.persistence.combat

import com.romina.combat.application.domain.model.Combat
import com.romina.combat.application.domain.model.Winner
import com.romina.player.infrastructure.driven.adapter.persistence.players.PlayerDAO
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.java.UUIDEntity
import org.jetbrains.exposed.v1.dao.java.UUIDEntityClass
import java.util.UUID

class CombatDAO (id : EntityID<UUID>) : UUIDEntity(id) {

    companion object : UUIDEntityClass<CombatDAO>(CombatTable)
    var player by PlayerDAO referencedOn CombatTable.playerId
    var enemy by PlayerDAO referencedOn CombatTable.enemyId
    var turnOrderRaw by CombatTable.turnOrder
    var currentTurnId by CombatTable.currentTurn
    var state by CombatTable.state
    var winner by CombatTable.winner

    fun toModel() = Combat(
        id = this.id.value,
        player = this.player.toModel(),
        enemy = this.enemy.toModel(),
        turnOrder = turnOrderRawToList(this.turnOrderRaw) as MutableList<UUID>,
        currentTurn = UUID.fromString(this.currentTurnId),
        state = this.state,
        winner = Winner.valueOf(winner)
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
