package ru.kirshov.nocigarettetimer.presentation

import kotlin.time.Duration


fun durationToStringFormat(days: Long, hours: Int, minutes: Int, seconds: Int, nanoseconds: Int):String{
    return "$days: $hours: $minutes: $seconds"
}

