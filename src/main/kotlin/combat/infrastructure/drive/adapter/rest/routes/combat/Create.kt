package com.romina.combat.infrastructure.drive.adapter.rest.routes.combat

import com.romina.combat.application.domain.`in`.CreateCombatCommand
import com.romina.combat.application.domain.`in`.CreateCombatUseCase
import com.romina.combat.infrastructure.drive.request.CreateCombatRequest
import com.romina.combat.infrastructure.drive.response.CreateCombatResponse
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post

fun Route.createRoute(useCase : CreateCombatUseCase){
    post("/combats"){
        val body = call.receive<CreateCombatRequest>()
        val combat = useCase.createCombat(CreateCombatCommand(body.playerId))
        call.respond(HttpStatusCode.Created, CreateCombatResponse(combat.toResponse()))
    }
}