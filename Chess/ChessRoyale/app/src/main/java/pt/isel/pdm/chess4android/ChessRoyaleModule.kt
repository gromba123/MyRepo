package pt.isel.pdm.chess4android

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import pt.isel.pdm.chess4android.dataAccess.API_URL
import pt.isel.pdm.chess4android.dataAccess.ChallengesRepository
import pt.isel.pdm.chess4android.dataAccess.HistoryDatabase
import pt.isel.pdm.chess4android.dataAccess.PuzzleOfDayService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ChessRoyaleModule {

    @Provides
    @Singleton
    fun getPuzzleOfDayService(): PuzzleOfDayService =
        Retrofit
        .Builder()
        .baseUrl(API_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(PuzzleOfDayService::class.java)

    @Provides
    @Singleton
    fun getHistoryDatabaseDao(@ApplicationContext context: Context) =
        Room
        .databaseBuilder(
            context,
            HistoryDatabase::class.java,
            "history_db"
        )
        .build()
        .getPuzzleHistoryDao()

    @Provides
    @Singleton
    fun getChallengesRepository() = ChallengesRepository()

    @Provides
    @Singleton
    fun getGson() = Gson()
}