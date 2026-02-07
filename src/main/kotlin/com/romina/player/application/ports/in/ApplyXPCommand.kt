package com.romina.player.application.ports.`in`

import java.util.UUID
data class ApplyXPCommand(
    val playerId : UUID,
    val creatureId : UUID,
    val xpGained : Int
){
    init{
        require(xpGained > 0){ "XP Gained must be greater than zero" }
    }
}