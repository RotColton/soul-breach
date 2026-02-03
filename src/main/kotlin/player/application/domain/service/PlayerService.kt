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

    //TODO: REFACTOR -> call Player constructor
    override suspend fun createPlayer(command: CreatePlayerCommand): UUID {
        val player = Player(
            name = command.playerName
        )
        return playerPort.createPlayer(player)
    }

    override suspend fun getPlayerDetails(query: PlayerDetailsQuery): Player {
        return playerPort.getPlayerDetails(query.playerId)
    }
}