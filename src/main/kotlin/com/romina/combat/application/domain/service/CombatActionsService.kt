package com.romina.combat.application.domain.service

import com.romina.combat.application.domain.event.CombatDomainEvent
import com.romina.combat.application.domain.`in`.CombatDetailsCommand
import com.romina.combat.application.domain.`in`.CombatDetailsUseCase
import com.romina.combat.application.domain.`in`.ExecuteCombatActionUseCase
import com.romina.combat.application.domain.`in`.ExecuteTurnCommand
import com.romina.combat.application.domain.model.Combat
import com.romina.combat.application.domain.model.CombatState
import com.romina.combat.application.domain.out.CombatPort
import com.romina.player.application.domain.ports.out.CreaturePort


class CombatActionsService(
    private val combatPort : CombatPort,
    private val creaturePort : CreaturePort,

    ) : CombatDetailsUseCase,
    ExecuteCombatActionUseCase {

    override suspend fun getCombat(command: CombatDetailsCommand): Combat {
        return combatPort.getById(command.combatId)
    }

    override suspend fun executeTurn(command: ExecuteTurnCommand) : Combat{
        val combat = command.currentCombat

        combat.validateTurn(command.activeId)
        combat.attack(command.targetId, command.activeId)

        combat.checkWinner()

        if(combat.state == CombatState.FINISHED){
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
            if(combat.winner == "PLAYER")
                combat.player1.creatures.forEach { it.applyXP(100) }

            combat.player1.creatures.forEach { creature -> creaturePort.update(creature) }

            return combatPort.update(combat)
        }

        combat.nextTurn()
        return combat
    }

}