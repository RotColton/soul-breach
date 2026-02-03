package com.romina.player.infrastructure.driven.adapter.persistence.players

import com.romina.player.application.domain.model.Player
import com.romina.player.application.domain.ports.out.PlayerPort
import com.romina.player.infrastructure.driven.adapter.persistence.mapper.daoToPlayer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.dao.id.IdTable
import org.jetbrains.exposed.v1.dao.Entity
import org.jetbrains.exposed.v1.jdbc.transactions.suspendTransaction
import java.util.UUID

class PlayerPostgresAdapter : PlayerPort {

    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        withContext(Dispatchers.IO) {
            suspendTransaction { block() }
        }

    override suspend fun createPlayer(player : Player): UUID = dbQuery{
        val player = PlayerDAO.new{
            name = player.name
        }
        player.id.value
    }

    override suspend fun getPlayerDetails(id : UUID): Player = dbQuery{
        val playerDAO = PlayerDAO.findById(id)
            ?: throw NoSuchElementException("Could not find player with ID: $id")
        daoToPlayer(playerDAO)
    }

}