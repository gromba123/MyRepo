package pt.isel.pdm.chess4android.navigation

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
    object Online : Screen("onlineGame")

    object Credits : Screen("credits")
}