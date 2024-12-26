package com.example.myfootballcolection.domain.error

interface UserError: Error {
    enum class Firebase: UserError {
        USER_ALREADY_EXISTS,
        SESSION_TIMEOUT,
    }

    enum class InvalidFields: UserError {
        INVALID_EMAIL,
        INVALID_PASSWORD
    }

    //TODO("Rearrange error types")
}