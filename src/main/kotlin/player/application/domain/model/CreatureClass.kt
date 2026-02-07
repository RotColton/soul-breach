package com.romina.player.application.domain.model

enum class CreatureClassName{
    WARRIOR, DEFENDER, ENCHANTER
}

sealed class CreatureClass {
    abstract val defaultAttributes: ElementalsAttributes
    abstract val className : CreatureClassName
}
//TODO: refactor magic numbers
object Warrior : CreatureClass() {
    override val className = CreatureClassName.WARRIOR
    override val defaultAttributes = ElementalsAttributes(10, 3, 25)
}

object Defender : CreatureClass() {
    override val className  = CreatureClassName.DEFENDER
    override val defaultAttributes = ElementalsAttributes(20, 2, 25)
}

object Enchanter : CreatureClass() {
    override val className  = CreatureClassName.ENCHANTER
    override val defaultAttributes = ElementalsAttributes(10, 4, 15)
}

object CreatureClassFactory {
    private val classMap: Map<String, CreatureClass> =
        CreatureClass::class.sealedSubclasses
            .mapNotNull { it.objectInstance }
            .associateBy { it.className.name }

    fun fromString(className: String): CreatureClass {
        return classMap[className.uppercase()]
            ?: throw IllegalArgumentException("Class '$className' not found. Available: ${classMap.keys}")
    }
}