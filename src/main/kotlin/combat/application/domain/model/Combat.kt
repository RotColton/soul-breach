package com.romina.combat.application.domain.model

import com.romina.player.application.domain.model.Player
import java.util.UUID
//Todo enums
data class Combat(
    val id : UUID,
    val player1 : Player,
    val player2 : Player,
    val currentTurn : String,
    val combatState : String,
    val winner : String
)
