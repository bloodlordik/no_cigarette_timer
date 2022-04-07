package ru.kirshov.nocigarettetimer.data

import android.content.Context
import androidx.datastore.dataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.kirshov.nocigarettetimer.data.storage.StorageManagerRepository
import ru.kirshov.nocigarettetimer.domain.MainViewModel
import ru.kirshov.nocigarettetimer.domain.TimerStateInteract


private const val PREFERENCE_FILE = "preference_file.json"
private val Context.dataStore by dataStore(
    fileName = PREFERENCE_FILE,
    serializer = AppSerializer
)

class TimerViewModelFactory(context: Context) : ViewModelProvider.NewInstanceFactory() {
        private val timerStateInteract = TimerStateInteract(

        storageManager = StorageManagerRepository(
            dataStore = context.dataStore,
        )
    )

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (!modelClass.isAssignableFrom(MainViewModel::class.java)) throw IllegalArgumentException()
        return MainViewModel(
            timerStateActions = timerStateInteract
        ) as T
    }
}