package pt.isel.pdm.chess4android.dataAccess

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import pt.isel.pdm.chess4android.dataAccess.PuzzleRepository

/**
 * Worker that obtains the puzzle of the day
 */
@HiltWorker
class DownloadDailyQuoteWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val puzzleRepository: PuzzleRepository
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        puzzleRepository.fetchPuzzleOfDay()

        return Result.success()
    }
}