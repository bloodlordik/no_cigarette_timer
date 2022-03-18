package ru.kirshov.nocigarettetimer.data.storage

import kotlinx.coroutines.flow.Flow
import ru.kirshov.nocigarettetimer.data.TimerGlobalState
import java.time.LocalDateTime

interface StorageManager {
    fun loadState():Flow<TimerGlobalState>
    suspend fun saveState(stateUpdater:Updater)
    suspend fun clean()
}
typealias Updater = (TimerGlobalState)->TimerGlobalState