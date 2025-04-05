package com.example.myfootballcollectionkmp.data.api

private const val USER_BASE_URL = ""
private const val FOOTBALL_BASE_URL = "https://v3.football.api-sports.io"
const val SPORTS_API_HEADER = "x-apisports-key"
const val SPORTS_API_KEY = "f836a43522f57b781f4403f16f72643a"

sealed class Route {

    sealed class User {
        data object Get {
            fun getRoute(teamId: String) = "$USER_BASE_URL/"
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
