package com.romina.application.domain.model

import kotlinx.serialization.Serializable
import java.util.UUID

data class Player(
    val playerId : UUID = UUID.randomUUID(),
    val name : String,
    val creatures: MutableList<Creature> = mutableListOf(),
) {
    fun addCreature(creatureName : String, creatureClass : CreatureClass ){
        creatures.add(Creature(
            name = creatureName,
            owner = playerId,
            creatureClass = creatureClass,
            level = 0,
            xp = 0,
            // TODO("attribute business rules not yet implemented")
            attributes = Attribute(hp = 100, attack = 10, speed = 20)
        ))
    }
}