package com.example.myfootballcollectionkmp.ui.screens.social

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import myfootballcollectionkmp.composeapp.generated.resources.Res
import myfootballcollectionkmp.composeapp.generated.resources.coming_soon
import org.jetbrains.compose.resources.stringResource

@Composable
fun BuildSocialScreen(
    navController: NavController
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = stringResource(resource = Res.string.coming_soon))
    }
}