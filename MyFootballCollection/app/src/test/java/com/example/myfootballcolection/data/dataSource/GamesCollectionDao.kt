package com.example.myfootballcolection.data.dataSource

import android.app.Application
import androidx.room.Room

fun getFakeDao(context: Application): GamesCollectionDao {
    return Room
        .inMemoryDatabaseBuilder(
            context,
            MFCCollectionDatabase::class.java
        )
        .build()
        .getMFCCollectionDao()
}