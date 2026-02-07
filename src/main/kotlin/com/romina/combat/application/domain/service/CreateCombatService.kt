package com.romina.combat.application.domain.service

import com.romina.combat.application.domain.`in`.CreateCombatCommand
import com.romina.combat.application.domain.`in`.CreateCombatUseCase
import com.romina.combat.application.domain.model.Combat
import com.romina.combat.application.domain.model.CombatState
import com.romina.combat.application.domain.out.CombatPort
import com.romina.player.application.domain.model.Creature
import com.romina.player.application.domain.model.CreatureClassFactory
import com.romina.player.application.domain.model.CreatureClassName
import com.romina.player.application.domain.model.Player
import com.romina.player.application.domain.ports.out.CreaturePort
import com.romina.player.application.domain.ports.out.PlayerPort
import java.util.UUID

class CreateCombatService(
    private val playerPort : PlayerPort,
    private val combatPort : CombatPort,
    private val creaturePort : CreaturePort,
) : CreateCombatUseCase {

    override suspend fun createCombatWithDefaultEnemy(createCombatCommand: CreateCombatCommand): Combat {
        val player = playerPort.findById(createCombatCommand.playerId)
        val enemy = createDefaultEnemy()

        //TODO refactor
        playerPort.save(enemy)
        creaturePort.save(enemy.creatures[0])
        creaturePort.save(enemy.creatures[1])

        val turnOrder = Combat.determineTurnOrderBySpeed(player.creatures, enemy.creatures)
        val fistTurn = turnOrder.first()

        val combat = Combat(
            id = UUID.randomUUID(),
            player1 = player,
            player2 = enemy,
            turnOrder = turnOrder,
            currentTurn = fistTurn,
            state = CombatState.ONGOING,
            winner = null
        )

        return combatPort.save(combat)
    }

    private suspend fun createDefaultEnemy() : Player {
        val id = UUID.randomUUID()
        val enemy = Player(
            id = id,
            name = "Bastard-IA",
            creatures = generateDefaultEnemyCreatures(id)
        )
        return enemy
    }

    private suspend fun generateDefaultEnemyCreatures(playerId : UUID): MutableList<Creature> {
        val warrior = CreatureClassFactory.fromString("WARRIOR")
        val defender = CreatureClassFactory.fromString("DEFENDER")
        val goblin = Creature(
            id = UUID.randomUUID(),
            name = "Goblin",
            owner = playerId,
            creatureClass = CreatureClassName.WARRIOR,
            level = 1,
            xp = 0,
            attributes = warrior.defaultAttributes
        )
        val smile =
            Creature(id = UUID.randomUUID(),
                name = "Slime",
                owner = playerId,
                creatureClass = CreatureClassName.DEFENDER,
                level = 1,
                xp = 0,
                attributes = defender.defaultAttributes
            )

        return listOf(goblin, smile) as MutableList<Creature>
    }
}