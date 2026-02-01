package com.romina.infrastructure.driven.rest.routes.player

import com.romina.application.domain.ports.`in`.AddCreatureToPlayerUseCase
import com.romina.infrastructure.driven.rest.routes.player.reponse.toResponse
import com.romina.infrastructure.driven.rest.routes.player.request.AddCreatureRequest
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post

fun Route.addCreatureRoute(useCase: AddCreatureToPlayerUseCase) {
    post("players/{playerId}/creatures"){
        val body =  call.receive<AddCreatureRequest>()
        call.respond(HttpStatusCode.Created, useCase.addCreature(body).toResponse())
    }

}