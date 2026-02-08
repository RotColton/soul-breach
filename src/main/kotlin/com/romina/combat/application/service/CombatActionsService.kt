package com.romina.combat.application.service

import com.romina.combat.application.domain.event.CombatDomainEvent
import com.romina.combat.application.ports.`in`.CombatActionUseCase
import com.romina.combat.application.domain.model.Combat
import com.romina.combat.application.domain.model.CombatState
import com.romina.combat.application.domain.model.Winner
import com.romina.combat.application.ports.`in`.CombatDetailsUseCase
import com.romina.combat.application.ports.out.CombatPort
import com.romina.player.application.ports.out.CreaturePort


class CombatActionsService(
    private val combatPort : CombatPort,
    private val creaturePort : CreaturePort,

    ) : CombatActionUseCase, CombatDetailsUseCase {

    override suspend fun executeTurn(command: CombatActionUseCase.Command) : Combat{
        val combat = command.currentCombat

        combat.validateTurn(command.activeId)
        combat.attack(command.targetId, command.activeId)

        combat.checkWinner()

        if(combat.state == CombatState.FINISHED){
            persistFinalState(combat)
            return combatPort.update(combat)
        }

        combat.nextTurn()
        return combat
    }

    override suspend fun combatDetails(command: CombatDetailsUseCase.Command): Combat {
        return combatPort.getById(command.combatId)
    }

    private suspend fun persistFinalState(combat : Combat){
        combat.events.forEach { event ->
            when (event) {
                is CombatDomainEvent.CreatureDied -> {
                    creaturePort.delete(event.creatureId)
                    // TODO: LOGGER
                    // println("Notificando: La criatura ${event.creatureId} ha muerto.")
                }
            }
        }
        combat.clearEvents()

        if(combat.winner == Winner.PLAYER)
            combat.player1.creatures.forEach { it.applyXP(100) }

        combat.player1.creatures.forEach { creature -> creaturePort.update(creature) }
    }

}