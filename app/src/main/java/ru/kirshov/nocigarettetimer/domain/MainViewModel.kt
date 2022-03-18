package ru.kirshov.nocigarettetimer.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.kirshov.nocigarettetimer.presentation.UiState
import ru.kirshov.nocigarettetimer.presentation.toUi

class MainViewModel(
    private val timerStateActions: TimerStateActions
) : ViewModel() {
    fun update(): Flow<UiState> = timerStateActions.subscriptionState().map { it.toUi() }
    fun startTimer() {
        viewModelScope.launch {
            timerStateActions.startTimer()
        }
    }

    fun stopTimer() {
        viewModelScope.launch {
            timerStateActions.stopTimer()
        }
    }
    fun clean(){
        viewModelScope.launch {
            timerStateActions.clean()
        }
    }
}