package com.romina.player.application.domain.model

import java.util.UUID

data class Player(
    //TODO: refactor id initialization - It`s initializing both DAO and Model
    val playerId : UUID = UUID.randomUUID(),
    val name : String,
    val creatures: MutableList<Creature> = mutableListOf(),
)