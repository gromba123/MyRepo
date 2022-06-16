package pt.isel.pdm.chess4android.offline.history

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import pt.isel.pdm.chess4android.PuzzleOfDayApplication

/**
 * Worker that obtains the puzzle of the day
 */
class DownloadDailyQuoteWorker(appContext: Context, workerParams: WorkerParameters)
    : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        val app : PuzzleOfDayApplication = applicationContext as PuzzleOfDayApplication

        app.puzzleRepository.fetchPuzzleOfDay()

        return Result.success()
    }
}