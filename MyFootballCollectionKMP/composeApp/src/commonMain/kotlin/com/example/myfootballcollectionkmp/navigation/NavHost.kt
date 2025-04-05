package com.example.myfootballcollectionkmp.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.myfootballcollectionkmp.navigation.graph.buildAuthNavGraph
import com.example.myfootballcollectionkmp.navigation.graph.buildMenuNavGraph

@Composable
fun BuildNavHost(
    navController: NavHostController,
    startDestination: Screen
) {
    NavHost(
        navController = navController,
        startDestination = AppGraph.Auth,
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        buildAuthNavGraph(navController, startDestination)
        buildMenuNavGraph(navController)
    }
}