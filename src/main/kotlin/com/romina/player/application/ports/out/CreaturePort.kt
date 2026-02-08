package com.romina.player.application.ports.out

import com.romina.player.application.domain.model.Creature
import com.romina.player.application.domain.model.Player
import java.util.UUID

interface CreaturePort {
    suspend fun save(creature : Creature) : Creature

    suspend fun findById(id : UUID) : Creature

    suspend fun update(creature : Creature) : Creature

    suspend fun delete(id : UUID)
}