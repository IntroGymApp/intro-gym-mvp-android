package ru.lonelywh1te.introgymapp

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import ru.lonelywh1te.introgymapp.di.appModule
import ru.lonelywh1te.introgymapp.di.dataModule
import ru.lonelywh1te.introgymapp.di.domainModule

class App: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            androidLogger(Level.DEBUG)
            modules(listOf(appModule, domainModule, dataModule))
        }

    }
}