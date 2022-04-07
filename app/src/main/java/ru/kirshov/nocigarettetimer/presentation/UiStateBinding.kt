package ru.kirshov.nocigarettetimer.presentation

import ru.kirshov.nocigarettetimer.data.AppState
import ru.kirshov.nocigarettetimer.data.TimerGlobalState

fun AppState.binding():UiState{
    return when(this){
        is AppState.LoadingState -> UiState.Loading
        is AppState.EmptyState -> UiState.EmptyUi
        is AppState.FinishState -> UiState.IsFinished("Timer is finish")
        is AppState.ProcessState -> UiState.IsProcess(this.duration.toIsoString())
        is AppState.StopState -> UiState.IsStopped("IsTOP")
    }
}