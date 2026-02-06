package com.romina.combat.application.domain.`in`

import com.romina.combat.application.domain.model.Combat

interface CombatDetailsUseCase {
   suspend fun getCombat(command : CombatDetailsCommand) : Combat
}