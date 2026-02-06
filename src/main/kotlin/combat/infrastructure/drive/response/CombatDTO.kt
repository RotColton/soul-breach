package com.romina.combat.infrastructure.drive.response

import com.romina.combat.application.domain.model.CombatState
import com.romina.player.infrastructure.drive.response.dto.PlayerDTO
import kotlinx.serialization.Serializable

@Serializable
data class CombatDTO(
    val id : String,
    val player1 : PlayerDTO,
    val player2 : PlayerDTO,
    val turnOrder: List<String>,
    var currentTurn : String,
    var state : CombatState,
    var winner : String?
) {
}
