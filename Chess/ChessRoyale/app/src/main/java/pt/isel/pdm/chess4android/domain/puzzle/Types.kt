package pt.isel.pdm.chess4android.domain.puzzle

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

fun PuzzleDTO.toQuiz() = Quiz(Game(pgn), Puzzle(sln.split(" "), id))

@Parcelize
data class Game(val pgn: String) : Parcelable

@Parcelize
data class Puzzle(
    val solution: List<String>,
    val id: String
) : Parcelable

@Parcelize
data class Quiz(
    val game: Game,
    val puzzle: Puzzle
) : Parcelable

/**
 * Represents a Data Transfers Object
 * to obtain information of the puzzles from the Room DB
 */
@Parcelize
data class PuzzleDTO(
    val id: String,
    val pgn: String,
    val sln: String,
    val actualPgn: String,
    val actualSln: String
) : Parcelable

/**
 * Represents a Data Transfers Object to obtain
 * partial information of the puzzles from the Room DB
 */
@Parcelize
data class PuzzleHistoryDTO(
    val id: String,
    val timestamp: String,
    val state: Int
) : Parcelable