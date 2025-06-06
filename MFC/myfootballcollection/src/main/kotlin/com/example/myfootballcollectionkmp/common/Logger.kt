package com.example.myfootballcollectionkmp.common

import java.io.File
import java.io.IOException
import java.util.logging.Logger

const val LOG_FILE_NAME = "log-files.txt"

val logger: Logger = Logger.getAnonymousLogger()
val log: File? = setupLogger()


fun setupLogger(): File? = try {
    val f = File(LOG_FILE_NAME)
    f.createNewFile()
    f
} catch (e: IOException) {
    println("An error occurred.")
    null
}