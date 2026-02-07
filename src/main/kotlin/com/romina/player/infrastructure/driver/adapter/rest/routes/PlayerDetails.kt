package com.romina.player.infrastructure.driver.adapter.rest.routes

import com.romina.player.application.ports.`in`.GetPlayerDetailsUseCase
import com.romina.player.application.ports.`in`.PlayerDetailsQuery
import com.romina.player.infrastructure.driver.response.dto.toDTO
import com.romina.player.infrastructure.driver.adapter.rest.getUUID
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get


fun Route.playerDetails(useCase : GetPlayerDetailsUseCase){
    get("/players/{id}"){
        val playerId = call.getUUID("id")
        call.respond(HttpStatusCode.OK,
            useCase.getPlayerDetails(PlayerDetailsQuery(playerId = playerId)).toDTO())
    }
}