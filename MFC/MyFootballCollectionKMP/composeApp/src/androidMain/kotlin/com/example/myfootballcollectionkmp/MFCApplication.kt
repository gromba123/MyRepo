package com.example.myfootballcollectionkmp

import android.app.Application
import com.example.myfootballcollectionkmp.di.initKoin
import org.koin.android.ext.koin.androidContext

class MFCApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@MFCApplication)
        }
    }
}