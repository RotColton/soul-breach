package com.romina.player.application.domain.model

sealed class CreatureClass {
    abstract fun initDefaultAttributesValues() : ElementalsAttributes
    abstract fun getClassName() : String
}

object Warrior : CreatureClass() {
    override fun getClassName() = "Warrior"
    override fun initDefaultAttributesValues() = ElementalsAttributes(10, 30, 25)
}

object Defender : CreatureClass() {
    override fun getClassName() = "Defender"
    override fun initDefaultAttributesValues() = ElementalsAttributes(20, 25, 25)
}

object Enchanter : CreatureClass() {
    override fun getClassName() = "Enchanter"
    override fun initDefaultAttributesValues() = ElementalsAttributes(10, 40, 15)
}

object CreatureClassFactory {
    private val classMap: Map<String, CreatureClass> =
        CreatureClass::class.sealedSubclasses
            .mapNotNull { it.objectInstance }
            .associateBy { it.getClassName().uppercase() }

    fun fromString(className: String): CreatureClass {
        return classMap[className.uppercase()]
            ?: throw IllegalArgumentException("Class '$className' not found. Available: ${classMap.keys}")
    }
}