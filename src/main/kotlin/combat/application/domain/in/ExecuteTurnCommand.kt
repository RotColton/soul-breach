package com.romina.combat.application.domain.`in`

import com.romina.combat.application.domain.model.Combat
import java.util.UUID

data class ExecuteTurnCommand(
    val actio : String,
    val activeId : UUID,
    val targetId : UUID,
    val currentCombat : Combat
)
