package com.romina.player.infrastructure.driven.adapter.persistence.creatures

import com.romina.player.infrastructure.driven.adapter.persistence.players.PlayerTable
import org.jetbrains.exposed.v1.core.dao.id.java.UUIDTable

object CreatureTable : UUIDTable("creatures") {
    val name = varchar("name", 50)
    val owner = reference("owner", PlayerTable)
    val creatureClass = varchar("creatureClass", 50)
    val level = integer("level")
    val xp = integer("xp")
    val hp = integer("hp")
    val attack = integer("attack")
    val speed = integer("speed")


}