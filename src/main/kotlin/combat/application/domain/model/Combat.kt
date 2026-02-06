package com.romina.combat.application.domain.model

import com.romina.combat.application.domain.exception.EmptyArmyException
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
    val turnOrder: List<UUID>,
    var currentTurn : UUID,
    var state : CombatState,
    var winner : String?
){
    companion object {
        fun determineTurnOrderBySpeed(
            playerCreatures: List<Creature>,
            enemies: List<Creature>
        ): List<UUID> {

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

}