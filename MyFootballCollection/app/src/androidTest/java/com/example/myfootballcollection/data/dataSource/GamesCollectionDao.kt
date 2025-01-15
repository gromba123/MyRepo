package com.example.myfootballcollection.data.dataSource

import android.content.Context
import androidx.room.Room

fun getFakeDao(context: Context): MFCCollectionDatabase {
    return Room
        .inMemoryDatabaseBuilder(
            context,
            MFCCollectionDatabase::class.java
        )
        .build()
}