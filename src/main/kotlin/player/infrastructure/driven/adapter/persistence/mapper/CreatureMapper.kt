package com.romina.player.infrastructure.driven.adapter.persistence.mapper

import com.romina.player.application.domain.model.Creature
import com.romina.player.application.domain.model.CreatureClassName
import com.romina.player.infrastructure.driven.adapter.persistence.creatures.CreatureDAO
import com.romina.player.application.domain.model.ElementalsAttributes

fun daoToCreature(dao : CreatureDAO) = Creature(
    id = dao.id.value,
    name = dao.name,
    owner = dao.owner.id.value,
    creatureClass = CreatureClassName.valueOf(dao.creatureClass),
    level = dao.level,
    xp = dao.xp,
    attributes = ElementalsAttributes(
        hp = dao.hp,
        attack = dao.attack,
        speed = dao.speed
    )
)
