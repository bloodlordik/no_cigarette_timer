package ru.kirshov.nocigarettetimer.domain

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Before
import org.junit.Test
import ru.kirshov.nocigarettetimer.data.Dispatchers
import ru.kirshov.nocigarettetimer.data.TimerGlobalState
import ru.kirshov.nocigarettetimer.data.storage.StorageManager
import ru.kirshov.nocigarettetimer.data.storage.Updater
import java.time.LocalDateTime

class TimerStateInteractTest {
    private lateinit var testActions: TimerStateActions
    private lateinit var initialValue: TimerGlobalState

    @Before
    fun setUp() {
        initialValue = TimerGlobalState(
            lastStart = LocalDateTime.of(2022, 3, 1, 12, 23),
            totalTry = 0,
            isProcess = false
        )
        testActions = TimerStateInteract(
            storageManager = TestStorage(initial = initialValue),

            )
    }

    @Test
    fun firstTimeStart() = runBlocking {
        testActions.subscriptionState().test {
            val first = awaitItem()
            assertThat(first).isEqualTo(initialValue)
            cancelAndConsumeRemainingEvents()
        }

    }

    @Test
    fun startTimer() = runBlocking {

        testActions.subscriptionState().test {
            val first = awaitItem()
            assertThat(first).isEqualTo(initialValue)
            testActions.startTimer()
            val  second = awaitItem()
            assertThat(second.isProcess).isTrue()
            cancelAndConsumeRemainingEvents()
        }

    }

    @Test
    fun retryStartTimer() = runBlocking {
        testActions.startTimer()
        testActions.startTimer()
        testActions.subscriptionState().test {
            val first = awaitItem()
            assertThat(first.isProcess).isTrue()

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun stopTimer() = runBlocking {
        testActions.subscriptionState().test {
            val init = awaitItem()
            assertThat(init).isEqualTo(initialValue)
            testActions.startTimer()
            val start = awaitItem()
            assertThat(start.isProcess).isTrue()
            testActions.stopTimer()
            val stop = awaitItem()
            assertThat(stop.isProcess).isFalse()
            cancelAndConsumeRemainingEvents()
        }

    }

    @Test
    fun retryStopTimer() = runBlocking {
        testActions.subscriptionState().test {
            val init = awaitItem()
            assertThat(init).isEqualTo(initialValue)
            testActions.startTimer()
            val start = awaitItem()
            assertThat(start.isProcess).isTrue()
            testActions.stopTimer()
            val stop = awaitItem()
            assertThat(stop.isProcess).isFalse()
            testActions.startTimer()
            val ret = awaitItem()
            assertThat(ret.isProcess).isTrue()
            testActions.stopTimer()
            val retStop = awaitItem()
            assertThat(retStop.isProcess).isFalse()
            cancelAndConsumeRemainingEvents()
        }



    }
    @Test fun cleatTest() = runBlocking {
        testActions.subscriptionState().test {
            val first = awaitItem()
            assertThat(first).isEqualTo(initialValue)
            testActions.startTimer()
            val sec = awaitItem()
            assertThat(sec).isNotEqualTo(initialValue)
           testActions.clean()
            assertThat(awaitItem()).isEqualTo(initialValue)
            cancelAndConsumeRemainingEvents()
        }
    }
    private class TestStorage(private val initial: TimerGlobalState) : StorageManager {
        private val _state = MutableStateFlow(initial)
        override fun loadState(): Flow<TimerGlobalState> {
            return _state.asStateFlow()
        }

        override suspend fun saveState(stateUpdater: Updater) {
            val newState = stateUpdater.invoke(_state.value)
            if(_state.value != newState){
                _state.value = newState
            }

        }

        override suspend fun clean() {
            _state.value = initial
        }


    }

    private class TestDispatcher @OptIn(ExperimentalCoroutinesApi::class) constructor(
        override val io: CoroutineDispatcher = UnconfinedTestDispatcher(),
        override val main: CoroutineDispatcher = UnconfinedTestDispatcher()
    ) : Dispatchers
}