package ru.kirshov.nocigarettetimer.domain

import kotlinx.coroutines.flow.Flow
import ru.kirshov.nocigarettetimer.data.TimerGlobalState
import ru.kirshov.nocigarettetimer.data.storage.StorageManager
import java.time.LocalDateTime

class TimerStateInteract(
    private val storageManager: StorageManager
) : TimerStateActions {

    override suspend fun startTimer() {
        storageManager.saveState { oldState ->
            if (oldState.isProcess){
                oldState
            }else{
                oldState.copy(
                    lastStart = LocalDateTime.now(),
                    isProcess = true,
                    totalTry = oldState.totalTry.inc()
                )
            }
        }

    }

    override suspend fun stopTimer() {
        storageManager.saveState { oldState->
            oldState.copy(isProcess = false)
        }

    }

    override suspend fun clean() {
       storageManager.clean()
    }

    override fun subscriptionState(): Flow<TimerGlobalState> = storageManager.loadState()

}