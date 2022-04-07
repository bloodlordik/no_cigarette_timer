package ru.kirshov.nocigarettetimer.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.kirshov.nocigarettetimer.data.AppState
import ru.kirshov.nocigarettetimer.data.Status
import ru.kirshov.nocigarettetimer.data.TimerGlobalState
import ru.kirshov.nocigarettetimer.data.storage.StorageManager
import java.time.LocalDateTime

class TimerStateInteract(
    private val storageManager: StorageManager
) : TimerStateActions {

    override suspend fun startTimer() {
        storageManager.saveState { oldValue ->
            val status = oldValue.status
            if (status == Status.STOP || status==Status.EMPTY){
                oldValue.copy(
                    lastStart = LocalDateTime.now(),
                    totalTry = oldValue.totalTry.inc(),
                    status = Status.PROCESS
                )
            }else{
                oldValue.copy()
            }

        }

    }

    override suspend fun stopTimer() {
        storageManager.saveState { oldValue ->
            val status = oldValue.status
            if (status == Status.STOP || status == Status.FINISH || status==Status.EMPTY){
                oldValue.copy()
            } else{
                val lastTime:LocalDateTime = checkNotNull(oldValue.lastStart)
                val duration = LocalDateTime.now().durationFrom(lastTime)
                oldValue.copy(
                    lastStart = null,
                    maxDuration = if (oldValue.maxDuration > duration) oldValue.maxDuration else duration,
                    status = Status.STOP
                )
            }

        }

    }

    override suspend fun clean() {
        storageManager.saveState {
            TimerGlobalState()
        }
    }

    override fun subscriptionState(): Flow<AppState> = storageManager.loadState().map { value ->
        when (value.status) {
            Status.EMPTY -> AppState.EmptyState
            Status.STOP -> AppState.StopState(
                total = value.totalTry,
                maxDuration = value.maxDuration
            )
            Status.PROCESS -> {
                val duration = LocalDateTime.now().durationFrom(value.lastStart!!)
                AppState.ProcessState(duration = duration, total = value.totalTry)
            }
            Status.FINISH -> AppState.FinishState

        }
    }


}