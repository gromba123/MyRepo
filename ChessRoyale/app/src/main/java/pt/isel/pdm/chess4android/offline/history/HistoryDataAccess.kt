package pt.isel.pdm.chess4android.offline.history

import androidx.room.*
import pt.isel.pdm.chess4android.offline.puzzle.PuzzleDTO
import pt.isel.pdm.chess4android.offline.puzzle.PuzzleHistoryDTO
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*

/**
 * Entity that represents a table in the Room DB
 */
@Entity(tableName = "puzzle_history")
data class PuzzleEntity(
    @PrimaryKey val id: String,
    val pgn: String,
    val sln: String,
    val timestamp: Date = Date.from(Instant.now().truncatedTo(ChronoUnit.DAYS)),
    val state: Int,
    val actualPgn: String,
    val actualSln: String
)

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long) = Date(value)

    @TypeConverter
    fun dateToTimestamp(date: Date) = date.time
}

/**
 * Data Access Object that defines a contract that specifies all the DB
 * interactions
 */
@Dao
interface PuzzleHistory {
    @Insert
    suspend fun insert(puzzle: PuzzleEntity)

    @Delete
    suspend fun delete(puzzle: PuzzleEntity)

    @Query("UPDATE puzzle_history SET actualPgn = :pgn, actualSln = :sln, state = :state WHERE id = :id")
    suspend fun update(id: String, pgn: String, sln: String, state: Int)

    @Query("SELECT id, pgn, sln, actualPgn, actualSln FROM puzzle_history WHERE id = :id")
    suspend fun getPuzzle(id: String): PuzzleDTO

    @Query("SELECT id, timestamp, state FROM puzzle_history ORDER BY timestamp DESC LIMIT 100")
    suspend fun getAll(): List<PuzzleHistoryDTO>

    @Query("SELECT id, pgn, sln, actualPgn, actualSln FROM puzzle_history WHERE timestamp = :date")
    suspend fun getTodayPuzzle(date: Date = Date.from(Instant.now().truncatedTo(ChronoUnit.DAYS))): PuzzleDTO?
}

/**
 * Represents the Room DB that implements the Data Access Object contract
 */
@Database(entities = [PuzzleEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class HistoryDatabase : RoomDatabase() {
    abstract fun getPuzzleHistoryDao(): PuzzleHistory
}