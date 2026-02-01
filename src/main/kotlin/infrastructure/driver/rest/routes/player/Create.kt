package com.romina.infrastructure.driven.rest.routes.player

import com.romina.application.domain.ports.`in`.CreatePlayerUseCase
import com.romina.infrastructure.driven.rest.routes.player.reponse.toResponse
import com.romina.infrastructure.driven.rest.routes.player.request.CreatePlayerRequest
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post

fun Route.createRoute(useCase : CreatePlayerUseCase){
    post("/players"){
        val body = call.receive<CreatePlayerRequest>()
        call.respond(HttpStatusCode.Created, useCase.createPlayer(body).toResponse())
    }
}
