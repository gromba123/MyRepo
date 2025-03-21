package com.example.myfootballcollection.di

import android.app.Application
import androidx.room.Room
import com.example.myfootballcollection.BaseViewModel
import com.example.myfootballcollection.data.api.FootballApiImpl
import com.example.myfootballcollection.data.dataSource.GamesCollectionDao
import com.example.myfootballcollection.data.dataSource.MFCCollectionDatabase
import com.example.myfootballcollection.data.firebase.FirebaseEmailAuthenticatorImpl
import com.example.myfootballcollection.data.repository.FootballRepositoryImpl
import com.example.myfootballcollection.data.repository.UserRepositoryImpl
import com.example.myfootballcollection.domain.data.api.FootballApi
import com.example.myfootballcollection.domain.data.firebase.FirebaseEmailAuthenticator
import com.example.myfootballcollection.domain.data.repository.FootballRepository
import com.example.myfootballcollection.domain.data.repository.UserRepository
import com.example.myfootballcollection.domain.useCase.football.SearchTeamsUseCase
import com.example.myfootballcollection.domain.useCase.user.GetCurrentUser
import com.example.myfootballcollection.domain.useCase.user.IsFirebaseLoggedIn
import com.example.myfootballcollection.domain.useCase.user.LoginUser
import com.example.myfootballcollection.domain.useCase.user.RegisterUser
import com.example.myfootballcollection.domain.useCase.user.UserUseCases
import com.example.myfootballcollection.ui.screens.auth.create.CreateScreenViewModel
import com.example.myfootballcollection.ui.screens.auth.login.LoginScreenViewModel
import com.example.myfootballcollection.ui.screens.auth.register.RegisterScreenViewModel
import com.example.myfootballcollection.ui.screens.games.GamesScreenViewModel
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
