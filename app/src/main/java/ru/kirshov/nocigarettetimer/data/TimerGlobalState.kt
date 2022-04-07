package ru.kirshov.nocigarettetimer.data


import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import kotlin.time.Duration


@Serializable
data class TimerGlobalState(
    @Serializable(with = LocalDateTimeSerializer::class)
    val lastStart:LocalDateTime?=null,
    val totalTry:Int=0,
    @Serializable(with = DurationSerializer::class)
    val maxDuration: Duration = Duration.ZERO,
    val status: Status=Status.EMPTY
)

enum class Status{
    FINISH, EMPTY, STOP, PROCESS
}

