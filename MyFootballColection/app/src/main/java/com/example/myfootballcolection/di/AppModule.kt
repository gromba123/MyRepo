package com.example.myfootballcolection.di

import android.app.Application
import androidx.room.Room
import com.example.myfootballcolection.data.GamesCollectionDataAccess
import com.example.myfootballcolection.data.MFCCollectionDatabase
import org.koin.dsl.module

private fun getMFCDao(context: Application): GamesCollectionDataAccess {
    return Room
        .databaseBuilder(
            context,
            MFCCollectionDatabase::class.java,
            "MFCCollectionDatabase"
        )
        .build()
        .getMFCCollectionDao()
}

val appModule = module {
    single { getMFCDao(get()) }
}