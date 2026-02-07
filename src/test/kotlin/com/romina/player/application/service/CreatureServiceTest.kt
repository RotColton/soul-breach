package com.romina.com.romina.player.application.service

import com.romina.player.application.domain.model.exception.PlayerIsNotTheOwnerException
import com.romina.player.application.domain.model.Creature
import com.romina.player.application.domain.model.CreatureClass
import com.romina.player.application.domain.model.CreatureClassFactory
import com.romina.player.application.ports.`in`.ApplyXPCommand
import com.romina.player.application.ports.out.CreaturePort
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import com.romina.player.application.service.CreatureService
import java.util.UUID
import kotlin.test.fail

class CreatureServiceTest {
    private val repository = mockk<CreaturePort>()
    private val service = CreatureService(repository)
    lateinit var command : ApplyXPCommand
    lateinit var creature : Creature
    lateinit var playerId : UUID
    lateinit var creatureId : UUID
    lateinit var creatureClass : CreatureClass

    @BeforeEach
    fun setUp() {
        playerId = UUID.randomUUID()
        creatureId = UUID.randomUUID()
        creatureClass = CreatureClassFactory.fromString("WARRIOR")

        creature = Creature(
            id = creatureId,
            owner = playerId,
            name = "Agumon",
            level = 1,
            xp = 0,
            creatureClass = creatureClass.className,
            attributes = creatureClass.defaultAttributes
        )
    }

    @Test
    fun `should throw exception if xp gained is less than 0`() = runTest {
        Assertions.assertThrows(IllegalArgumentException::class.java){
            command = ApplyXPCommand(
                playerId = UUID.randomUUID(),
                creatureId = UUID.randomUUID(),
                xpGained = -1
            )
        }
    }

    @Test
    fun `should throw exception if xp gained is 0`() = runTest {
        Assertions.assertThrows(IllegalArgumentException::class.java){
            command = ApplyXPCommand(
                playerId = UUID.randomUUID(),
                creatureId = UUID.randomUUID(),
                xpGained = 0
            )
        }
    }

    @Test
    fun `should throw exception if the player is not the owner of creature `() = runTest {

        coEvery { repository.findById(creatureId) } returns creature

        command = ApplyXPCommand(
            playerId = UUID.randomUUID(),
            creatureId = creatureId,
            xpGained = 100
        )

        try {
            service.applyXP(command)
            fail("Should have thrown PlayerIsNotTheOwnerException")
        } catch (e: PlayerIsNotTheOwnerException) {

        } catch (e: Exception) {
            fail("Expected PlayerIsNotTheOwnerException but caught ${e::class.simpleName}")
        }

    }

}