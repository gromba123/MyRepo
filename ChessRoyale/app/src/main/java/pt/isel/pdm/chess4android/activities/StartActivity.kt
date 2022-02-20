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
import pt.isel.pdm.chess4android.APP_TAG
import pt.isel.pdm.chess4android.R
import pt.isel.pdm.chess4android.activities.ui.theme.Chess4AndroidTheme
import pt.isel.pdm.chess4android.offline.game.OfflineActivity
import pt.isel.pdm.chess4android.offline.history.HistoryActivity
import pt.isel.pdm.chess4android.online.challenges.list.ChallengesListActivity

const val TITTLE_FRACTION = 0.2F
const val SCREEN_FRACTION = 0.65F
const val NAVIGATION_BUTTONS_FRACTION = 0.15F

class StartActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Chess4AndroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    BuildScreen()
                }
            }
        }
    }
}

@Composable
fun BuildScreen() {
    val configuration = LocalConfiguration.current
    val context = LocalContext.current
    val screenHeight = configuration.screenHeightDp
    val screenWidth = configuration.screenWidthDp
    var offset by remember { mutableStateOf(0f) }
    val controller = rememberScrollableState {
        offset = when {
            offset - it >= 3 * screenWidth -> (3 * screenWidth).toFloat()
            offset - it <= 0 -> 0F
            else -> offset - it
        }
        it
    }
    Log.v(APP_TAG, offset.toString())
    Column(
        Modifier
            .background(Color.Black)
            .fillMaxSize()
            .scrollable(
                orientation = Orientation.Horizontal,
                state = controller
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height((screenHeight * TITTLE_FRACTION).dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "ChessRoyale",
                color = Color.White,
                fontSize = (screenWidth * 0.1).sp,
                fontStyle = FontStyle.Italic
            )
        }
        Row(modifier = Modifier
            .fillMaxWidth()
            .height((screenHeight * SCREEN_FRACTION).dp)) {
            when {
                offset < 1.5 * screenWidth ->
                    SingleButtonScreen(stringResource(id = R.string.online)) {
                    context.startActivity(Intent(context, ChallengesListActivity::class.java))
                }
                offset < 2.5 * screenWidth -> OfflineScreen(screenWidth, context)
                else -> SingleButtonScreen(stringResource(id = R.string.credits)) {
                    context.startActivity(Intent(context, CreditsActivity::class.java))
                }
            }
        }
        Spacer(modifier = Modifier.height(2.dp))
        BuildNavigationSystem(screenWidth = screenWidth, screenHeight = screenHeight)
    }
}

@Composable
fun OfflineScreen(screenWidth: Int, context: Context) {
    BuildOfflineButton(label = stringResource(id = R.string.offline), screenWidth) {
        context.startActivity(Intent(context, OfflineActivity::class.java))
    }
    Spacer(modifier = Modifier.width(2.dp))
    BuildOfflineButton(label = stringResource(id = R.string.fetch_button), screenWidth) {
        context.startActivity(Intent(context, HistoryActivity::class.java))
    }
}

@Composable
fun SingleButtonScreen(
    label: String,
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier.fillMaxSize(),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black),
        border = BorderStroke(2.dp, Color.White),
        shape = RoundedCornerShape(10),
        onClick = onClick
    ) {
        Text(
            text = label,
            color = Color.White,
            fontSize = 20.sp
        )
    }
}

@Composable
fun BuildOfflineButton(
    label: String,
    screenWidth: Int,
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier
            .fillMaxHeight()
            .width((screenWidth * 0.5).dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black),
        border = BorderStroke(2.dp, Color.White),
        shape = RoundedCornerShape(10),
        onClick = onClick
    ) {
        Text(
            text = label,
            color = Color.White,
            fontSize = 20.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Chess4AndroidTheme {
        BuildScreen()
    }
}