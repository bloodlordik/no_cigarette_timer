package ru.kirshov.nocigarettetimer.domain


import org.junit.Test
import java.time.LocalDateTime
import com.google.common.truth.Truth.assertThat
import kotlin.random.Random
import kotlin.time.DurationUnit

class DurationDataTest{
    private val oldTime = LocalDateTime.of(2022,4,4,11,30,20)

    @Test fun simpleSecondTest(){
        val diffSecond = 5
        val freshTime = oldTime.plusSeconds(diffSecond.toLong())
        val result = freshTime.durationFrom(oldTime)
        assertThat(result.toInt(DurationUnit.SECONDS)).isEqualTo(diffSecond)
    }
    @Test fun simpleMinuteTest(){
        val diffMinutes = 5
        val freshTime = oldTime.plusMinutes(diffMinutes.toLong())
        val result = freshTime.durationFrom(oldTime)
        assertThat(result.toInt(DurationUnit.MINUTES)).isEqualTo(diffMinutes)
    }
    @Test fun simpleDaysTest(){
        val diffDays = Random.nextLong(0, 25)
        val freshTime = oldTime.plusDays(diffDays)
        val result = freshTime.durationFrom(oldTime)
        assertThat(result.toInt(DurationUnit.DAYS)).isEqualTo(diffDays)
    }


}