package com.romina.player.application.service

import com.romina.player.application.domain.model.Player
import com.romina.player.application.ports.`in`.CreatePlayerUseCase
import com.romina.player.application.ports.`in`.GetPlayerDetailsUseCase
import com.romina.player.application.ports.out.PlayerPort
import java.util.UUID

class PlayerService(
    private val playerPort: PlayerPort,

    ) : CreatePlayerUseCase, GetPlayerDetailsUseCase{

    override suspend fun createPlayer(command: CreatePlayerUseCase.Command): UUID {
        val player = Player(
            name = command.playerName
        )
        return playerPort.save(player)
    }

    override suspend fun getPlayerDetails(query: GetPlayerDetailsUseCase.Query): Player {
        return playerPort.findById(query.playerId)
    }
}