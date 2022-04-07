package ru.kirshov.nocigarettetimer.domain

import android.os.CountDownTimer
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

private const val IntervalError = "Interval is more that duration"
private const val IntervalZero = "Interval mast be more that zero"
private const val DurationInfinite = "Duration is infinite"
private const val IntervalInfinite = "Interval is infinite"

class DownCountTimer(
    duration: Duration = 45.toDuration(DurationUnit.DAYS),
    interval: Duration = 1.toDuration(DurationUnit.SECONDS),
    private val onTick:(duration:Duration)->Unit = {},
    private val onFinish:()->Unit = {},
    autoStart:Boolean = false
) : CountDownTimer(duration.toLong(DurationUnit.MILLISECONDS), interval.toLong(DurationUnit.MILLISECONDS)) {
    init {
        require(duration>interval){ IntervalError}
        require(interval > Duration.ZERO){ IntervalZero}
        require(!duration.isInfinite()){ DurationInfinite}
        require(!interval.isInfinite()){ IntervalInfinite}
        if (autoStart){
            this.start()
        }
    }
    override fun onTick(p0: Long) = onTick.invoke(p0.toDuration(DurationUnit.MILLISECONDS))

    override fun onFinish() = onFinish.invoke()

}



