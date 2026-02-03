package com.romina.player.infrastructure.drive.adapter.rest.routes.player

import com.romina.player.application.domain.ports.`in`.AddCreatureToPlayerUseCase
import com.romina.player.infrastructure.drive.response.toResponse
import com.romina.player.infrastructure.drive.request.AddCreatureRequest
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post

fun Route.addCreatureRoute(useCase: AddCreatureToPlayerUseCase) {
    post("players/{id}/creatures"){
        val body =  call.receive<AddCreatureRequest>()
        call.respond(HttpStatusCode.Created, useCase.addCreature(body).toResponse())
    }

}