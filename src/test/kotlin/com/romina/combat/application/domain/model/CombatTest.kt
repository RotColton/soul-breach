package com.romina.com.romina.combat.application.domain.model

import com.romina.combat.application.domain.model.Combat
import com.romina.combat.application.domain.model.CombatState
import com.romina.player.application.domain.model.Creature
import com.romina.player.application.domain.model.CreatureClassFactory
import com.romina.player.application.domain.model.Player
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CombatTest {

    lateinit var player: Player
    lateinit var enemy : Player
    lateinit var combat: Combat
    lateinit var warrior : Creature
    lateinit var defender: Creature


    @BeforeEach
    fun setUp() {
        player = Player(name = "Player")
        enemy = Player(name = "Enemy")

        val warriorClass = CreatureClassFactory.fromString("warrior")
        val defenderClas = CreatureClassFactory.fromString("defender")

        warrior = Creature(
            name = "Warrior",
            owner = player.id,
            creatureClass = warriorClass.className,
            attributes = warriorClass.defaultAttributes.copy()
        )

        defender = Creature(
            name = "Defender",
            owner = enemy.id,
            creatureClass = defenderClas.className,
            attributes = defenderClas.defaultAttributes.copy()
        )

        player.creatures.add(warrior)
        enemy.creatures.add(defender)

        val turnOrder = Combat.Companion.determineTurnOrderBySpeed(player.creatures, enemy.creatures)
        val fistTurn = turnOrder.first()

        combat = Combat(
            player = player,
            enemy = enemy,
            turnOrder = turnOrder,
            currentTurn = fistTurn,
            state = CombatState.ONGOING
        )
    }

    @Test
    fun `should deal damage`(){
        val targetHp = defender.attributes.hp
        val damagePoints = warrior.attributes.attack

        combat.attack(defender.id, warrior.id)

        Assertions.assertEquals(targetHp - damagePoints, defender.attributes.hp)
    }

    @Test
    fun `should removal a creature if it is dead`(){
        combat.enemy.creatures[0].attributes.hp = 1;
        combat.attack(defender.id, warrior.id)

        Assertions.assertFalse(combat.enemy.creatures.contains(defender))
        Assertions.assertFalse(combat.turnOrder.contains(defender.id))
    }

    @Test
    fun `should player wins if all enemy's creatures are dead`(){
        combat.enemy.creatures[0].attributes.hp = 1;
        combat.attack(defender.id, warrior.id)
        combat.checkWinner()

        Assertions.assertEquals("PLAYER", combat.winner.name)

    }

    @Test
    fun `should combat ends if a player wins`(){
        combat.enemy.creatures[0].attributes.hp = 1;
        combat.attack(defender.id, warrior.id)
        combat.checkWinner()

        Assertions.assertEquals("FINISHED", combat.state.name)
    }

}