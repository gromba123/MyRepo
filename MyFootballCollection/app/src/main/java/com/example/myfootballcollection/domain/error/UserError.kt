package com.example.myfootballcollection.domain.error

const val USER_ALREADY_EXISTS_CODE = "ERROR_EMAIL_ALREADY_IN_USE"
const val INVALID_EMAIL_CODE = "ERROR_INVALID_EMAIL"
const val OPERATION_NOT_ALLOWED_CODE = "ERROR_OPERATION_NOT_ALLOWED"
const val WEAK_PASSWORD_CODE = "ERROR_WEAK_PASSWORD"
const val WRONG_PASSWORD_CODE = "ERROR_WRONG_PASSWORD"
const val USER_DISABLED_CODE = "ERROR_USER_DISABLED"
const val USER_NOT_FOUND_CODE = "ERROR_USER_NOT_FOUND"

interface UserError: Error {
    enum class Firebase {
        ;
        enum class Create: UserError {
            USER_ALREADY_EXISTS,
            INVALID_EMAIL,
            OPERATION_NOT_ALLOWED,
            WEAK_PASSWORD,
        }
        enum class Login: UserError {
            WRONG_PASSWORD,
            INVALID_EMAIL,
            USER_DISABLED,
            USER_NOT_FOUND,
        }
        enum class Other : UserError {
            UNEXPECTED_ERROR
        }
    }

    enum class InvalidFields: UserError {
        INVALID_EMAIL,
        INVALID_PASSWORD
    }

    //TODO("Rearrange error types")
}