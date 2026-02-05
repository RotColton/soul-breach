package com.romina.combat.application.domain.service

import com.romina.combat.application.domain.`in`.CreateCombatCommand
import com.romina.combat.application.domain.`in`.CreateCombatUseCase
import com.romina.combat.application.domain.`in`.InitialCombatState
import com.romina.combat.application.domain.model.Combat
import com.romina.player.application.domain.model.Player
import com.romina.player.application.domain.ports.out.PlayerPort
import java.util.UUID

//TODO rename "DefaultCombatService"
class CombatService(
    private val playerPort : PlayerPort

) : CreateCombatUseCase {
    override suspend fun createCombat(createCombatCommand: CreateCombatCommand): InitialCombatState {
        val player = playerPort.findById(createCombatCommand.playerId)

        val combat = Combat(
            id = UUID.randomUUID(),
            player1 = player,
            player2 = createDefaultEnemy(),
            currentTurn = decideTurns(),
            combatState = "In-progress",
            winner = ""
        )

        TODO("Not yet implemented")
    }

    private fun createDefaultEnemy() : Player {
        TODO("Not yet implemented")
    }

    private fun decideTurns() : String {
        TODO("Not yet implemented")
    }
}