package ru.kirshov.nocigarettetimer.data

import kotlinx.coroutines.CoroutineDispatcher

interface Dispatchers {
    val io: CoroutineDispatcher
    val main: CoroutineDispatcher
}

fun Dispatchers():Dispatchers{
    return object :Dispatchers{
        override val io: CoroutineDispatcher
            get() = kotlinx.coroutines.Dispatchers.IO
        override val main: CoroutineDispatcher
            get() = kotlinx.coroutines.Dispatchers.Main

    }
}