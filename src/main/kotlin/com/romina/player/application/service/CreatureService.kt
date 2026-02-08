package com.romina.player.application.service

import com.romina.player.application.domain.model.exception.PlayerIsNotTheOwnerException
import com.romina.player.application.domain.model.Creature
import com.romina.player.application.domain.model.CreatureClassFactory
import com.romina.player.application.domain.model.Player
import com.romina.player.application.ports.`in`.AddCreatureToPlayerUseCase
import com.romina.player.application.ports.`in`.ApplyXPUseCase
import com.romina.player.application.ports.out.CreaturePort
import com.romina.player.application.ports.out.PlayerPort


class CreatureService(
    private val creaturePort : CreaturePort,
    private val playerPort : PlayerPort,

    ) : AddCreatureToPlayerUseCase, ApplyXPUseCase {

    override suspend fun addCreature(command: AddCreatureToPlayerUseCase.Command): Player {
        val creatureClass = CreatureClassFactory.fromString(command.creatureClass)

        val creature = Creature(
            name = command.creatureName,
            owner = command.playerId,
            creatureClass = creatureClass.className,
            attributes = creatureClass.defaultAttributes
        )
        creaturePort.save(creature)

        return playerPort.findById(creature.owner)
    }

    override suspend fun applyXP(command: ApplyXPUseCase.Command) {
        val creature = creaturePort.findById(command.creatureId)

        if (creature.owner != command.playerId)
            throw PlayerIsNotTheOwnerException("Player ${command.playerId} is not the owner of creature ${command.creatureId}")

        creature.applyXP(command.xpGained)
        creaturePort.update(creature)
    }
}