package ru.kirshov.nocigarettetimer.data

import kotlin.time.Duration

sealed class AppState{
    object EmptyState:AppState()
    object FinishState:AppState()
    object LoadingState:AppState()
    data class ProcessState(val duration: Duration, val total:Int):AppState()
    data class StopState(val total: Int, val maxDuration:Duration):AppState()

}



