package com.romina.combat.application.domain.model

import com.romina.combat.application.domain.model.exception.EmptyArmyException
import com.romina.player.application.domain.model.Creature
import com.romina.player.application.domain.model.Player
import com.romina.combat.application.domain.event.CombatDomainEvent
import com.romina.combat.application.domain.model.exception.AttackerNotFoundException
import com.romina.combat.application.domain.model.exception.InvalidTurnException
import com.romina.combat.application.domain.model.exception.TargetNotFoundException
import java.util.UUID
enum class CombatState{
    ONGOING,
    FINISHED
}

enum class Winner{
    PLAYER,
    ENEMY,
    NULL
}

data class Combat(
    val id : UUID = UUID.randomUUID(),
    val player1 : Player,
    val player2 : Player,
    var turnOrder: MutableList<UUID>,
    var currentTurn : UUID,
    var state : CombatState,
    var winner: Winner = Winner.NULL
){
    private val _events = mutableListOf<CombatDomainEvent>()
    val events: List<CombatDomainEvent> get() = _events.toList()
    fun clearEvents() = _events.clear()

    companion object {
        fun determineTurnOrderBySpeed(
            playerCreatures: List<Creature>,
            enemies: List<Creature>
        ): MutableList<UUID> {

            if (playerCreatures.isEmpty() || enemies.isEmpty()){
                throw EmptyArmyException()
            }

            val sortedPlayers = playerCreatures.sortedByDescending { it.attributes.speed }
            val sortedEnemies = enemies.sortedByDescending { it.attributes.speed }

            val turnOrder = mutableListOf<UUID>()
            val maxSize = maxOf(sortedPlayers.size, sortedEnemies.size)

            for (i in 0 until maxSize) {
                if (i < sortedPlayers.size) turnOrder.add(sortedPlayers[i].id)
                if (i < sortedEnemies.size) turnOrder.add(sortedEnemies[i].id)
            }

            return turnOrder
        }
    }

    fun attack(targetId : UUID, activeId : UUID){
        val active = findCreature(activeId)
            ?: throw AttackerNotFoundException()
        val target = findCreature(targetId)
            ?: throw TargetNotFoundException()

        target.receiveDamage(active.attributes.attack)
        if(target.attributes.hp <= 0) killCreature(target)
    }

    private fun findCreature(id : UUID) : Creature? = allCreatures().find { creature ->  creature.id == id}

    fun allCreatures(): List<Creature> = player1.creatures + player2.creatures

    private fun killCreature(creature: Creature){
        val ownerId = if (player1.creatures.contains(creature)) player1.id else player2.id

        val removed = player1.creatures.remove(creature) || player2.creatures.remove(creature)
        if(removed) {
            turnOrder.remove(creature.id)
            _events.add(CombatDomainEvent.CreatureDied(creature.id, ownerId))
        }
    }
    fun nextTurn(){
        val index = turnOrder.indexOf(currentTurn)
        currentTurn =
            if (index + 1 == turnOrder.size) turnOrder[0]
            else turnOrder[index + 1]
    }
    fun validateTurn(activeId : UUID){
        if(activeId != currentTurn) {
            throw InvalidTurnException()
        }
    }
    fun checkWinner() {
        val p1Defeated = player1.creatures.none { it.attributes.hp > 0 }
        val p2Defeated = player2.creatures.none { it.attributes.hp > 0 }
        winner = when{
            p1Defeated -> Winner.ENEMY
            p2Defeated -> Winner.PLAYER
            else -> Winner.NULL
        }
        if (winner != Winner.NULL) state = CombatState.FINISHED
    }
}