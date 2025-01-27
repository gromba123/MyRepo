package com.example.myfootballcollection

import android.app.Application
import com.example.myfootballcollection.di.appModule
import com.example.myfootballcollection.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MFCApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MFCApplication)
            //androidLogger()

            modules(networkModule, appModule)
        }
    }
}