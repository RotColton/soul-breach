package com.romina.player.application.domain.ports.out

import com.romina.player.application.domain.model.Creature
import com.romina.player.application.domain.model.Player
import java.util.UUID

interface PlayerCommandPort {
    fun createPlayer(playerName : String): UUID
    fun addCreature(creature : Creature, playerId : UUID) : Player
}