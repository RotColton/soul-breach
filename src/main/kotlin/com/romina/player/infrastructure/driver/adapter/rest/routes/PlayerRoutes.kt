package com.romina.player.infrastructure.driver.adapter.rest.routes

import com.romina.player.application.ports.`in`.AddCreatureToPlayerUseCase
import com.romina.player.application.ports.`in`.CreatePlayerUseCase
import com.romina.player.application.ports.`in`.GetPlayerDetailsUseCase
import io.ktor.server.routing.Route

fun Route.playerRoutes(createUseCase : CreatePlayerUseCase,
                       addCreatureUseCase: AddCreatureToPlayerUseCase,
                       getPlayerDetailsUseCase: GetPlayerDetailsUseCase
){
    createRoute(createUseCase)
    addCreatureRoute(addCreatureUseCase)
    playerDetails(getPlayerDetailsUseCase)
}
