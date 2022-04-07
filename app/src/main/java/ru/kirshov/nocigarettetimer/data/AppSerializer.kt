package ru.kirshov.nocigarettetimer.data

import androidx.datastore.core.Serializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

object AppSerializer : Serializer<TimerGlobalState> {
    override val defaultValue: TimerGlobalState
        get() = TimerGlobalState()

    override suspend fun readFrom(input: InputStream): TimerGlobalState {
        return try {
            Json.decodeFromString(
                deserializer = TimerGlobalState.serializer(),
                string = input.readBytes().decodeToString()
            )
        } catch (er: SerializationException) {
            er.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: TimerGlobalState, output: OutputStream) {
        output.write(
            Json.encodeToString(
                serializer = TimerGlobalState.serializer(),
                value = t
            ).encodeToByteArray()
        )
    }

}