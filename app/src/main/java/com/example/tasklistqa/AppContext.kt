package com.example.tasklistqa

import android.app.Application
import com.example.tasklistqa.di.appModule
import com.example.tasklistqa.di.dataModule
import com.example.tasklistqa.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin


class AppContext : Application() {
    companion object {
        lateinit var instance: AppContext
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        startKoin {
            androidContext(this@AppContext)
            modules(appModule, dataModule, networkModule)
        }
    }
}