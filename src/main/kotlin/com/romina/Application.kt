package com.romina

import com.romina.combat.application.domain.service.CombatActionsService
import com.romina.combat.application.domain.service.CreateCombatService
import com.romina.combat.infrastructure.driven.adapter.persistence.combat.CombatPostgresAdapter
import com.romina.player.application.domain.service.CreatureService
import com.romina.player.application.domain.service.PlayerService
import com.romina.player.infrastructure.driven.adapter.persistence.creatures.CreaturePostgresAdapter
import com.romina.player.infrastructure.driven.adapter.persistence.players.PlayerPostgresAdapter
import io.ktor.server.application.*
import io.ktor.server.netty.EngineMain

fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module() {

    val playerRepository = PlayerPostgresAdapter()
    val playerService = PlayerService(playerRepository)
    val combatRepository = CombatPostgresAdapter()
    val creaturesRepository = CreaturePostgresAdapter()
    val creatureService = CreatureService(creaturesRepository)
    val createCombatService = CreateCombatService(
            playerRepository,
            combatRepository,
            creaturesRepository
        )
    val combatActionsService = CombatActionsService(
        combatRepository,
        creaturesRepository
    )

    configureSerialization(playerRepository)
    configureSockets(combatActionsService)
    configureDatabases(environment.config)
    configureRouting(playerService, creatureService, createCombatService)
}
