package com.romina.com.romina.player.application.domain.model

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import player.application.domain.model.Creature
import player.application.domain.model.CreatureClass
import player.application.domain.model.CreatureClassFactory
import java.util.UUID
import kotlin.test.assertTrue

class ApplyXPTest {

    lateinit var creature : Creature
    lateinit var playerId : UUID
    lateinit var creatureId : UUID
    lateinit var creatureClass : CreatureClass

    @BeforeEach
    fun setUp() {
        playerId = UUID.randomUUID()
        creatureId = UUID.randomUUID()
        creatureClass = CreatureClassFactory.fromString("WARRIOR")

        creature = Creature(
            id = creatureId,
            owner = playerId,
            name = "Agumon",
            level = 1,
            xp = 0,
            creatureClass = creatureClass.className,
            attributes = creatureClass.defaultAttributes
        )
    }

    @Test
    fun `should level up and improve the attributes`(){
        val level = creature.level
        val hp = creature.attributes.hp
        val attack = creature.attributes.attack
        val speed = creature.attributes.speed
        val xp = creature.xp

        creature.applyXP(100)

        assertTrue{ level < creature.level }
        assertTrue { hp < creature.attributes.hp }
        assertTrue { attack < creature.attributes.attack }
        assertTrue { speed < creature.attributes.speed }
        assertTrue{ xp < creature.xp }
    }

}