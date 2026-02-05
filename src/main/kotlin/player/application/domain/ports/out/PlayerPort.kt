package com.romina.player.application.domain.ports.out

import com.romina.player.application.domain.model.Player
import java.util.UUID
//TODO: refactor naming ports
// refactor -> implement CQRS pattern at the port level

interface PlayerPort {
    suspend fun save(player: Player): UUID
    suspend fun findById(id: UUID): Player
}