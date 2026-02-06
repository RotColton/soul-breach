package com.romina

import com.romina.combat.application.domain.`in`.CombatDetailsCommand
import com.romina.combat.application.domain.`in`.ExecuteTurnCommand
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
        pingPeriod = 15.seconds
        timeout = 15.seconds
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
                val currentCombat = combatActionsService.getCombat(CombatDetailsCommand(combatId))
                sendSerialized<CombatEvent>(CombatEvent.StateUpdate(currentCombat.toResponse()))

                for (frame in incoming) {
                    val actionRequest = receiveDeserialized<CombatActionRequest>()

                    if (actionRequest.type == "ACTION") {
                        combatActionsService.executeTurn(
                            ExecuteTurnCommand(
                                actionRequest.type,
                                UUID.fromString(actionRequest.activeId),
                                UUID.fromString(actionRequest.targetId),
                                currentCombat = currentCombat
                            ))
                    }
                }

            } catch (e: Exception) {
                sendSerialized<CombatEvent>(CombatEvent.Error(e.message ?: "Unknown error"))
            } finally {
                // Limpieza al desconectarse
            }


        }
    }
}
