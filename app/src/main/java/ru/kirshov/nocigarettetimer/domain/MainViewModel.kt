package ru.kirshov.nocigarettetimer.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.kirshov.nocigarettetimer.data.AppState
import ru.kirshov.nocigarettetimer.presentation.UiState
import ru.kirshov.nocigarettetimer.presentation.binding

class MainViewModel(
    private val timerStateActions: TimerStateActions
) : ViewModel() {
    init {
        viewModelScope.launch {
            timerStateActions.subscriptionState().collect{
                _state.emit(it)
            }
        }
    }
    private val _state = MutableStateFlow<AppState>(AppState.LoadingState)
    fun update():Flow<UiState> = _state.asStateFlow().map { it.binding() }
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