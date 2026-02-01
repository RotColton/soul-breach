package com.romina.infrastructure.driver.rest.routes.player.request

import com.romina.application.domain.model.CreatureClass
import kotlinx.serialization.Serializable
//TODO: validation rules
@Serializable
data class AddCreatureRequest(
    val creatureName: String,
    val creatureClass : CreatureClass
)
