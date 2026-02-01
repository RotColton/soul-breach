package com.romina


import com.romina.application.domain.ports.`in`.AddCreatureToPlayerUseCase
import com.romina.application.domain.ports.`in`.CreatePlayerUseCase
import com.romina.application.domain.service.PlayerService
import com.romina.infrastructure.driven.rest.routes.player.playerRoutes
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.plugins.swagger.*


fun Application.configureRouting() {

    val playerService = PlayerService()

    routing {
        staticResources("/static", "static")

        swaggerUI(path = "swagger", swaggerFile = "openapi/documentation.yaml")

        playerRoutes(
            createUseCase = playerService,
            addCreatureUseCase = playerService
        )
    }
}

