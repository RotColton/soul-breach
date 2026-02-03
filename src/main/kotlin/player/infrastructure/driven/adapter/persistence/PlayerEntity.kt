package com.romina.player.infrastructure.driven.adapter.persistence

import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.java.UUIDEntity
import org.jetbrains.exposed.v1.dao.java.UUIDEntityClass
import java.util.UUID

class PlayerEntity (id : EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<PlayerEntity>(PlayersTable)
    var name by PlayersTable.name


}