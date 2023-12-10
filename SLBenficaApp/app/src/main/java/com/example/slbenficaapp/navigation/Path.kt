package com.example.slbenficaapp.navigation

const val ROOT_ROUTE = "root_route"

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Associate : Screen("menu")
}