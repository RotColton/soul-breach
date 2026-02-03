package com.romina.player.application.domain.service

import com.romina.player.application.domain.model.Creature
import com.romina.player.application.domain.model.CreatureClassFactory
import com.romina.player.application.domain.model.Player
import com.romina.player.application.domain.ports.`in`.AddCreatureToPlayerCommand
import com.romina.player.application.domain.ports.`in`.AddCreatureToPlayerUseCase
import com.romina.player.application.domain.ports.out.CreatureCommandPort


class CreatureService(
    private val commandPort : CreatureCommandPort
) : AddCreatureToPlayerUseCase {

    override suspend fun addCreature(command: AddCreatureToPlayerCommand): Player {
        val creatureClass = CreatureClassFactory.fromString(command.creatureClass)
        val creature = Creature(
            name = command.creatureName,
            owner = command.playerId,
            creatureClass = creatureClass.className,
            level = 0,
            xp = 0,
            attributes = creatureClass.defaultAttributes
        )
        return commandPort.createCreatureWithOwner(creature, command.playerId)
    }
}