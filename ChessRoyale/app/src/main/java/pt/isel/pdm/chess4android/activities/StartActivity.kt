package pt.isel.pdm.chess4android.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import pt.isel.pdm.chess4android.activities.ui.theme.Chess4AndroidTheme

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
                    BuildScreen(navController)
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