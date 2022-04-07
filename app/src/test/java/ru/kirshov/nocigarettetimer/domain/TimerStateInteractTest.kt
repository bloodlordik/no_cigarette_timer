package ru.kirshov.nocigarettetimer.domain

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import ru.kirshov.nocigarettetimer.data.AppSerializer
import ru.kirshov.nocigarettetimer.data.AppState
import ru.kirshov.nocigarettetimer.data.TimerGlobalState
import ru.kirshov.nocigarettetimer.data.storage.StorageManager
import ru.kirshov.nocigarettetimer.data.storage.Updater


class TimerStateInteractTest {
    private lateinit var actions: TimerStateActions

    @Before
    fun setUp() {
        actions = TimerStateInteract(MockStorage())
    }

    @Test
    fun firstLoad() = runBlocking {
        actions.subscriptionState().test {
            val first = awaitItem()
            assertThat(first).isEqualTo(AppState.EmptyState)
            cancelAndConsumeRemainingEvents()
        }
    }
    @Test
    fun firstStart() = runBlocking {

        actions.subscriptionState().test {
            val value = awaitItem()
            assertThat(value).isInstanceOf(AppState.EmptyState::class.java)
            actions.startTimer()
            val start = awaitItem()
            assertThat(start).isInstanceOf(AppState.ProcessState::class.java)
            cancelAndConsumeRemainingEvents()
        }
    }
    @Test
    fun firstStop() = runBlocking {
        actions.subscriptionState().test {
            val first = awaitItem()
            assertThat(first).isInstanceOf(AppState.EmptyState::class.java)
            actions.startTimer()
            val start = awaitItem()
            assertThat(start).isInstanceOf(AppState.ProcessState::class.java)
            actions.stopTimer()
            val stop = awaitItem()
            assertThat(stop).isInstanceOf(AppState.StopState::class.java)
            cancelAndConsumeRemainingEvents()
        }
    }

}

private class MockStorage : StorageManager {

    private val value = MutableStateFlow<TimerGlobalState>(TimerGlobalState())

    override fun loadState(): Flow<TimerGlobalState> = value.asStateFlow()

    override suspend fun saveState(stateUpdater: Updater) {
        val newValue = stateUpdater(value.value)
        value.value = newValue
    }

    override suspend fun clean() {
        value.tryEmit(TimerGlobalState())
    }

}