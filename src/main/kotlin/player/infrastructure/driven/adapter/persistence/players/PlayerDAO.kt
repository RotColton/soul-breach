package com.romina.player.infrastructure.driven.adapter.persistence.players

import com.romina.player.infrastructure.driven.adapter.persistence.creatures.CreatureDAO
import com.romina.player.infrastructure.driven.adapter.persistence.creatures.CreatureTable
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.java.UUIDEntity
import org.jetbrains.exposed.v1.dao.java.UUIDEntityClass
import java.util.UUID

class PlayerDAO (id : EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<PlayerDAO>(PlayerTable)
    var name by PlayerTable.name
    val creatures by CreatureDAO referrersOn CreatureTable.owner
}

