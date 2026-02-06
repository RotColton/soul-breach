package com.romina.player.infrastructure.driven.adapter.persistence.players

import com.romina.player.application.domain.model.Player
import com.romina.player.application.domain.ports.out.PlayerPort
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.v1.jdbc.transactions.suspendTransaction
import java.util.UUID

class PlayerPostgresAdapter : PlayerPort {

    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        withContext(Dispatchers.IO) {
            suspendTransaction { block() }
        }

    override suspend fun save(player : Player): UUID = dbQuery{
        val player = PlayerDAO.new(player.id){
            name = player.name
        }
        player.id.value
    }

    override suspend fun findById(id : UUID): Player = dbQuery{
        val playerDAO = PlayerDAO.findById(id)
            ?: throw NoSuchElementException("Could not find player with ID: $id")
        playerDAO.toModel()
    }

}