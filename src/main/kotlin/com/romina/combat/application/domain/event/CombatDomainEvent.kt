package com.romina.combat.application.domain.event

import java.util.UUID

sealed class CombatDomainEvent {
    data class CreatureDied(val creatureId: UUID, val playerId: UUID) : CombatDomainEvent()
}