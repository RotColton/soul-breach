package com.romina.player.application.domain.service

import com.romina.player.application.domain.model.Player
import com.romina.player.application.domain.ports.`in`.CreatePlayerCommand
import com.romina.player.application.domain.ports.`in`.CreatePlayerUseCase
import com.romina.player.application.domain.ports.`in`.GetPlayerDetailsUseCase
import com.romina.player.application.domain.ports.`in`.PlayerDetailsQuery
import com.romina.player.application.domain.ports.out.PlayerPort
import java.util.UUID

class PlayerService(
    private val playerPort: PlayerPort,

    ) : CreatePlayerUseCase, GetPlayerDetailsUseCase{

    override suspend fun createPlayer(command: CreatePlayerCommand): UUID {
        //TODO: separate create logic
        val player = Player(
            name = command.playerName
        )
        return playerPort.save(player)
    }

    override suspend fun getPlayerDetails(query: PlayerDetailsQuery): Player {
        return playerPort.findById(query.playerId)
    }
}