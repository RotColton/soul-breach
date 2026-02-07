package com.romina.combat.infrastructure.driver.adapter.rest.routes

import com.romina.combat.application.ports.`in`.CreateCombatUseCase
import io.ktor.server.routing.Route

fun Route.combatRoutes(createUseCase : CreateCombatUseCase,
){
    createRoute(createUseCase)
}
