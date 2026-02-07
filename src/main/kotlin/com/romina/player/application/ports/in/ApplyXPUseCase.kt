package com.romina.player.application.ports.`in`

interface ApplyXPUseCase {
    suspend fun applyXP(command: ApplyXPCommand)
}