package pt.isel.pdm.chess4android.ui.screens.auth.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import pt.isel.pdm.chess4android.R
import pt.isel.pdm.chess4android.compose.BuildCrossedText
import pt.isel.pdm.chess4android.compose.BuildDefaultOutlinedTextField
import pt.isel.pdm.chess4android.compose.BuildLogo
import pt.isel.pdm.chess4android.compose.BuildPasswordOutlinedTextField
import pt.isel.pdm.chess4android.compose.BuildSocialNetworkList
import pt.isel.pdm.chess4android.domain.ScreenState
import pt.isel.pdm.chess4android.navigation.Screen
import pt.isel.pdm.chess4android.ui.screens.menu.ImageListItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuildSignupScreen(
    navController: NavController,
    screenState: ScreenState,
    onSignup: (email: String, username: String, password: String, onComplete: () -> Unit) -> Unit,
    onError: (message: String) -> Unit,
) {
    BuildContent(
        navController = navController,
        onSignup = onSignup,
        onError = onError
    )
    if (screenState == ScreenState.Loading) {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            //BuildWaitingScreen(text = stringResource(id = R.string.loading))
        }
    }
}

@ExperimentalMaterial3Api
@Composable
private fun BuildContent(
    navController: NavController,
    onSignup: (email: String,  username: String, password: String, onComplete: () -> Unit) -> Unit,
    onError: (message: String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 35.dp, bottom = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BuildLogo()
        Spacer(modifier = Modifier.height(30.dp))
        Column(
            modifier = Modifier.width(IntrinsicSize.Max),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            val networkItems = listOf(
                ImageListItem(
                    title = R.string.google,
                    description = R.string.google,
                    icon = R.drawable.ic_google,
                    onClick = {}
                ),
                ImageListItem(
                    title = R.string.twitter,
                    description = R.string.twitter,
                    icon = R.drawable.ic_twitter,
                    onClick = {}
                ),
                ImageListItem(
                    title = R.string.facebook,
                    description = R.string.facebook,
                    icon = R.drawable.ic_facebook,
                    onClick = {}
                ),
            )
            var email by remember { mutableStateOf("") }
            var username by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }
            var confirmPassword by remember { mutableStateOf("") }
            BuildSocialNetworkList(list = networkItems, label = R.string.register_with)
            BuildCrossedText(text = R.string.or)
            BuildDefaultOutlinedTextField(
                placeholder = R.string.mail,
                value = email,
                onChange = { email = it }
            )
            BuildDefaultOutlinedTextField(
                placeholder = R.string.username,
                value = username,
                onChange = { username = it }
            )
            BuildPasswordOutlinedTextField(
                placeholder = R.string.password,
                value = password,
                onChange = { password = it }
            )
            BuildPasswordOutlinedTextField(
                placeholder = R.string.confirm_password,
                value = confirmPassword,
                onChange = { confirmPassword = it }
            )
            Button(
                modifier = Modifier.fillMaxWidth(),
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black
                ),
                onClick = {
                    signup(
                        navController = navController,
                        email = email,
                        username = username,
                        password = password,
                        onSignup = onSignup
                    )
                }
            ) {
                Text(
                    text = stringResource(id = R.string.advance),
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}

private fun signup(
    navController: NavController,
    email: String,
    username: String,
    password: String,
    onSignup: (email: String,  username: String, password: String, onComplete: () -> Unit) -> Unit
) {
    onSignup(email, username, password) {
        navController.navigate(Screen.Menu.route) {
            popUpTo(Screen.SignUp.route) {
                inclusive = true
            }
        }
    }
}