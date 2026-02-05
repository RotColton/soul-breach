package com.romina.player.application.domain.service

import com.romina.player.application.domain.exception.PlayerIsNotTheOwnerException
import com.romina.player.application.domain.model.Creature
import com.romina.player.application.domain.model.CreatureClassFactory
import com.romina.player.application.domain.model.Player
import com.romina.player.application.domain.ports.`in`.AddCreatureToPlayerCommand
import com.romina.player.application.domain.ports.`in`.AddCreatureToPlayerUseCase
import com.romina.player.application.domain.ports.`in`.ApplyXPCommand
import com.romina.player.application.domain.ports.`in`.ApplyXPUseCase
import com.romina.player.application.domain.ports.out.CreaturePort

//TODO create diferents services for each use case
class CreatureService(
    private val commandPort : CreaturePort
) : AddCreatureToPlayerUseCase, ApplyXPUseCase {

    override suspend fun addCreature(command: AddCreatureToPlayerCommand): Player {
        val creatureClass = CreatureClassFactory.fromString(command.creatureClass)
        //TODO: separate create logic
        val creature = Creature(
            name = command.creatureName,
            owner = command.playerId,
            creatureClass = creatureClass.className,
            level = 0,
            xp = 0,
            attributes = creatureClass.defaultAttributes
        )
        return commandPort.save(creature)
    }

    override suspend fun applyXP(command: ApplyXPCommand) {
        val creature = commandPort.findById(command.creatureId)

        if (creature.owner != command.playerId)
            throw PlayerIsNotTheOwnerException("Player ${command.playerId} is not the owner of creature ${command.creatureId}")

        creature.applyXP(command.xpGained)
        commandPort.update(creature)
    }
}