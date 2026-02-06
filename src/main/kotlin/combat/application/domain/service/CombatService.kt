package com.romina.combat.application.domain.service

import com.romina.combat.application.domain.`in`.CreateCombatCommand
import com.romina.combat.application.domain.`in`.CreateCombatUseCase
import com.romina.combat.application.domain.`in`.InitialCombatState
import com.romina.combat.application.domain.model.Combat
import com.romina.combat.application.domain.model.CombatState
import com.romina.combat.application.domain.out.CombatPort
import com.romina.player.application.domain.model.Creature
import com.romina.player.application.domain.model.CreatureClassFactory
import com.romina.player.application.domain.model.CreatureClassName
import com.romina.player.application.domain.model.Enchanter
import com.romina.player.application.domain.model.Player
import com.romina.player.application.domain.ports.out.PlayerPort
import java.util.UUID

//TODO rename "DefaultCombatService"
class CombatService(
    private val playerPort : PlayerPort,
    private val combatPort : CombatPort
) : CreateCombatUseCase {

    override suspend fun createCombat(createCombatCommand: CreateCombatCommand): Combat {
        val player = playerPort.findById(createCombatCommand.playerId)
        val enemy = createDefaultEnemy()
        val enemyID = playerPort.save(enemy)

        val turnOrder = Combat.determineTurnOrderBySpeed(player.creatures, enemy.creatures)
        val fistTurn = turnOrder.first()


        val combat = Combat(
            id = UUID.randomUUID(),
            player1 = player,
            player2 = enemy,
            turnOrder = turnOrder,
            currentTurn = fistTurn,
            state = CombatState.IN_PROGRESS,
            winner = null
        )

        val combatId = combatPort.save(combat)
        return combat
    }

    private fun createDefaultEnemy() : Player {
        val id = UUID.randomUUID()
        return Player(
            id = id,
            name = "Bastard-IA",
            creatures = generateDefaultEnemies(id)
        )
    }

    private fun generateDefaultEnemies(playerId : UUID): MutableList<Creature> {
        val warrior = CreatureClassFactory.fromString("WARRIOR")
        val defender = CreatureClassFactory.fromString("DEFENDER")

        return listOf(
            Creature(
                id = UUID.randomUUID(),
                name = "Goblin",
                owner = playerId,
                creatureClass = CreatureClassName.WARRIOR,
                level = 1,
                xp = 0,
                attributes = warrior.defaultAttributes
            ),
            Creature(id = UUID.randomUUID(),
                name = "Slime",
                owner = playerId,
                creatureClass = CreatureClassName.DEFENDER,
                level = 1,
                xp = 0,
                attributes = defender.defaultAttributes
            )
        ) as MutableList<Creature>
    }
}