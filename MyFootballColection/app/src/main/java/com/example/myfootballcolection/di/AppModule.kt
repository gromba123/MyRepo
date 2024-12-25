package com.example.myfootballcolection.di

import android.app.Application
import androidx.room.Room
import com.example.myfootballcolection.data.GamesCollectionDataAccess
import com.example.myfootballcolection.data.MFCCollectionDatabase
import com.example.myfootballcolection.ui.screens.games.GamesScreenViewModel
import org.koin.core.module.dsl.viewModelOf
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

    viewModelOf(::GamesScreenViewModel)
}