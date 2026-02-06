package com.romina

import com.romina.combat.infrastructure.driven.adapter.persistence.combat.CombatTable
import com.romina.player.infrastructure.driven.adapter.persistence.creatures.CreatureTable
import com.romina.player.infrastructure.driven.adapter.persistence.players.PlayerTable
import io.ktor.server.application.*
import io.ktor.server.config.ApplicationConfig
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.SchemaUtils
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

fun Application.configureDatabases(config: ApplicationConfig) {
    val url = config.property("storage.jdbcURL").getString()
    val user = config.property("storage.user").getString()
    val password = config.property("storage.password").getString()

    val database = Database.connect(
        url,
        user = user,
        password = password
    )

    transaction(database) {
        SchemaUtils.create(PlayerTable, CreatureTable, CombatTable)
    }
}