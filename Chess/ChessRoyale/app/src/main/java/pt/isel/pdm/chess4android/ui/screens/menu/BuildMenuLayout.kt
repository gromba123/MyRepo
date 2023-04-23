package pt.isel.pdm.chess4android.ui.screens.menu

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun BuildMenuLayout(
    navController: NavController,
    buildContent: @Composable () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = rememberScaffoldState(),
        backgroundColor = MaterialTheme.colors.surface,
        bottomBar = { BuildNavBar(navController = navController) }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            buildContent()
        }
    }
}