package pt.isel.pdm.chess4android.ui.screens.auth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Facebook
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import pt.isel.pdm.chess4android.R
import pt.isel.pdm.chess4android.domain.ScreenState
import pt.isel.pdm.chess4android.navigation.Screen
import pt.isel.pdm.chess4android.ui.screens.menu.IconListItem
import pt.isel.pdm.chess4android.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuildLoginScreen(
    navController: NavController,
    screenState: ScreenState,
    onLogin: (email: String, password: String, onComplete: () -> Unit) -> Unit,
    onError: (message: String) -> Unit,
) {
    BuildContent(
        navController = navController,
        onLogin = onLogin,
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
    onLogin: (email: String, password: String, onComplete: () -> Unit) -> Unit,
    onError: (message: String) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val networkItem = listOf(
            IconListItem(
                title = R.string.google,
                description = R.string.google,
                icon = Icons.Filled.Facebook,
                onClick = {}
            ),
            IconListItem(
                title = R.string.google,
                description = R.string.google,
                icon = Icons.Filled.Facebook,
                onClick = {}
            ),
        )
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        TextField(
            value = email,
            onValueChange = { email = it },
        )
        Spacer(modifier = Modifier.height(2.dp))
        TextField(
            value = password,
            onValueChange = { password = it },
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = stringResource(id = R.string.forgot_password),
            fontSize = 14.sp,
            color = White,
            modifier = Modifier.clickable { }
        )
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            shape = RectangleShape,
            //colors = ButtonDefaults.buttonColors(backgroundColor = GreenishBlue),
            onClick = {
                signIn(
                    navController = navController,
                    email = email,
                    password = password,
                    onLogin = onLogin
                )
            }
        ) {
            Text(
                text = stringResource(id = R.string.login),
                color = White,
                style = MaterialTheme.typography.button
            )
        }
        Spacer(modifier = Modifier.height(15.dp))
        Column(
            modifier = Modifier.width(IntrinsicSize.Max),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            networkItem.forEach {
                BuildSocialNetworkButton(item = it)
            }
        }
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = stringResource(id = R.string.sign_up),
            style = MaterialTheme.typography.body2,
            modifier = Modifier.clickable {  }
        )
    }
}

@Composable
fun BuildSocialNetworkButton(
    item: IconListItem
) {
    Button(
        modifier = Modifier.padding(5.dp),
        onClick = item.onClick
    ) {
        Row(
            modifier = Modifier
                .height(IntrinsicSize.Max)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon (
                imageVector = item.icon,
                contentDescription = stringResource(id = item.description),
                tint = Color.White,
                modifier = Modifier.size(30.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = stringResource(id = item.title),
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = MaterialTheme.colors.secondary
            )
        }
    }
}

private fun signIn(
    navController: NavController,
    email: String,
    password: String,
    onLogin: (email: String, password: String, onComplete: () -> Unit) -> Unit
) {
    onLogin(email, password) {
        navController.navigate(Screen.Menu.route) {
            popUpTo(Screen.Login.route) {
                inclusive = true
            }
        }
    }
}
