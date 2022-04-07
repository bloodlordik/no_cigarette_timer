package ru.kirshov.nocigarettetimer.domain

import java.time.LocalDateTime
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration



private fun durationBetweenTimes(oldTime: LocalDateTime, freshTime: LocalDateTime): Duration {
    require(freshTime.isAfter(oldTime))
    val epochSecond = java.time.Duration.between(oldTime, freshTime).seconds
    return epochSecond.toDuration(DurationUnit.SECONDS)
}

fun LocalDateTime.durationFrom(oldTime: LocalDateTime): Duration {
    return durationBetweenTimes(oldTime = oldTime, freshTime = this)
}