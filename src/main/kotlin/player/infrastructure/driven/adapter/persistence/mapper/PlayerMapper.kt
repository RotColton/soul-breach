package com.romina.player.infrastructure.driven.adapter.persistence.mapper


import com.romina.player.application.domain.model.Player
import com.romina.player.infrastructure.driven.adapter.persistence.players.PlayerDAO

fun daoToPlayer(dao: PlayerDAO) = Player(
    id = dao.id.value,
    name = dao.name,
    creatures = dao.creatures.map { daoToCreature(it) }.toMutableList()
)