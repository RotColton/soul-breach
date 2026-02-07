package com.romina.combat.application.ports.out

import com.romina.combat.application.domain.model.Combat
import java.util.UUID

interface CombatPort {
    suspend fun save(combat : Combat) : Combat
    suspend fun getById(id : UUID) : Combat
    suspend fun update(combat : Combat) : Combat
}