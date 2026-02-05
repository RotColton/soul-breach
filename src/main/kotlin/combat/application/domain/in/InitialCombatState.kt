package com.romina.combat.application.domain.`in`

import com.romina.player.application.domain.model.Creature
import java.util.UUID

data class InitialCombatState (
    val combatId : UUID,
    val turns : List<Creature>
)