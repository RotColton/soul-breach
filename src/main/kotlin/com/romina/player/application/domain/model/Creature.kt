package com.romina.player.application.domain.model

import java.util.UUID
import kotlin.math.pow
import kotlin.math.sqrt

data class Creature (
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val owner: UUID,
    val creatureClass: CreatureClassName,
    var level: Int,
    var xp: Int,
    var attributes: ElementalsAttributes
){
    companion object {
        private const val DIFFICULTY_MODIFIER = 0.1
    }

    fun applyXP(amount : Int ){
        require(amount >= 0) {"Amount must be non-negative. You set amount to $amount."}

        xp += amount
        val targetLevel = (DIFFICULTY_MODIFIER *
                sqrt(xp.toDouble())).toInt() + 1

        while (level < targetLevel) {
            levelUp()
        }
    }

    private fun levelUp() {
        level++

        attributes = attributes.copy(
            hp = calculateStatIncrease(attributes.hp, "hp"),
            attack = calculateStatIncrease(attributes.attack, "attack"),
            speed = calculateStatIncrease(attributes.speed, "speed")
        )
    }

    private fun calculateStatIncrease(currentValue: Int, statType: String): Int {
        val growthRate = when (statType) {
            "hp" -> 1.5
            "attack" -> 1.2
            "speed" -> 0.5
            else -> 1.0
        }

        val increase = (growthRate * level.toDouble().pow(1.1)).toInt().coerceAtLeast(1)

        return currentValue + increase
    }

    fun receiveDamage(damagePoint: Int){
        if(damagePoint >= attributes.hp) this.attributes.hp = 0
        else this.attributes.hp -= damagePoint
    }

    fun isDead(): Boolean = attributes.hp <= 0
}