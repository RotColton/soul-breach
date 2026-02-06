package com.romina.combat.infrastructure.drive.response

import com.romina.combat.application.domain.model.Combat
import com.romina.combat.application.domain.model.CombatState
import com.romina.player.infrastructure.drive.response.dto.CreatureDTO
import com.romina.player.infrastructure.drive.response.dto.toDTO
import kotlinx.serialization.Serializable

@Serializable
data class StateCombatResponse(
    val combatID: String,
    val turnOrder: List<String>,
    val state: CombatState,
    val currentTurn: String,
    val creaturesP1: MutableList<CreatureDTO>,
    val creaturesP2: MutableList<CreatureDTO>,
)

fun Combat.toResponse() = StateCombatResponse(
    combatID = id.toString(),
    turnOrder = turnOrder.map { it.toString() },
    state = state,
    currentTurn = currentTurn.toString(),
    creaturesP1 = player1.creatures.map{ it.toDTO() } as MutableList<CreatureDTO>,
    creaturesP2 = player2.creatures.map{ it.toDTO() } as MutableList<CreatureDTO>
)