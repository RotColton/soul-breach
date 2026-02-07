package com.romina.combat.infrastructure.drive.event

import com.romina.combat.infrastructure.drive.response.StateCombatResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
sealed class CombatEvent {

    @Serializable
    @SerialName("STATE_UPDATE")
    data class StateUpdate(val combat: StateCombatResponse) : CombatEvent()
    @Serializable
    @SerialName("ERROR")
    data class Error(val message: String) : CombatEvent()
    @Serializable
    @SerialName("GAME_OVER")
    data class GameOver(val message: String) : CombatEvent()

}