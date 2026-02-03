package com.romina.player.application.domain.model

import java.util.UUID


data class Creature (
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val owner: UUID,
    val creatureClass: CreatureClassName,
    var level: Int = 0,
    var xp: Int = 0,
    val attributes: ElementalsAttributes
) {

}