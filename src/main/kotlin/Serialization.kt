package com.romina

import com.romina.player.infrastructure.driven.adapter.persistence.PlayerPostgresAdapter
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*

fun Application.configureSerialization(repository: PlayerPostgresAdapter) {
    install(ContentNegotiation) {
        json()
    }
}
