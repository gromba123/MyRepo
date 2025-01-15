package com.example.myfootballcollection.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.myfootballcollection.navigation.graph.buildAuthNavGraph
import com.example.myfootballcollection.navigation.graph.buildMenuNavGraph

@Composable
fun BuildNavHost(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = AppGraph.Auth,
        modifier = Modifier.fillMaxSize()
    ) {
        buildMenuNavGraph(navController)
        buildAuthNavGraph(navController)
    }
}