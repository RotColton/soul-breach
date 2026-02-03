package com.romina.player.infrastructure.driven.adapter.persistence.players

import org.jetbrains.exposed.v1.core.dao.id.java.UUIDTable

object PlayerTable : UUIDTable("players") {
    val name = varchar("name", 50)

}