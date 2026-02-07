package com.romina.player.infrastructure.drive.adapter.rest.routes

import com.romina.player.application.domain.ports.`in`.AddCreatureToPlayerCommand
import com.romina.player.application.domain.ports.`in`.AddCreatureToPlayerUseCase
import com.romina.player.infrastructure.drive.response.dto.toDTO
import com.romina.player.infrastructure.drive.request.AddCreatureRequest
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import java.util.UUID

fun Route.addCreatureRoute(useCase: AddCreatureToPlayerUseCase) {
    post("players/{id}/creatures"){
        val body =  call.receive<AddCreatureRequest>()
        val idPath = call.parameters["id"]
        val id = UUID.fromString(idPath)

        val player = useCase.addCreature(
            AddCreatureToPlayerCommand(
                id,
                body.creatureName,
                body.creatureClass
            ))
        call.respond(HttpStatusCode.Created, player.toDTO())
    }
}