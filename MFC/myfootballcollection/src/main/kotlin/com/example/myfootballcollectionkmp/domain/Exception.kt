package com.example.myfootballcollectionkmp.domain

// Custom exceptions showed to the User
class BadCredentialsException : Exception()
class ServerException(message: String) : Exception(message)
class NotFoundException(message: String) : Exception(message)
class BadRequestException(message: String) : Exception(message)