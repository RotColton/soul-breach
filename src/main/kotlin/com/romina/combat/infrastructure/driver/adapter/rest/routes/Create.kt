package com.romina.combat.infrastructure.driver.adapter.rest.routes


import com.romina.combat.application.ports.`in`.CreateCombatUseCase
import com.romina.combat.infrastructure.driver.request.CreateCombatRequest
import com.romina.combat.infrastructure.driver.response.toResponse
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
            CreateCombatUseCase.Command(UUID.fromString(body.playerId)))

        call.respond(HttpStatusCode.Created, combat.toResponse("STATE_STORAGE"))
    }
}