package com.romina

import com.romina.player.application.domain.service.PlayerService
import com.romina.player.infrastructure.driven.adapter.persistence.PlayerPostgresAdapter
import io.ktor.server.application.*


fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {

    val repository = PlayerPostgresAdapter()
    val playerService = PlayerService(repository)

    configureSerialization(repository)
    configureSockets()
    configureDatabases(environment.config)
    configureRouting(playerService)
}
