package com.romina.player.infrastructure.driven.adapter.persistence.creatures

import com.romina.player.infrastructure.driven.adapter.persistence.players.PlayerDAO
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.java.UUIDEntity
import org.jetbrains.exposed.v1.dao.java.UUIDEntityClass
import java.util.UUID

class CreatureDAO (id : EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<CreatureDAO>(CreatureTable)
    var name by CreatureTable.name
    var owner by PlayerDAO.Companion referencedOn CreatureTable.owner
    var creatureClass by CreatureTable.creatureClass
    var level by CreatureTable.level
    var xp by CreatureTable.xp
    var hp by CreatureTable.hp
    var attack by CreatureTable.attack
    var speed by CreatureTable.speed

}