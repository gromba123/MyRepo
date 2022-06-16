package pt.isel.pdm.chess4android.offline.history

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LifecycleOwner
import pt.isel.pdm.chess4android.activities.ui.theme.Chess4AndroidTheme
import pt.isel.pdm.chess4android.offline.puzzle.PuzzleActivity
import pt.isel.pdm.chess4android.offline.puzzle.PuzzleHistoryDTO
import pt.isel.pdm.chess4android.offline.puzzle.SolvedActivity

private const val ITEMS_LIST_FRACTION = 0.9F
private const val FETCH_FRACTION = 0.1F
const val PUZZLE_EXTRA = "HistoryActivity.Extra.Puzzle"

class PuzzleHistoryActivity : ComponentActivity() {

    private val viewModel by viewModels<HistoryActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Chess4AndroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val configuration = LocalConfiguration.current
                    val screenHeight = configuration.screenHeightDp
                    val owner: LifecycleOwner = this
                    val activity: Activity = this
                    BuildScreen(
                        viewModel = viewModel,
                        activity = activity,
                        owner = owner,
                        screenHeight = screenHeight
                    )
                }
            }
        }
    }
}

@Composable
fun BuildScreen(
    viewModel: HistoryActivityViewModel,
    activity: Activity,
    owner: LifecycleOwner,
    screenHeight: Int
) {
    Column {
        BuildList(viewModel = viewModel, activity = activity, screenHeight = screenHeight)
        BuildFetchButton(
            viewModel = viewModel,
            activity = activity,
            owner = owner,
            screenHeight = screenHeight
        )
    }
}

@Composable
fun BuildList(
    viewModel: HistoryActivityViewModel,
    activity: Activity,
    screenHeight: Int
) {
    val context = LocalContext.current
    val list = viewModel.history.observeAsState().value
    if (list != null) {
        LazyColumn (
            modifier = Modifier
                .fillMaxWidth()
                .height((screenHeight * ITEMS_LIST_FRACTION).dp),
            verticalArrangement = Arrangement.SpaceAround
        ) {
            itemsIndexed(list) { _, item ->
                BuildListItem(dto = item) {
                    val dto = viewModel.loadPuzzle(item).value
                    if (dto != null) {
                        val intent = if (item.state != 2)
                            PuzzleActivity.buildIntent(activity, dto)
                        else SolvedActivity.buildIntent(activity, dto)
                        context.startActivity(intent)
                    }
                }
            }
        }
    }
}

//TODO("Verify the puzzle state before switching activities")
@Composable
fun BuildFetchButton(
    viewModel: HistoryActivityViewModel,
    activity: Activity,
    owner: LifecycleOwner,
    screenHeight: Int
) {
    val context = LocalContext.current
    Button(
        modifier = Modifier.fillMaxWidth().height((screenHeight * FETCH_FRACTION).dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black),
        border = BorderStroke(2.dp, Color.White),
        shape = RoundedCornerShape(10),
        onClick = {
            viewModel.fetchDailyPuzzle().observe(owner) {
                if (it != null) {
                    context.startActivity(PuzzleActivity.buildIntent(activity, it))
                }
            }
        }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize(),
        ) {
            Text(
                //stringResource(id = text)
                text = "Fetch puzzle",
                color = Color.White,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}