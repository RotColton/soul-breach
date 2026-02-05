package com.romina.player.application.domain.ports.out

import com.romina.player.application.domain.model.Creature
import com.romina.player.application.domain.model.Player
import java.util.UUID

//TODO: refactor -> implement CQRS pattern at the port level
// refactor naming ports
interface CreaturePort {
    suspend fun save(creature : Creature) : Player

    suspend fun findById(creatureId : UUID) : Creature

    suspend fun update(creature : Creature) : Creature
}