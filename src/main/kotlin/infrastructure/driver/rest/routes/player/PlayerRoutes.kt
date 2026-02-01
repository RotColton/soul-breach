package com.romina.infrastructure.driver.rest.routes.player

import com.romina.application.domain.ports.`in`.AddCreatureToPlayerUseCase
import com.romina.application.domain.ports.`in`.CreatePlayerUseCase
import io.ktor.server.routing.Route

fun Route.playerRoutes(createUseCase : CreatePlayerUseCase, addCreatureUseCase: AddCreatureToPlayerUseCase){
    createRoute(createUseCase)
    addCreatureRoute(addCreatureUseCase)
}
