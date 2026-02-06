package com.romina


import com.romina.combat.application.domain.service.CreateCombatService
import com.romina.combat.infrastructure.drive.adapter.rest.routes.combatRoutes
import com.romina.player.application.domain.service.CreatureService
import com.romina.player.application.domain.service.PlayerService
import com.romina.player.infrastructure.drive.adapter.rest.routes.playerRoutes
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.routing.*
import io.ktor.server.plugins.swagger.*


fun Application.configureRouting(
    playerService : PlayerService,
    creatureService: CreatureService,
    createCombatService: CreateCombatService
){

    routing {
        staticResources("/static", "static")

        swaggerUI(path = "swagger", swaggerFile = "openapi/documentation.yaml")

        playerRoutes(
            createUseCase = playerService,
            addCreatureUseCase = creatureService,
            getPlayerDetailsUseCase = playerService
        )
        combatRoutes(createCombatService)
    }
}

