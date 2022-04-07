package ru.kirshov.nocigarettetimer.data

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class DurationSerializer: KSerializer<Duration> {

    override fun deserialize(decoder: Decoder): Duration {
        val intDuration = decoder.decodeInt()
        return intDuration.toDuration(DurationUnit.SECONDS)
    }


    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("Duration", PrimitiveKind.INT)

    override fun serialize(encoder: Encoder, value: Duration) {
        val intDuration = value.toInt(DurationUnit.SECONDS)
        encoder.encodeInt(intDuration)
    }

}