package com.romina.infrastructure.driver.rest.routes.player.request

import kotlinx.serialization.Serializable
//TODO: validation rules
@Serializable
data class CreatePlayerRequest(
    val playerName : String
)