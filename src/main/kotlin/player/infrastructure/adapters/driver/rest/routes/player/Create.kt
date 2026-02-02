package com.romina.player.infrastructure.adapters.driver.rest.routes.player

import com.romina.player.application.domain.ports.`in`.CreatePlayerUseCase
import com.romina.player.infrastructure.adapters.driver.rest.dto.toDTO
import com.romina.player.infrastructure.adapters.driver.rest.routes.player.request.CreatePlayerRequest
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post

fun Route.createRoute(useCase : CreatePlayerUseCase){
    post("/players"){
        val body = call.receive<CreatePlayerRequest>()
        call.respond(HttpStatusCode.Created, useCase.createPlayer(body).toDTO())
    }
}
