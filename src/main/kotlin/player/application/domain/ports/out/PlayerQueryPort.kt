package com.romina.player.application.domain.ports.out

import com.romina.player.application.domain.model.Player
import java.util.UUID

interface PlayerQueryPort {
    fun getPlayerDetails(id : UUID) : Player
}