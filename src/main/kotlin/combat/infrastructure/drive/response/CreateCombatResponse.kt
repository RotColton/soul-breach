package com.romina.combat.infrastructure.drive.response

import java.util.UUID

data class CreateCombatResponse(
    val combatID : UUID,
    //todo { Turn Order, Creatures, Attributes, Etc. }
    val initialState : String,
)
