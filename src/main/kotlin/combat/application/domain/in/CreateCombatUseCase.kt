package com.romina.combat.application.domain.`in`

interface CreateCombatUseCase {
    suspend fun createCombat(createCombatCommand : CreateCombatCommand) : InitialCombatState
}