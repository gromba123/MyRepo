package com.example.myfootballcollectionkmp.navigation

import android.net.Uri
import androidx.compose.ui.text.input.KeyboardType.Companion.Uri
import androidx.core.bundle.Bundle
import androidx.navigation.NavType
import com.example.myfootballcollectionkmp.data.api.Route
import com.example.myfootballcollectionkmp.domain.model.user.User
import kotlinx.serialization.json.Json

object CustomNavType {

    val UserType = object : NavType<Route.User>(
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