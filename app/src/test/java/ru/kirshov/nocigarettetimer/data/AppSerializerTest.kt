package ru.kirshov.nocigarettetimer.data

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.time.LocalDateTime
import kotlin.random.Random
import kotlin.time.DurationUnit
import kotlin.time.toDuration


class AppSerializerTest{
    @Test
    fun defaultTest() = runBlocking {
        val expectValue = TimerGlobalState()
        val actualValue = AppSerializer.defaultValue
        assertThat(expectValue).isEqualTo(actualValue)

    }
    @Test
    fun readTest() = runBlocking {
        (0..100).forEach { _->
            val outStream = ByteArrayOutputStream()
            val originData = generateSampleData()
            AppSerializer.writeTo(originData, outStream)
            val inputStream =  ByteArrayInputStream(outStream.toByteArray())
            val expectData = AppSerializer.readFrom(inputStream)
            assertThat(originData).isEqualTo(expectData)
            outStream.close()
            inputStream.close()
        }
    }


    private fun generateSampleData():TimerGlobalState{
        return TimerGlobalState(
            lastStart = if (Random.nextBoolean()) LocalDateTime.now() else null,
            totalTry = Random.nextInt(0, 10),
            maxDuration = Random.nextInt(2, 1000).toDuration(DurationUnit.SECONDS),
            status = when(Random.nextInt(0, 4)){
                0 -> Status.EMPTY
                1->Status.STOP
                2->Status.FINISH
                3->Status.PROCESS
                else -> Status.EMPTY
            }
        )
    }
}