package com.romina.player.infrastructure.driver.adapter.rest.routes

import com.romina.player.application.ports.`in`.CreatePlayerCommand
import com.romina.player.application.ports.`in`.CreatePlayerUseCase
import com.romina.player.infrastructure.driver.request.CreatePlayerRequest
import com.romina.player.infrastructure.driver.response.CreatePlayerResponse
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post

fun Route.createRoute(useCase : CreatePlayerUseCase){
    post("/players"){
        val body = call.receive<CreatePlayerRequest>()
        val playerId = useCase.createPlayer(CreatePlayerCommand(body.playerName))
        call.respond(HttpStatusCode.Created, CreatePlayerResponse(playerId.toString()))
    }
}
