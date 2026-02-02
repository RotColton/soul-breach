package com.romina.player.infrastructure.adapters.driver.rest.routes.player

import com.romina.player.application.domain.ports.`in`.GetPlayerDetailsUseCase
import com.romina.player.application.domain.ports.`in`.PlayerDetailsQuery
import com.romina.player.infrastructure.adapters.driver.rest.dto.toDTO
import com.romina.player.infrastructure.adapters.driver.rest.getUUID
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