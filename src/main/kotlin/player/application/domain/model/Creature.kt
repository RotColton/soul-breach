package com.romina.player.application.domain.model

import java.util.UUID


data class Creature (
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val owner: UUID,
    val creatureClass: CreatureClass,
    var level: Int,
    var xp: Int,
    val attributes: ElementalsAttributes
)