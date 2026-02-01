package com.romina.infrastructure.driven.rest.routes.player.request

import kotlinx.serialization.Serializable

@Serializable
data class CreatePlayerRequest(
    val playerName : String
)