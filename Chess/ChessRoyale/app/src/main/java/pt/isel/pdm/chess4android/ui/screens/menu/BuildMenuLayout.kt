package pt.isel.pdm.chess4android.ui.screens.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

@Composable
fun BuildLevelIndicator() {
    TopAppBar(
        backgroundColor = MaterialTheme.colors.primary,
    ) {
        Row(
            modifier = Modifier.padding(start = 15.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("1", color = Color.White, fontSize = 17.sp)
            CustomLinearProgressIndicator(progress = 0.5F)
        }
    }
}

@Composable
fun CustomLinearProgressIndicator(
    modifier: Modifier = Modifier,
    progress: Float,
    progressColor: Color = MaterialTheme.colors.surface,
    backgroundColor: Color = Color.Black,
    clipShape: Shape = RoundedCornerShape(15.dp)
) {
    Box(
        modifier = modifier
            .height(10.dp)
            .width(100.dp)
            .background(backgroundColor)
            .border(1.dp, Color.White, clipShape)
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(progress)
                .background(progressColor)
                .clip(clipShape),
            contentAlignment = Alignment.Center
        ) {
            Text("10/20", color = Color.White, fontSize = 7.sp)
        }
    }
}