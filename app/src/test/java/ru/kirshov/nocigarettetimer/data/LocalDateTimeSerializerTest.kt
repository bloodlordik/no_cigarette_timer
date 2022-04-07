package ru.kirshov.nocigarettetimer.data


import com.google.common.truth.Truth.assertThat
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.Test
import java.time.LocalDateTime
import kotlin.random.Random

class LocalDateTimeSerializerTest {

    @Test
    fun simplyTest() {
        val test = generateRandomSampleClass()
        val encodeString = Json.encodeToString(test)
        val decodeClass = Json.decodeFromString<TestSampleDate>(encodeString)
        assertThat(test.time.isEqual(decodeClass.time)).isTrue()


    }
    @Test
    fun randomTest(){
        (0 until 100).forEach { _ ->
            val test = generateRandomSampleClass()
            val encodeString = Json.encodeToString(test)
            val decodeClass = Json.decodeFromString<TestSampleDate>(encodeString)
            assertThat(test.time.isEqual(decodeClass.time)).isTrue()
        }
    }

    private fun generateRandomSampleClass(): TestSampleDate {
        val randomLocalDateTime = LocalDateTime.of(
            Random.nextInt(2000, 2030),
            Random.nextInt(1, 13),
            Random.nextInt(1, 29),
            Random.nextInt(1, 24),
            Random.nextInt(1, 60),
            Random.nextInt(1, 60)
        )
        return TestSampleDate(time = randomLocalDateTime)
    }


}
@Serializable
private data class TestSampleDate(
    @Serializable(with = LocalDateTimeSerializer::class) val time: LocalDateTime
)