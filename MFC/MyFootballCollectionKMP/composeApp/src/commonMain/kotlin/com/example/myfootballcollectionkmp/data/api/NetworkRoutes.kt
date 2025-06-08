package com.example.myfootballcollectionkmp.data.api

private const val USER_BASE_URL = "user"
private const val USER_CREATE_URL = "${USER_BASE_URL}/update"
private const val USER_UPDATE_URL = "${USER_BASE_URL}/create"
private const val USER_GET_URL = "user/{userId}"
private const val FOOTBALL_BASE_URL = "https://v3.football.api-sports.io"
const val SPORTS_API_HEADER = "x-apisports-key"
const val SPORTS_API_KEY = "f836a43522f57b781f4403f16f72643a"

sealed class Route {

    sealed class User {
        data object Create {
            fun getRoute() = USER_CREATE_URL
        }
        data object Update {
            fun getRoute() = USER_UPDATE_URL
        }
        data object Get {
            fun getRoute(userId: String) = "$USER_BASE_URL${userId}"
        }
    }

    sealed class Football {
        data object SearchTeams {
            fun getRoute(name: String) = "$FOOTBALL_BASE_URL/teams?search=$name"
        }
        data object GetTeam {
            fun getRoute(teamId: String) = "$FOOTBALL_BASE_URL/"
        }
    }
}
