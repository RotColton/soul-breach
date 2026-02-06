package com.romina.combat.application.domain.out

import com.romina.combat.application.domain.model.Combat
import java.util.UUID

interface CombatPort {
    suspend fun save(combat : Combat) : UUID
}