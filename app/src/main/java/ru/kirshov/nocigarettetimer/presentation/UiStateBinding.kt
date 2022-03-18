package ru.kirshov.nocigarettetimer.presentation

import ru.kirshov.nocigarettetimer.data.TimerGlobalState

fun TimerGlobalState.toUi():UiState{
    return UiState(
        status = this.isProcess,
        last = this.totalTry,
        time = this.lastStart.toString()
    )
}