package pt.isel.pdm.chess4android.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pt.isel.pdm.chess4android.R
import pt.isel.pdm.chess4android.activities.ui.theme.Chess4AndroidTheme
import pt.isel.pdm.chess4android.offline.game.OfflineActivity
import pt.isel.pdm.chess4android.offline.puzzle.PuzzleActivity
import pt.isel.pdm.chess4android.online.challenges.list.ChallengesListActivity
import pt.isel.pdm.chess4android.online.games.OnlineActivity

class StartActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Chess4AndroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    BuildScreen()
                }
            }
        }
    }
}

@Composable
fun BuildScreen() {
    val context = LocalContext.current
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(100.dp))
        BuildImage()
        Spacer(modifier = Modifier.height(20.dp))
        BuildButton(stringResource(R.string.online)) {
            context.startActivity(Intent(context, ChallengesListActivity::class.java))
        }
        Spacer(modifier = Modifier.height(20.dp))
        BuildButton(stringResource(R.string.offline)) {
            context.startActivity(Intent(context, OfflineActivity::class.java))
        }
        Spacer(modifier = Modifier.height(20.dp))
        BuildButton(stringResource(R.string.puzzle)) {
            context.startActivity(Intent(context, PuzzleActivity::class.java))
        }
        Spacer(modifier = Modifier.height(20.dp))
        BuildButton(stringResource(R.string.credits)) {
            context.startActivity(Intent(context, CreditsActivity::class.java))
        }
    }
}

@Composable
fun BuildImage() {
    Image(
        painter = painterResource(id = R.drawable.ic_pieces_foreground),
        contentDescription = "launchImage",
        modifier = Modifier
            .size(275.dp)
            .clip(CircleShape)
            .border(4.dp, Color.Black, CircleShape)
    )
}

@Composable
fun BuildButton(label: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.border(2.dp, Color.Black, RoundedCornerShape(50)),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
        border = BorderStroke(0.dp, Color.Transparent)
    ) {
        Text(
            text = label,
            color = Color.Black,
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