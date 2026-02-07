package com.romina.player.application.ports.`in`

import com.romina.player.application.domain.model.Player

//TODO: rename use case for more specific business naming
interface GetPlayerDetailsUseCase {
    suspend fun getPlayerDetails(query : PlayerDetailsQuery) : Player
}