package com.romina.player.application.domain.model

import java.util.UUID

data class Player(
    val playerId : UUID = UUID.randomUUID(),
    val name : String,
    val creatures: MutableList<Creature> = mutableListOf(),
) {
    fun addCreature(creatureName: String, creatureClass: CreatureClass){
        creatures.add(Creature(
            name = creatureName,
            owner = playerId,
            creatureClass = creatureClass,
            level = 0,
            xp = 0,
            attributes = creatureClass.initDefaultAttributesValues()
        ))
    }
}