package ru.kirshov.nocigarettetimer.data.storage

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import ru.kirshov.nocigarettetimer.data.TimerGlobalState


class StorageManagerRepository(
    private val dataStore: DataStore<TimerGlobalState>

) : StorageManager {

    override fun loadState(): Flow<TimerGlobalState> = dataStore.data

    override suspend fun saveState(stateUpdater: Updater) {
        dataStore.updateData {
                stateUpdater.invoke(it)
        }
    }

    override suspend fun clean() {
        dataStore.updateData { TimerGlobalState() }
    }

}