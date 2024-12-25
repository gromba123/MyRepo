package com.example.myfootballcolection.ui.screens.auth.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myfootballcolection.R
import com.example.myfootballcolection.compose.BuildCrossedText
import com.example.myfootballcolection.compose.BuildDefaultOutlinedTextField
import com.example.myfootballcolection.compose.BuildPasswordOutlinedTextField
import com.example.myfootballcolection.compose.BuildSocialNetworkList
import com.example.myfootballcolection.compose.ImageListItem
import com.example.myfootballcolection.navigation.AppAuth
import com.example.myfootballcolection.navigation.Screen
import com.example.myfootballcolection.ui.theme.Gold
import com.example.myfootballcolection.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuildLoginScreen(
    navController: NavController
) {
    BuildContent(navController = navController)
}

@ExperimentalMaterial3Api
@Composable
private fun BuildContent(
    navController: NavController,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 20.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 35.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_ball),
                tint = White,
                contentDescription = "logo"
            )
            Spacer(modifier = Modifier.height(30.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
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
                        title = R.string.x,
                        description = R.string.x,
                        icon = R.drawable.ic_x,
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
                BuildSocialNetworkList(list = networkItems, label = R.string.login_with)
                BuildCrossedText(text = R.string.or)
                BuildDefaultOutlinedTextField(
                    placeholder = R.string.mail,
                    value = email,
                    onChange = { email = it }
                )
                BuildPasswordOutlinedTextField(
                    placeholder = R.string.password,
                    value = password,
                    onChange = { password = it }
                )
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RectangleShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Gold
                    ),
                    onClick = {
                        signIn(
                            navController = navController,
                            email = email,
                            password = password,
                            onLogin = { email, password, onComplete ->}
                        )
                    }
                ) {
                    Text(
                        text = stringResource(id = R.string.login),
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.secondary
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
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                color = White
            )
            Text(
                text = stringResource(id = R.string.havent_sign_up),
                fontSize = 14.sp,
                color = White,
                modifier = Modifier.clickable {
                    navController.navigate(AppAuth.Register)
                }
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
        navController.navigate(Screen.Social) {
            popUpTo(AppAuth.Login) {
                inclusive = true
            }
        }
    }
}