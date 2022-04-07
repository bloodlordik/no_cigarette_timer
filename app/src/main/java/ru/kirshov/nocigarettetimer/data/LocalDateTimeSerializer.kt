package ru.kirshov.nocigarettetimer.data

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.IntArraySerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.LocalDateTime

class LocalDateTimeSerializer:KSerializer<LocalDateTime> {
    private val delegateSerializer = IntArraySerializer()
    override fun deserialize(decoder: Decoder): LocalDateTime {
        val array = decoder.decodeSerializableValue(delegateSerializer)
        return LocalDateTime.of(array[0], array[1], array[2], array[3],array[4],array[5], array[6])
    }

    @OptIn(ExperimentalSerializationApi::class)
    override val descriptor: SerialDescriptor
        get() = SerialDescriptor("LocalDateTime", delegateSerializer.descriptor)

    override fun serialize(encoder: Encoder, value: LocalDateTime) {
        val array = intArrayOf(
            value.year,
            value.monthValue,
            value.dayOfMonth,
            value.hour,
            value.minute,
            value.second,
            value.nano
        )
        encoder.encodeSerializableValue(delegateSerializer, array)

    }
}