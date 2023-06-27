package pt.isel.pdm.chess4android.navigation

import pt.isel.pdm.chess4android.domain.pieces.Team

sealed class Screen(val route: String) {
    object Menu : Screen("menu")

    object Play : Screen("menu/play")

    object Puzzle : Screen("menu/profile")

    object Profile : Screen("menu/credits")

    object PuzzleList : Screen("puzzleList")

    object UnsolvedPuzzle : Screen("unsolvedPuzzle/{id}") {
        fun buildRoute(id: String) = "unsolvedPuzzle/$id"
    }
    object SolvedPuzzle : Screen("solvedPuzzle/{id}") {
        fun buildRoute(id: String) = "solvedPuzzle/$id"
    }

    object Offline : Screen("offlineGame")


    object Challenges : Screen("challenges")

    object Create : Screen("createChallenge")

    object Online : Screen("onlineGame/{id}/team/{team}") {
        fun buildRoute(id: String, team: Team) = "onlineGame/$id/team/${team.value}"
    }

    object Credits : Screen("credits")
}