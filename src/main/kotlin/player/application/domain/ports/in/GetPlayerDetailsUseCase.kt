package com.romina.player.application.domain.ports.`in`

import com.romina.player.application.domain.model.Player

interface GetPlayerDetailsUseCase {
    suspend fun getPlayerDetails(query : PlayerDetailsQuery ) : Player
}