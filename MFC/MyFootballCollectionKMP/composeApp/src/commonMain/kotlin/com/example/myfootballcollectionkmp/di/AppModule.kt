package com.example.myfootballcollectionkmp.di

import android.app.Application
import androidx.room.Room
import com.example.myfootballcollectionkmp.BaseViewModel
import com.example.myfootballcollectionkmp.data.api.FootballApiImpl
import com.example.myfootballcollectionkmp.data.createHttpClient
import com.example.myfootballcollectionkmp.data.dataSource.GamesCollectionDao
import com.example.myfootballcollectionkmp.data.dataSource.MFCCollectionDatabase
import com.example.myfootballcollectionkmp.data.firebase.FirebaseEmailAuthenticatorImpl
import com.example.myfootballcollectionkmp.data.repository.FootballRepositoryImpl
import com.example.myfootballcollectionkmp.data.repository.UserRepositoryImpl
import com.example.myfootballcollectionkmp.domain.data.api.FootballApi
import com.example.myfootballcollectionkmp.domain.data.firebase.FirebaseEmailAuthenticator
import com.example.myfootballcollectionkmp.domain.data.repository.FootballRepository
import com.example.myfootballcollectionkmp.domain.data.repository.UserRepository
import com.example.myfootballcollectionkmp.domain.useCase.football.SearchTeamsUseCase
import com.example.myfootballcollectionkmp.domain.useCase.user.GetCurrentUser
import com.example.myfootballcollectionkmp.domain.useCase.user.IsFirebaseLoggedIn
import com.example.myfootballcollectionkmp.domain.useCase.user.LoginUser
import com.example.myfootballcollectionkmp.domain.useCase.user.RegisterUser
import com.example.myfootballcollectionkmp.domain.useCase.user.UserUseCases
import com.example.myfootballcollectionkmp.ui.screens.auth.create.CreateScreenViewModel
import com.example.myfootballcollectionkmp.ui.screens.auth.login.LoginScreenViewModel
import com.example.myfootballcollectionkmp.ui.screens.auth.register.RegisterScreenViewModel
import com.example.myfootballcollectionkmp.ui.screens.games.GamesScreenViewModel
import org.koin.core.module.dsl.singleOf
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
    singleOf(::FirebaseEmailAuthenticatorImpl) bind FirebaseEmailAuthenticator::class
    singleOf(::UserRepositoryImpl) bind UserRepository::class

    single { createHttpClient(get()) }
    singleOf(::FootballApiImpl) bind FootballApi::class
    singleOf(::FootballRepositoryImpl) bind FootballRepository::class

    singleOf(::LoginUser)
    singleOf(::RegisterUser)
    singleOf(::GetCurrentUser)
    singleOf(::IsFirebaseLoggedIn)
    singleOf(::UserUseCases)

    singleOf(::SearchTeamsUseCase)

    viewModelOf(::BaseViewModel)
    viewModelOf(::LoginScreenViewModel)
    viewModelOf(::RegisterScreenViewModel)
    viewModelOf(::CreateScreenViewModel)
    viewModelOf(::GamesScreenViewModel)
}
