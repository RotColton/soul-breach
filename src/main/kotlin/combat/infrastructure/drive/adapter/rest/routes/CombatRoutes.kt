package com.romina.combat.infrastructure.drive.adapter.rest.routes

import com.romina.combat.application.domain.`in`.CreateCombatUseCase
import io.ktor.server.routing.Route

fun Route.combatRoutes(createUseCase : CreateCombatUseCase,
){
    createRoute(createUseCase)
}
