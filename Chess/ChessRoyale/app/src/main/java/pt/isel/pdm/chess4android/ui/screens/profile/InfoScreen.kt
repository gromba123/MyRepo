package pt.isel.pdm.chess4android.ui.screens.profile

import android.content.Intent
import android.net.Uri
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import pt.isel.pdm.chess4android.R
import pt.isel.pdm.chess4android.utils.BuildArrowBack

private const val GITHUB_URL = "https://github.com/gromba123/MyRepo"
private const val LINKEDIN_URL = "https://www.linkedin.com/in/goncaloromba/"
private const val API_URL = "https://lichess.org/api"
private const val GITHUB = "github_url"
private const val LINKED = "linked_url"
private const val API = "lichess_api_url"

data class GridItem(
    val url: String,
    val contentDescription: String,
    @DrawableRes val image: Int
)

@Composable
fun BuildInfoScreen(
    navController: NavController,
    onClick: (Intent) -> Unit
) {
    val items = listOf(
        GridItem(
            url = GITHUB_URL,
            contentDescription = GITHUB,
            image = R.drawable.github_negative,
        ),
        GridItem(
            url = LINKEDIN_URL,
            contentDescription = LINKED,
            image = R.drawable.linkedin_negative,
        ),
        GridItem(
            url = API_URL,
            contentDescription = API,
            image = R.drawable.api_negative,
        )
    )
    Box (
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
    ) {
        BuildArrowBack(navController = navController)
        Column (
            modifier = Modifier.fillMaxSize().padding(50.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(8.dp),
                horizontalArrangement = Arrangement.spacedBy(0.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                items(items) {
                    BuildImageLink(
                        image = it.image,
                        url = it.url,
                        contentDescription = it.contentDescription,
                        onClick = onClick
                    )
                }
            }
        }
    }
}

@Composable
fun BuildImageLink(
    @DrawableRes image: Int,
    url: String,
    contentDescription: String,
    onClick: (Intent) -> Unit
) {
    Image(
        painter = painterResource(id = image),
        contentDescription = contentDescription,
        modifier = Modifier
            .background(Color.Unspecified)
            .size(100.dp)
            .clickable {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }
                onClick(intent)
            }
    )
}