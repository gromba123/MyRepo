package com.example.myfootballcollectionkmp.navigation

import androidx.core.bundle.Bundle
import androidx.navigation.NavType
import com.example.myfootballcollectionkmp.domain.model.user.User
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import net.thauvin.erik.urlencoder.UrlEncoderUtil

object CustomNavType {

    val UserType = object : NavType<User>(
        isNullableAllowed = false
    ) {
        override fun get(bundle: Bundle, key: String): User? {
            return Json.decodeFromString(bundle.getString(key) ?: return null)
        }

        override fun parseValue(value: String): User {
            return Json.decodeFromString(UrlEncoderUtil.decode(value))
        }

        override fun serializeAsValue(value: User): String {
            return UrlEncoderUtil.encode(Json.encodeToString(value))
        }

        override fun put(bundle: Bundle, key: String, value: User) {
            bundle.putString(key, Json.encodeToString(value))
        }
    }
}