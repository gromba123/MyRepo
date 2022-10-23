package pt.isel.pdm.chess4android.domain

class ServiceUnavailable(message: String = "", cause: Throwable? = null) : Exception(message, cause)