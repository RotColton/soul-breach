package com.romina.player.infrastructure.driven.adapter.persistence

import com.romina.player.application.domain.model.Creature
import com.romina.player.application.domain.model.Player
import com.romina.player.application.domain.ports.out.PlayerCommandPort
import com.romina.player.application.domain.ports.out.PlayerQueryPort
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import java.util.UUID

class PlayerPostgresAdapter : PlayerCommandPort, PlayerQueryPort {


    override fun createPlayer(playerName : String): UUID {
       val id = transaction {
            val player = PlayerEntity.new{
                name = playerName
            }
           player.id.value
        }

        return id
    }

    override fun addCreature(creature : Creature, playerId: UUID): Player {
        TODO("Not yet implemented")
    }

    override fun getPlayerDetails(id : UUID): Player {
        TODO("Not yet implemented")
    }
}