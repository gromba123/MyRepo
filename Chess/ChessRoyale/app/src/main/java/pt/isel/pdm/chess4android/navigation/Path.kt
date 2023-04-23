package pt.isel.pdm.chess4android.navigation

sealed class Screen(val route: String) {
    object Menu : Screen("menu")

    object Play : Screen("menu/play")
    object Puzzle : Screen("menu/profile")
    object Profile : Screen("menu/credits")

    object PuzzleList : Screen("puzzleList")
    object UnsolvedPuzzle : Screen("unsolvedPuzzle/{id}") {
        fun setRoute(id: String) = "unsolvedPuzzle/$id"
    }
    object SolvedPuzzle : Screen("solvedPuzzle/{id}") {
        fun setRoute(id: String) = "solvedPuzzle/$id"
    }

    object Offline : Screen("offlineGame")

    object Online : Screen("onlineGame")
    object Challenges : Screen("challenges")

    object Credits : Screen("credits")
}