package ru.kirshov.nocigarettetimer.data

import com.google.common.truth.Truth.assertThat
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.Test
import kotlin.random.Random
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration


class DurationSerializerTest {
    @Test fun randomSampleTest(){
        (2..99).forEach { _->
            val value = generateTestDurationSample()
            val stringValue = Json.encodeToString(value)
            val decodeValue = Json.decodeFromString<TestDurationSample>(stringValue)
            assertThat(value.duration).isEquivalentAccordingToCompareTo(decodeValue.duration)
        }
    }
    private fun generateTestDurationSample(): TestDurationSample {
        val range = Random.nextInt(0, 3)
        val value = Random.nextInt(3, 99)
        return when (range) {
            0 -> TestDurationSample(duration = value.toDuration(DurationUnit.DAYS))
            1 -> TestDurationSample(duration = value.toDuration(DurationUnit.MINUTES))
            2 -> TestDurationSample(duration = value.toDuration(DurationUnit.SECONDS))
            else -> {
                TestDurationSample(
                    duration = value.toDuration(DurationUnit.DAYS)
                        .plus(value.toDuration(DurationUnit.SECONDS))
                )
            }
        }
    }
}

@Serializable
private data class TestDurationSample(
    @Serializable(with = DurationSerializer::class) val duration: Duration
)