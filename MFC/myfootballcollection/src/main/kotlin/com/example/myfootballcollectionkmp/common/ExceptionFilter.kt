package com.example.myfootballcollectionkmp.common

import com.example.myfootballcollectionkmp.domain.NotFoundException
import com.example.myfootballcollectionkmp.domain.ServerException
import org.postgresql.util.PSQLException
import java.util.logging.Level

// Catch the exception thrown when trying to communicate with the Database and throwing a custom exception to let the User know of the problem

const val UNKNOWN_ERROR_MESSAGE = "Unknown error"
private const val ERROR_MEDIA_TYPE = "application/problem+json"

/* Container with all the possible Status returned by the Database in order to translate them into actual understandable Exceptions */
var sqlFilter: HashMap<String, Exception> = hashMapOf(
    "UEC01" to ServerException("The User wasn't created"),
    "08001" to ServerException("Database is Offline")
)

fun <T> sqlExceptionFilter(sqlCommand: () -> T?): T {
    return try {
        sqlCommand() ?: throw NotFoundException("Resource wasn't found")
    } catch (e: Exception) {
        if (e.cause is PSQLException) {
            logger.log(
                Level.SEVERE,
                (sqlFilter[(e.cause as PSQLException).sqlState] ?: ServerException(UNKNOWN_ERROR_MESSAGE)).message
            )
            throw sqlFilter[(e.cause as PSQLException).sqlState] ?: ServerException(UNKNOWN_ERROR_MESSAGE)
        }
        logger.log(Level.SEVERE, e.message)
        if (e is NotFoundException) throw e // Special case
        throw ServerException(UNKNOWN_ERROR_MESSAGE)
    }
}


/*
// Custom exception base model used to represent errors for the User
open class ErrorJsonModel(
    val title: String? = null,
    val status: Int = HttpStatusCodes.STATUS_CODE_SERVER_ERROR,
) {
    companion object {
        val MEDIA_TYPE = MediaType.parseMediaType(ERROR_MEDIA_TYPE)
    }
}

 */