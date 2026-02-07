package com.romina.combat.infrastructure.drive.request

import kotlinx.serialization.Serializable
@Serializable
data class CombatActionRequest(
    val type : String,
    val activeId : String,
    val targetId : String,
) {
}