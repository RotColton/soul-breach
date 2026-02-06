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

    override fun executeTurn(command: ExecuteTurnCommand) {
        //validar si es el turno del atacante
        if(command.activeId != command.currentCombat.currentTurn) {
            throw InvalidTurnException("It is not creature $command.activeId's turn.")
        }
        //atacar
        //dañar
        //eliminar creatura si muere
        //avanzar el turno
        //detectar final del combate
        //devolver rta
    }

}