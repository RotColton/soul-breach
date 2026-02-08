package com.romina.player.application.ports.out

import com.romina.player.application.domain.model.Player
import java.util.UUID

interface PlayerPort {

    suspend fun save(player: Player): UUID

    suspend fun findById(id: UUID): Player

}