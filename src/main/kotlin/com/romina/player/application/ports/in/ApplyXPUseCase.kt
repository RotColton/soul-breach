package com.romina.player.application.ports.`in`

import java.util.UUID

interface ApplyXPUseCase {

    data class Command(
        val playerId : UUID,
        val creatureId : UUID,
        val xpGained : Int
    ){
        init{
            require(xpGained > 0){ "XP Gained must be greater than zero" }
        }
    }

    suspend fun applyXP(command: Command)
}