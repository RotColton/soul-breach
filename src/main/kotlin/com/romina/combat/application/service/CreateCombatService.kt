package com.romina.combat.application.service

import com.romina.combat.application.ports.`in`.CreateCombatCommand
import com.romina.combat.application.ports.`in`.CreateCombatUseCase
import com.romina.combat.application.domain.model.Combat
import com.romina.combat.application.domain.model.CombatState
import com.romina.combat.application.ports.out.CombatPort
import com.romina.player.application.domain.model.Creature
import com.romina.player.application.domain.model.CreatureClassFactory
import com.romina.player.application.domain.model.CreatureClassName
import com.romina.player.application.domain.model.Player
import com.romina.player.application.ports.out.CreaturePort
import com.romina.player.application.ports.out.PlayerPort
import java.util.UUID

class CreateCombatService(
    private val playerPort : PlayerPort,
    private val combatPort : CombatPort,
    private val creaturePort : CreaturePort,
) : CreateCombatUseCase {

    override suspend fun createCombatWithDefaultEnemy(createCombatCommand: CreateCombatCommand): Combat {
        val player = playerPort.findById(createCombatCommand.playerId)
        val enemy = Player(name = "Bastard-AI")
        generateDefaultEnemyCreatures(enemy)

        //TODO refactor
        playerPort.save(enemy)
        creaturePort.save(enemy.creatures[0])
        creaturePort.save(enemy.creatures[1])

        val turnOrder = Combat.determineTurnOrderBySpeed(player.creatures, enemy.creatures)
        val fistTurn = turnOrder.first()

        val combat = Combat(
            player1 = player,
            player2 = enemy,
            turnOrder = turnOrder,
            currentTurn = fistTurn,
            state = CombatState.ONGOING,
        )

        return combatPort.save(combat)
    }

    private fun generateDefaultEnemyCreatures(player : Player) {
        val warrior = CreatureClassFactory.fromString("WARRIOR")
        val defender = CreatureClassFactory.fromString("DEFENDER")
        val goblin = Creature(
            id = UUID.randomUUID(),
            name = "Goblin",
            owner = player.id,
            creatureClass = CreatureClassName.WARRIOR,
            level = 1,
            xp = 0,
            attributes = warrior.defaultAttributes
        )
        val smile =
            Creature(id = UUID.randomUUID(),
                name = "Slime",
                owner = player.id,
                creatureClass = CreatureClassName.DEFENDER,
                level = 1,
                xp = 0,
                attributes = defender.defaultAttributes
            )
        player.creatures.add(goblin)
        player.creatures.add(smile)
    }
}