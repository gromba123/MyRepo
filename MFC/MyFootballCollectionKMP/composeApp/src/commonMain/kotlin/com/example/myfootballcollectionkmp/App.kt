package com.example.myfootballcollectionkmp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.myfootballcollectionkmp.navigation.BuildNavHost
import com.example.myfootballcollectionkmp.ui.composeUtils.BuildNavBar
import com.example.myfootballcollectionkmp.ui.theme.MyFootballCollectionTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App() {
    MyFootballCollectionTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            val navController = rememberNavController()
            val vm = koinViewModel<BaseViewModel>()
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                containerColor = MaterialTheme.colorScheme.surface,
                bottomBar = {
                    BuildNavBar(navController = navController)
                }
            ) { paddingValues ->
                Box(modifier = Modifier.padding(paddingValues)) {
                    BuildNavHost(
                        navController = navController,
                        startDestination = vm.isUserLoggedIn()
                    )
                }
            }
        }
    }
}