package com.romina.player.infrastructure.adapters.driver.rest.routes.player.request


import kotlinx.serialization.Serializable
//TODO: validation rules
@Serializable
data class AddCreatureRequest(
    val creatureName: String,
    val creatureClass : String
)
