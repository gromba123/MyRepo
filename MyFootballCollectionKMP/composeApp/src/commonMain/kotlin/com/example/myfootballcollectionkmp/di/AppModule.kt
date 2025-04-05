package com.example.myfootballcollectionkmp.di

import android.app.Application
import androidx.room.Room
import com.example.myfootballcollectionkmp.BaseViewModel
import com.example.myfootballcollectionkmp.data.api.FootballApiImpl
import com.example.myfootballcollectionkmp.data.dataSource.GamesCollectionDao
import com.example.myfootballcollectionkmp.data.dataSource.MFCCollectionDatabase
import com.example.myfootballcollectionkmp.data.firebase.FirebaseEmailAuthenticatorImpl
import com.example.myfootballcollectionkmp.data.repository.FootballRepositoryImpl
import com.example.myfootballcollectionkmp.data.repository.UserRepositoryImpl
import com.example.myfootballcollectionkmp.domain.data.api.FootballApi
import com.example.myfootballcollectionkmp.domain.data.firebase.FirebaseEmailAuthenticator
import com.example.myfootballcollectionkmp.domain.data.repository.FootballRepository
import com.example.myfootballcollectionkmp.domain.data.repository.UserRepository
import com.example.myfootballcollection.domain.useCase.football.SearchTeamsUseCase
import com.example.myfootballcollectionkmp.domain.useCase.user.GetCurrentUser
import com.example.myfootballcollectionkmp.domain.useCase.user.IsFirebaseLoggedIn
import com.example.myfootballcollectionkmp.domain.useCase.user.LoginUser
import com.example.myfootballcollectionkmp.domain.useCase.user.RegisterUser
import com.example.myfootballcollectionkmp.domain.useCase.user.UserUseCases
import com.example.myfootballcollectionkmp.ui.screens.auth.create.CreateScreenViewModel
import com.example.myfootballcollectionkmp.ui.screens.auth.login.LoginScreenViewModel
import com.example.myfootballcollectionkmp.ui.screens.auth.register.RegisterScreenViewModel
import com.example.myfootballcollectionkmp.ui.screens.games.GamesScreenViewModel
import org.koin.core.module.dsl.viewModel
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
    single { FirebaseEmailAuthenticatorImpl() } bind FirebaseEmailAuthenticator::class
    single { UserRepositoryImpl(get(), get()) } bind UserRepository::class

    factory { FootballApiImpl(get()) } bind FootballApi::class
    single { FootballRepositoryImpl(get()) } bind FootballRepository::class

    single { LoginUser(get()) }
    single { RegisterUser(get()) }
    single { GetCurrentUser(get()) }
    single { IsFirebaseLoggedIn(get()) }
    single { UserUseCases(get(), get(), get(), get()) }

    single { SearchTeamsUseCase(get()) }

    viewModel { BaseViewModel(get()) }
    viewModel { LoginScreenViewModel(get()) }
    viewModel { RegisterScreenViewModel(get()) }
    viewModel { CreateScreenViewModel(get()) }
    viewModelOf(::GamesScreenViewModel)
}
