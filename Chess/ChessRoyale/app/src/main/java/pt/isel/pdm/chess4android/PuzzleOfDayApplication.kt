package pt.isel.pdm.chess4android

import android.app.Application
import androidx.room.Room
import androidx.work.*
import com.google.gson.Gson
import dagger.hilt.android.HiltAndroidApp
import pt.isel.pdm.chess4android.offline.history.DownloadDailyQuoteWorker
import pt.isel.pdm.chess4android.offline.history.HistoryDatabase
import pt.isel.pdm.chess4android.offline.history.PuzzleRepository
import pt.isel.pdm.chess4android.offline.puzzle.API_URL
import pt.isel.pdm.chess4android.offline.puzzle.PuzzleOfDayService
import pt.isel.pdm.chess4android.online.challenges.ChallengesRepository
import pt.isel.pdm.chess4android.online.games.GamesRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val APP_TAG = "PuzzleOfDay"

/**
 * Application for the Chess Royale application
 */
@HiltAndroidApp
class PuzzleOfDayApplication : Application() {

    private val puzzleOfDayService: PuzzleOfDayService by lazy {
        Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PuzzleOfDayService::class.java)
    }

    val historyDb: HistoryDatabase by lazy {
        Room.databaseBuilder(this, HistoryDatabase::class.java, "history_db").build()
    }

    val challengesRepository: ChallengesRepository by lazy { ChallengesRepository() }

    val puzzleRepository: PuzzleRepository by lazy {
        PuzzleRepository(puzzleOfDayService, historyDb.getPuzzleHistoryDao())
    }

    private val mapper: Gson by lazy { Gson() }

    val gamesRepository: GamesRepository by lazy { GamesRepository(mapper) }

    override fun onCreate() {
        super.onCreate()
        val workRequest = PeriodicWorkRequestBuilder<DownloadDailyQuoteWorker>(1, TimeUnit.DAYS)
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .setRequiresBatteryNotLow(true)
                    .setRequiresStorageNotLow(true)
                    .build()
            )
            .build()

        WorkManager
            .getInstance(this)
            .enqueueUniquePeriodicWork(
                "DownloadDailyPuzzle",
                ExistingPeriodicWorkPolicy.KEEP,
                workRequest
            )
    }
}