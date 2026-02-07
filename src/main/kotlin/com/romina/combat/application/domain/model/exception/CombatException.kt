package com.romina.combat.application.domain.model.exception

sealed class CombatException(msg: String) : RuntimeException(msg)
    class EmptyArmyException : CombatException("Cannot start a battle without creatures")
    class InvalidTurnException: CombatException("It is not creature turn")
    class AttackerNotFoundException: CombatException("The creature is not a team")
    class TargetNotFoundException(): CombatException("The target is not a team")
