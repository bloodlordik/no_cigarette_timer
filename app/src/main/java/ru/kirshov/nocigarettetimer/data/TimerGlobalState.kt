package ru.kirshov.nocigarettetimer.data

import java.time.LocalDateTime

data class TimerGlobalState(
    val lastStart:LocalDateTime,
    val isProcess:Boolean,
    val totalTry:Int){
    companion object{
        fun newInstance():TimerGlobalState{
            return TimerGlobalState(
                lastStart = LocalDateTime.now(),
                isProcess = true,
                totalTry = 0
            )
        }

    }
}
