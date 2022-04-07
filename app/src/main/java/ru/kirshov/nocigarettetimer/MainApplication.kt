package ru.kirshov.nocigarettetimer

import android.app.Application
import ru.kirshov.nocigarettetimer.BuildConfig.DEBUG


class MainApplication:Application(){
    override fun onCreate() {
        if(DEBUG){
            strictMode()
        }
        super.onCreate()
    }
    private fun strictMode(){

    }
}