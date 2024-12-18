package com.example.myfootballcolection

import android.app.Application
import com.example.myfootballcolection.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MFCApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MFCApplication)
            //androidLogger()

            modules(appModule)
        }
    }
}