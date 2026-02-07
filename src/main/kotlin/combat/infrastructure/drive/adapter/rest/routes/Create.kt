package com.romina.combat.infrastructure.drive.adapter.rest.routes

import com.romina.combat.application.domain.`in`.CreateCombatCommand
import com.romina.combat.application.domain.`in`.CreateCombatUseCase
import com.romina.combat.infrastructure.drive.request.CreateCombatRequest
import com.romina.combat.infrastructure.drive.response.toResponse
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import java.util.UUID

fun Route.createRoute(useCase : CreateCombatUseCase){
    post("/combats"){
        val body = call.receive<CreateCombatRequest>()
        val combat = useCase.createCombatWithDefaultEnemy(
            CreateCombatCommand(UUID.fromString(body.playerId)))

        call.respond(HttpStatusCode.Created, combat.toResponse("STATE_STORAGE"))
    }
}