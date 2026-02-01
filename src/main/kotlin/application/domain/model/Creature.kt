package com.romina.application.domain.model

import java.util.UUID


data class Creature (
    val id : UUID = UUID.randomUUID(),
    val name : String,
    val owner : UUID,
    val creatureClass : CreatureClass,
    val level: Int,
    val xp: Int,
    val attributes : Attribute
)