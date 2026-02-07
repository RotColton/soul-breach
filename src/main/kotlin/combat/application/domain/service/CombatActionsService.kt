package com.romina.combat.application.domain.service

import com.romina.combat.application.domain.exception.InvalidTurnException
import com.romina.combat.application.domain.`in`.CombatDetailsCommand
import com.romina.combat.application.domain.`in`.CombatDetailsUseCase
import com.romina.combat.application.domain.`in`.ExecuteCombatActionUseCase
import com.romina.combat.application.domain.`in`.ExecuteTurnCommand
import com.romina.combat.application.domain.model.Combat
import com.romina.combat.application.domain.out.CombatPort

class CombatActionsService(
    private val port : CombatPort
) : CombatDetailsUseCase,
    ExecuteCombatActionUseCase {

    override suspend fun getCombat(command: CombatDetailsCommand): Combat {
        return port.getById(command.combatId)
    }

    override fun executeTurn(command: ExecuteTurnCommand) : Combat{
        //validar si es el turno del atacante
        val combat = command.currentCombat
        combat.validateTurn(command.activeId)

        //atacar
        //dañar
        //eliminar creatura si muere
        combat.attack(command.targetId, command.activeId)

        //avanzar el turno
        combat.nextTurn()
        //detectar final del combate

        //devolver rta
        return combat
    }

}