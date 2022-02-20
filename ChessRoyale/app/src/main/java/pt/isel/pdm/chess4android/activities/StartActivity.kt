package pt.isel.pdm.chess4android.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import pt.isel.pdm.chess4android.APP_TAG
import pt.isel.pdm.chess4android.R
import pt.isel.pdm.chess4android.activities.ui.theme.Chess4AndroidTheme
import pt.isel.pdm.chess4android.offline.game.OfflineActivity
import pt.isel.pdm.chess4android.offline.history.HistoryActivity
import pt.isel.pdm.chess4android.online.challenges.list.ChallengesListActivity

const val TITTLE_FRACTION = 0.2F
const val SCREEN_FRACTION = 0.65F
const val NAVIGATION_BUTTONS_FRACTION = 0.15F

//"Try to build middleware-like functions to help to turn the code more" +
//"generic and clean. May use Companion Object in Navigation to help"
class StartActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Chess4AndroidTheme {
                val navController = rememberNavController()

                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    BuildOfflineScreen()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Chess4AndroidTheme {
        BuildOfflineScreen()
    }
}