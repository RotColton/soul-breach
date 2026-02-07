package com.romina.player.application.domain.ports.`in`

interface ApplyXPUseCase {
    suspend fun applyXP(command: ApplyXPCommand)
}