package com.romina.combat.infrastructure.driver.request

import kotlinx.serialization.Serializable
@Serializable
data class CombatActionRequest(
    val type : String,
    val activeId : String,
    val targetId : String,
) {
}