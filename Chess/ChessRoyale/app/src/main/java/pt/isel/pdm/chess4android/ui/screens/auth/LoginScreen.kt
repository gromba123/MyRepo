package pt.isel.pdm.chess4android.ui.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import pt.isel.pdm.chess4android.R
import pt.isel.pdm.chess4android.domain.ScreenState
import pt.isel.pdm.chess4android.navigation.Screen
import pt.isel.pdm.chess4android.ui.screens.menu.ImageListItem
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
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 20.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            BuildLogo()
            Spacer(modifier = Modifier.height(35.dp))
            Column(
                modifier = Modifier.width(IntrinsicSize.Max),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                val networkItem = listOf(
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
                var password by remember { mutableStateOf("") }
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    networkItem.forEach {
                        BuildSocialNetworkButton(item = it)
                    }
                }
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Divider(
                        modifier = Modifier.fillMaxWidth(),
                        color = White
                    )
                    Text(
                        text = stringResource(id = R.string.or),
                        color = White,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .background(color = MaterialTheme.colors.surface)
                            .padding(
                                start = 5.dp,
                                end = 5.dp,
                                bottom = 5.dp
                            )
                    )
                }
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = {
                        Text(
                            text = stringResource(id = R.string.username),
                            color = White,
                            fontSize = 14.sp
                        )
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        containerColor = Color.Black,
                        textColor = White,
                        placeholderColor = White,
                        cursorColor = White,
                        focusedBorderColor = White,
                        unfocusedBorderColor = White
                    )
                )
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = {
                        Text(
                            text = stringResource(id = R.string.password),
                            color = White,
                            fontSize = 14.sp
                        )
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        containerColor = Color.Black,
                        textColor = White,
                        placeholderColor = White,
                        cursorColor = White,
                        focusedBorderColor = White,
                        unfocusedBorderColor = White
                    )
                )
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RectangleShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black
                    ),
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
                        fontSize = 14.sp,
                        color = MaterialTheme.colors.secondary
                    )
                }
                Text(
                    text = stringResource(id = R.string.forgot_password),
                    fontSize = 14.sp,
                    color = White,
                    modifier = Modifier.clickable { }
                )
            }
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            Divider(
                modifier = Modifier.fillMaxWidth(),
                color = White
            )
            Text(
                text = stringResource(id = R.string.havent_sign_up),
                fontSize = 14.sp,
                color = MaterialTheme.colors.secondary,
                modifier = Modifier.clickable {  }
            )
        }
    }

}

@Composable
fun BuildSocialNetworkButton(
    item: ImageListItem
) {
    Button(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Black
        ),
        shape = RectangleShape,
        onClick = item.onClick
    ) {
        Row(
            modifier = Modifier
                .height(IntrinsicSize.Max)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image (
                painter = painterResource(id = item.icon),
                contentDescription = stringResource(id = item.description),
                modifier = Modifier.size(30.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = stringResource(id = R.string.login_with, stringResource(id = item.title)),
                fontSize = 14.sp,
                color = MaterialTheme.colors.secondary
            )
        }
    }
}

@Composable
fun BuildLogo() {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Chess",
            fontSize = 40.sp,
            color = White,
            fontWeight = FontWeight.Bold
        )
        Image (
            painter = painterResource(id = R.drawable.ic_white_king),
            contentDescription = "",
            modifier = Modifier.size(100.dp)
        )
        Text(
            text = "Royal",
            fontSize = 40.sp,
            color = White,
            fontWeight = FontWeight.Bold
        )
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
