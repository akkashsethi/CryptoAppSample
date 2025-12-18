package com.example.cmcinterviewtask

import android.app.Application

class MainApplication : Application(){


    lateinit var container: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        container = AppContainer()
    }

}