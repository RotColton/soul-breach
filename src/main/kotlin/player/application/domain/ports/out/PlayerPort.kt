package com.romina.player.application.domain.ports.out

import com.romina.player.application.domain.model.Player
import java.util.UUID

interface PlayerPort {
    suspend fun createPlayer(player: Player): UUID
    suspend fun getPlayerDetails(id: UUID): Player
}