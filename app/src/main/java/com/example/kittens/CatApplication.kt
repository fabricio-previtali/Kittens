package com.example.kittens

import android.app.Application
import com.example.kittens.data.AppContainer
import com.example.kittens.network.DefaultAppContainer

class CatApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}
