package com.romina.player.infrastructure.adapters.driver.rest.routes.player

import com.romina.player.application.domain.ports.`in`.AddCreatureToPlayerUseCase
import com.romina.player.application.domain.ports.`in`.CreatePlayerUseCase
import io.ktor.server.routing.Route

fun Route.playerRoutes(createUseCase : CreatePlayerUseCase, addCreatureUseCase: AddCreatureToPlayerUseCase){
    createRoute(createUseCase)
    addCreatureRoute(addCreatureUseCase)
}
