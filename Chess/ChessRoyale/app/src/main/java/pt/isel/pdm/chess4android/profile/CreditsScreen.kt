package pt.isel.pdm.chess4android.profile

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import pt.isel.pdm.chess4android.R
import pt.isel.pdm.chess4android.utils.BuildArrowBack

private const val GITHUB_URL = "https://github.com"
private const val API_URL = "https://lichess.org/api"
private const val GITHUB = "github"
private const val API = "api"

@Composable
fun BuildCreditsScreen(
    navController: NavController,
    onClick: (Intent) -> Unit
) {
    Box (
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
    ) {
        BuildArrowBack(navController = navController)
        Column (
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Column (
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(
                    text = stringResource(id = R.string.authors),
                    color = MaterialTheme.colors.secondary
                )
                Text(
                    text = stringResource(id = R.string.author),
                    color = MaterialTheme.colors.secondary
                )
            }
            val githubId = if (isSystemInDarkTheme()) R.drawable.github_negative else R.drawable.github
            val apiId = if (isSystemInDarkTheme()) R.drawable.api_negative else R.drawable.api
            Image(
                painter = painterResource(id = githubId),
                contentDescription = GITHUB,
                modifier = Modifier
                    .clickable {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(GITHUB_URL)).apply {
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        }
                        onClick(intent)
                    }
            )
            Image(
                painter = painterResource(id = apiId),
                contentDescription = API,
                modifier = Modifier
                    .clickable {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(API_URL)).apply {
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        }
                        onClick(intent)
                    }
            )
        }
    }
}