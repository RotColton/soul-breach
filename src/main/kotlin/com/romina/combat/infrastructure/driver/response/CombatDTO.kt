package com.romina.combat.infrastructure.driver.response

import com.romina.combat.application.domain.model.CombatState
import com.romina.player.infrastructure.driver.response.dto.PlayerDTO
import kotlinx.serialization.Serializable

@Serializable
data class CombatDTO(

    val id : String,
    val player : PlayerDTO,
    val enemy : PlayerDTO,
    val turnOrder: List<String>,
    var currentTurn : String,
    var state : CombatState,
    var winner : String?
) {
}
