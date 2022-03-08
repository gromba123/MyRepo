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

private const val ITEMS_LIST_FRACTION = 0.8F
private const val FETCH_FRACTION = 0.2F

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
                    val screenWidth = configuration.screenWidthDp
                    val owner: LifecycleOwner = this
                    val activity: Activity = this
                }
            }
        }
    }
}

@Composable
fun BuildScreen(
    viewModel: HistoryActivityViewModel,
    activity: Activity,
    owner: LifecycleOwner
) {
    Column {
        BuildList(viewModel = viewModel, activity = activity, owner = owner)
        BuildFetchButton(viewModel = viewModel)
    }
}

@Composable
fun BuildList(
    viewModel: HistoryActivityViewModel,
    activity: Activity,
    owner: LifecycleOwner
) {
    val context = LocalContext.current
    val state = viewModel.history.observeAsState()
    val list = state.value
    if (list != null) {
        LazyColumn (
            modifier = Modifier
                .fillMaxWidth()
            //.height((screenHeight * ITEMS_LIST_FRACTION).dp)
            ,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            itemsIndexed(list) { _, item ->
                BuildListItem(dto = item) {
                    viewModel.loadPuzzle(item).observe(owner) { dto ->
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
}

@Composable
fun BuildFetchButton(
    viewModel: HistoryActivityViewModel
) {
    Button(
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black),
        border = BorderStroke(2.dp, Color.White),
        shape = RoundedCornerShape(10),
        onClick = {

        }
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.fillMaxSize()
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