package com.romina

import com.romina.combat.application.domain.`in`.CombatDetailsCommand
import com.romina.combat.application.domain.`in`.ExecuteTurnCommand
import com.romina.combat.application.domain.model.CombatState
import com.romina.combat.application.domain.service.CombatActionsService
import com.romina.combat.infrastructure.drive.event.CombatEvent
import com.romina.combat.infrastructure.drive.request.CombatActionRequest
import com.romina.combat.infrastructure.drive.response.toResponse
import io.ktor.serialization.kotlinx.KotlinxWebsocketSerializationConverter
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.serialization.json.Json
import java.util.UUID
import kotlin.time.Duration.Companion.seconds

fun Application.configureSockets(combatActionsService : CombatActionsService) {
    install(WebSockets) {
        pingPeriod = 90.seconds
        timeout = 90.seconds
        maxFrameSize = Long.MAX_VALUE
        masking = false
        contentConverter = KotlinxWebsocketSerializationConverter(Json)
    }
    routing {
        webSocket("/ws/combats/{combatId}") {

            val combatId = call.parameters["combatId"]?.let { UUID.fromString(it) }
                ?: return@webSocket close(CloseReason(CloseReason.Codes.VIOLATED_POLICY, "Invalid Combat ID"))

            val playerId = call.request.queryParameters["playerId"]?.let { UUID.fromString(it) }
                ?: return@webSocket close(CloseReason(CloseReason.Codes.VIOLATED_POLICY, "Missing Player ID"))

            try {
                var currentCombat = combatActionsService.getCombat(CombatDetailsCommand(combatId))

                do{
                    sendSerialized<CombatEvent>(
                        CombatEvent.StateUpdate(
                        currentCombat.toResponse("STATE_STORAGE")))

                    if (currentCombat.state == CombatState.FINISHED) {

                        close(CloseReason(CloseReason.Codes.NORMAL,
                            "Combat finished. Winner: ${currentCombat.winner}"))
                        break
                    }

                    try {
                        val actionRequest = receiveDeserialized<CombatActionRequest>()
                        if (actionRequest.type == "ACTION") {
                            currentCombat = combatActionsService.executeTurn(
                                ExecuteTurnCommand(
                                    actionRequest.type,
                                    UUID.fromString(actionRequest.activeId),
                                    UUID.fromString(actionRequest.targetId),
                                    currentCombat = currentCombat
                                )
                            )

                        }
                    }catch (e: Exception) {
                        sendSerialized<CombatEvent>(CombatEvent.Error(e.message ?: "Unknown error"))
                    }
                }while(true)

            } catch (e: Exception) {
                sendSerialized<CombatEvent>(CombatEvent.Error(e.message ?: "Unknown error"))
            }
        }
    }
}
