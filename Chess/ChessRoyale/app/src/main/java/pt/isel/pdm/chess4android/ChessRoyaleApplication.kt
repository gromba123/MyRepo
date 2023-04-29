package pt.isel.pdm.chess4android

import android.app.Application
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import dagger.hilt.android.HiltAndroidApp
import pt.isel.pdm.chess4android.dataAccess.DownloadDailyQuoteWorker
import java.util.concurrent.TimeUnit

const val APP_TAG = "PuzzleOfDay"

/**
 * Application for the Chess Royale application
 */
@HiltAndroidApp
class ChessRoyaleApplication : Application() {

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