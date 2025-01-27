package com.example.myfootballcollection.ui.composeUtils

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.myfootballcollection.navigation.AppAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuildTopBar(
    navController: NavController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val destination = navBackStackEntry?.destination
    if (doWeShowTopBar(destination)) {
        TopAppBar(
            modifier = Modifier.padding(10.dp),
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.surface,
            ),
            navigationIcon = {
                BuildArrowBack { navController.navigateUp() }
            },
            title = { Text(text = "") }
        )
    }
    else {
        TopAppBar(
            modifier = Modifier.padding(10.dp),
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.surface,
            ),
            title = { Text(text = "") }
        )
    }
}

private fun doWeShowTopBar(navDestination: NavDestination?) =
    if (navDestination == null) false
    else {
        val currentRoute = navDestination.route
        if (currentRoute == null) {
            false
        } else {
            if(currentRoute.startsWith(AppAuth.Register::class.qualifiedName!!)) {
                true
                //TODO(Add other screens in the future)
            } else {
                false
            }
        }
    }