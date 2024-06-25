package com.example.slbenficaapp.navigation

const val MENU_ROUTE = "menu"

sealed class Screen(val route: String) {
    data object Menu : Screen(MENU_ROUTE)
    data object Home : Screen("menu/home")
    data object Associate : Screen("menu/associate")
    data object Store : Screen("menu/store")
    data object Profile : Screen("menu/profile")
}