package com.romina.player.infrastructure.driven.adapter.persistence

import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.java.UUIDEntity
import org.jetbrains.exposed.v1.dao.java.UUIDEntityClass
import java.util.UUID

class CreatureEntity (id : EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<CreatureEntity>(CreaturesTable)
    var name by CreaturesTable.name
    var owner by PlayerEntity referencedOn CreaturesTable.owner
    var creatureClass by CreaturesTable.creatureClass
    var level by CreaturesTable.level
    var xp by CreaturesTable.xp
    var hp by CreaturesTable.hp
    var attack by CreaturesTable.attack
    var speed by CreaturesTable.speed

}