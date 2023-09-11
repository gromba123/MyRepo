package pt.isel.pdm.chess4android.domain

enum class FetchState {
    NotLoaded,
    Loaded
}

enum class ScreenState {
    Loaded,
    Loading,
    Error,
    Delete,
    Deleting
}