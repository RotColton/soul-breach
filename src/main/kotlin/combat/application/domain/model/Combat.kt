package com.romina.combat.application.domain.model

import com.romina.combat.application.domain.exception.EmptyArmyException
import com.romina.combat.application.domain.exception.InvalidTurnException
import com.romina.player.application.domain.model.Creature
import com.romina.player.application.domain.model.Player
import java.util.UUID
enum class CombatState{
    IN_PROGRESS,
    FINISHED
}

data class Combat(
    val id : UUID,
    val player1 : Player,
    val player2 : Player,
    val turnOrder: MutableList<UUID>,
    var currentTurn : UUID,
    var state : CombatState,
    var winner : String?
){
    companion object {
        fun determineTurnOrderBySpeed(
            playerCreatures: List<Creature>,
            enemies: List<Creature>
        ): MutableList<UUID> {

            if (playerCreatures.isEmpty()){
                throw EmptyArmyException("Cannot start a battle without creatures in your team")
            }

            val sortedPlayers = playerCreatures.sortedByDescending { it.attributes.speed }
            val sortedEnemies = enemies.sortedByDescending { it.attributes.speed }

            val turnOrder = mutableListOf<UUID>()
            val maxSize = maxOf(sortedPlayers.size, sortedEnemies.size)

            for (i in 0 until maxSize) {
                if (i < sortedPlayers.size) {
                    turnOrder.add(sortedPlayers[i].id)
                }
                if (i < sortedEnemies.size) {
                    turnOrder.add(sortedEnemies[i].id)
                }
            }

            return turnOrder
        }
    }

    fun attack(targetId : UUID, activeId : UUID){
        val active = findCreature(activeId)
            ?: throw IllegalArgumentException("Attacker $activeId} not found")
        val target = findCreature(targetId)
            ?: throw IllegalArgumentException("Target ${targetId} not found")

        target.receiveDamage(active.attributes.attack)
        if(target.attributes.hp <= 0) killCreature(target)
    }

    fun findCreature(id : UUID) : Creature? = allCreatures().find { creature ->  creature.id == id}

    fun allCreatures(): List<Creature> = player1.creatures + player2.creatures

    fun killCreature(creature: Creature){
        turnOrder.remove(creature.id)
    }
    fun nextTurn(){
        val index = turnOrder.indexOf(currentTurn)
        currentTurn = turnOrder[index+1]
    }
    fun validateTurn(activeId : UUID){
        if(activeId != currentTurn) {
            throw InvalidTurnException("It is not creature ${activeId}'s turn.")
        }
    }

}