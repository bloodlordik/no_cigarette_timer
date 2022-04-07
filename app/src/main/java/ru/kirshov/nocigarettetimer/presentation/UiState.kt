package ru.kirshov.nocigarettetimer.presentation

sealed class UiState() {
    object Loading:UiState()
    object EmptyUi:UiState()
    data class IsProcess(val durationText:String):UiState()
    data class IsStopped(val stoppedText:String):UiState()
    data class IsFinished(val finishedText:String):UiState()
}
