package com.example.myfootballcollection.navigation

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import com.example.myfootballcollection.domain.model.user.User
import kotlinx.serialization.json.Json

object CustomNavType {

    val UserType = object : NavType<User>(
        isNullableAllowed = false
    ) {
        override fun get(bundle: Bundle, key: String): User? {
            return Json.decodeFromString(bundle.getString(key) ?: return null)
        }

        override fun parseValue(value: String): User {
            return Json.decodeFromString(Uri.decode(value))
        }

        override fun serializeAsValue(value: User): String {
            return Uri.encode(Json.encodeToString(value))
        }

        override fun put(bundle: Bundle, key: String, value: User) {
            bundle.putString(key, Json.encodeToString(value))
        }
    }
}