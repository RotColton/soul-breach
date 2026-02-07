package com.romina.player.application.domain.model

import java.util.UUID

data class Player(
    val id : UUID = UUID.randomUUID(),
    val name : String,
    val creatures: MutableList<Creature> = mutableListOf(),
)