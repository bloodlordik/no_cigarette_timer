package ru.kirshov.nocigarettetimer.data.storage

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import ru.kirshov.nocigarettetimer.data.TimerGlobalState
import java.time.LocalDateTime
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.*

private val Context.dataStore by preferencesDataStore(
    name = "test_file"
)

class StorageManagerRepositoryTest {

    private lateinit var testRepository: StorageManager
    private lateinit var dataStore: DataStore<Preferences>

    private val initialTestTime = LocalDateTime.of(2022, 2, 7, 12, 12, 20, 56)
    private val initialState = TimerGlobalState(
        totalTry = 0,
        isProcess = false,
        lastStart = initialTestTime
    )

    @Before
    fun setUp() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        dataStore = appContext.dataStore
        testRepository = StorageManagerRepository(
            dataStore = dataStore,
            initial = initialState
        )
    }


    @Test
    fun loadEmptyState() = runBlocking() {
        val first = testRepository.loadState().first()
        assertThat(first).isEqualTo(initialState)
    }

    @Test
    fun setEqualTest() = runBlocking {
        val testVal =
            TimerGlobalState(lastStart = LocalDateTime.now(), isProcess = false, totalTry = 3)
        val twoList = async {
            testRepository.saveState { testVal }
            testRepository.loadState().take(2).toList()

        }.join()

        assertThat(testVal).isEqualTo(testVal)


    }

    @Test
    fun setTime() = runBlocking {

    }

    @Test
    fun setStatus() = runBlocking {

    }

    @Test
    fun setTotal() = runBlocking {

    }

    @After
    fun clean() {
        runBlocking {
            dataStore.edit {
                it.clear()
            }
        }
    }
}