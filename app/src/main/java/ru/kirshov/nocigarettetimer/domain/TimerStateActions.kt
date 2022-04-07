package ru.kirshov.nocigarettetimer.domain

import kotlinx.coroutines.flow.Flow
import ru.kirshov.nocigarettetimer.data.AppState
import ru.kirshov.nocigarettetimer.data.TimerGlobalState

interface TimerStateActions {
    suspend fun startTimer()
    suspend fun stopTimer()
    suspend fun clean()
    fun subscriptionState():Flow<AppState>
}