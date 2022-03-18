package ru.kirshov.nocigarettetimer.data.storage

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import kotlinx.coroutines.flow.*
import ru.kirshov.nocigarettetimer.data.TimerGlobalState
import java.io.IOException
import java.time.LocalDateTime


class StorageManagerRepository(
    private val dataStore: DataStore<Preferences>,
    private val initial: TimerGlobalState
) : StorageManager {
    override  fun loadState(): Flow<TimerGlobalState> = dataStore.data.catch { exp ->
        if (exp is IOException) {
            emit(emptyPreferences())
        } else {
            throw exp
        }
    }.map { preference ->
        val time = preference[PreferenceKeys.LAST_START] ?: initial.lastStart.toString()
        val isProcess = preference[PreferenceKeys.IS_PROCESS] ?: initial.isProcess
        val total = preference[PreferenceKeys.TOTAL_TRY] ?: initial.totalTry
        TimerGlobalState(
            lastStart = LocalDateTime.parse(time),
            isProcess = isProcess,
            totalTry = total
        )
    }

    override suspend fun saveState(stateUpdater: Updater) {
        val oldState = loadState().first()
        val newState = stateUpdater.invoke(oldState)
        if (oldState == newState) return
        dataStore.edit { preference->
            preference[PreferenceKeys.LAST_START] = newState.lastStart.toString()
            preference[PreferenceKeys.IS_PROCESS] = newState.isProcess
            preference[PreferenceKeys.TOTAL_TRY] = newState.totalTry
        }
    }

    override suspend fun clean() {
        dataStore.edit { it.clear() }
    }


    private object PreferenceKeys {
        val IS_PROCESS = booleanPreferencesKey("is_process")
        val TOTAL_TRY = intPreferencesKey("total_try")
        val LAST_START = stringPreferencesKey("last_start")
    }

}