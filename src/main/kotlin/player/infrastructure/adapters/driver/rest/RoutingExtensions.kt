package com.romina.player.infrastructure.adapters.driver.rest

import io.ktor.server.application.ApplicationCall
import java.util.UUID

fun ApplicationCall.getUUID(name: String): UUID {
    val value = parameters[name]
        ?: throw IllegalArgumentException("Parameter '$name' is missing")

    return try {
        UUID.fromString(value)
    } catch (e: Exception) {
        throw IllegalArgumentException("Parameter '$name' must be a valid UUID")
    }
}