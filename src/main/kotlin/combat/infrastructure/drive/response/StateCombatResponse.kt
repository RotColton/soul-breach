package com.romina.combat.infrastructure.drive.response

import com.romina.combat.application.domain.model.Combat
import com.romina.combat.application.domain.model.CombatState
import com.romina.player.infrastructure.drive.response.dto.CreatureDTO
import com.romina.player.infrastructure.drive.response.dto.toDTO
import kotlinx.serialization.Serializable


@Serializable
data class StateCombatResponse(
    val type : String,
    val combatId : String,
    val creatures : List<CreatureDTO>,
    val currentTurnCreatureId : String,
    val status : CombatState,
    val winner : String?
) {
}

fun Combat.toResponse(type : String) : StateCombatResponse = StateCombatResponse(
    type = type,
    combatId = id.toString(),
    creatures = allCreatures().map{ it.toDTO() },
    currentTurnCreatureId = currentTurn.toString(),
    state,
    winner = winner
)