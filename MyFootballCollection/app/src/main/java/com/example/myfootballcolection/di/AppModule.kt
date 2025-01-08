package com.example.myfootballcolection.di

import android.app.Application
import androidx.room.Room
import com.example.myfootballcolection.data.dataSource.GamesCollectionDao
import com.example.myfootballcolection.data.dataSource.MFCCollectionDatabase
import com.example.myfootballcolection.data.firebase.FirebaseEmailAuthenticatorImpl
import com.example.myfootballcolection.data.repository.UserRepositoryImpl
import com.example.myfootballcolection.domain.repository.UserRepository
import com.example.myfootballcolection.ui.screens.games.GamesScreenViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

private fun getMFCDao(context: Application): GamesCollectionDao {
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
    single { FirebaseEmailAuthenticatorImpl() }
    single { UserRepositoryImpl(get(), get()) } bind UserRepository::class

    viewModelOf(::GamesScreenViewModel)
}