package com.romina

import com.romina.combat.application.domain.service.CombatService
import com.romina.player.application.domain.service.CreatureService
import com.romina.player.application.domain.service.PlayerService
import com.romina.player.infrastructure.driven.adapter.persistence.creatures.CreaturePostgresAdapter
import com.romina.player.infrastructure.driven.adapter.persistence.players.PlayerPostgresAdapter
import io.ktor.server.application.*


fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {

    val playerRepository = PlayerPostgresAdapter()
    val playerService = PlayerService(playerRepository)
    val creaturesRepository = CreaturePostgresAdapter()
    val creatureService = CreatureService(creaturesRepository)
    val combatService = CombatService()

    configureSerialization(playerRepository)
    configureSockets()
    configureDatabases(environment.config)
    configureRouting(playerService, creatureService, combatService)
}
