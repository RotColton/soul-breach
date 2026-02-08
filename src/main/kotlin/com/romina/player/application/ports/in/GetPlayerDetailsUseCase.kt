package com.romina.player.application.ports.`in`

import com.romina.player.application.domain.model.Player
import java.util.UUID

interface GetPlayerDetailsUseCase {

    data class Query(
        val playerId : UUID
    )

    suspend fun getPlayerDetails(query : Query) : Player
}