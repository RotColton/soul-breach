package com.romina.player.infrastructure.driver.request


import kotlinx.serialization.Serializable
@Serializable
data class AddCreatureRequest(
    val creatureName: String,
    val creatureClass : String
)
