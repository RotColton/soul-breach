package com.romina.player.infrastructure.driven.adapter.persistence

import org.jetbrains.exposed.v1.core.dao.id.java.UUIDTable

object PlayersTable : UUIDTable("players") {
    val name = varchar("name", 50)
}