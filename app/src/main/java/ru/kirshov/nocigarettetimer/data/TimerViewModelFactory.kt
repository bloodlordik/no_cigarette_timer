package ru.kirshov.nocigarettetimer.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.kirshov.nocigarettetimer.data.storage.StorageManagerRepository
import ru.kirshov.nocigarettetimer.domain.MainViewModel
import ru.kirshov.nocigarettetimer.domain.TimerStateInteract


private const val PREFERENCE_FILE = "preference_file"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = PREFERENCE_FILE
)

class TimerViewModelFactory(context: Context) : ViewModelProvider.NewInstanceFactory() {
    private val dispatchers = Dispatchers()
    private val timerStateInteract = TimerStateInteract(

        storageManager = StorageManagerRepository(
            dataStore = context.dataStore,
            initial = TimerGlobalState.newInstance()
        )
    )

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        return MainViewModel(
             timerStateActions = timerStateInteract
        ) as T
    }
}