package com.example.myfootballcollection.ui.screens.auth.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myfootballcollection.R
import com.example.myfootballcollection.navigation.AppAuth
import com.example.myfootballcollection.navigation.Screen
import com.example.myfootballcollection.ui.composeUtils.BuildCrossedText
import com.example.myfootballcollection.ui.composeUtils.BuildDefaultOutlinedTextField
import com.example.myfootballcollection.ui.composeUtils.BuildPasswordOutlinedTextField
import com.example.myfootballcollection.ui.composeUtils.BuildSocialNetworkList
import com.example.myfootballcollection.ui.composeUtils.DefaultButton
import com.example.myfootballcollection.ui.composeUtils.ImageListItem
import com.example.myfootballcollection.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuildLoginScreen(
    navController: NavController,
    loginUser: (mail: String, password: String, onSuccess: (Screen) -> Unit) -> Unit
) {
    BuildContent(
        navController = navController,
        loginUser = loginUser
    )
}

@ExperimentalMaterial3Api
@Composable
private fun BuildContent(
    navController: NavController,
    loginUser: (mail: String, password: String, onSuccess: (Screen) -> Unit) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 15.dp),
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
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                val networkItems by remember {
                    mutableStateOf(
                        listOf(
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
                    )
                }
                var email by remember { mutableStateOf("") }
                var password by remember { mutableStateOf("") }
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
                Text(
                    text = stringResource(id = R.string.forgot_password),
                    fontSize = 14.sp,
                    color = White,
                    modifier = Modifier.clickable { }
                )
                DefaultButton(
                    modifier = Modifier.fillMaxWidth(),
                    label = R.string.login
                ) {
                    signIn(
                        navController = navController,
                        email = email,
                        password = password,
                        onLogin = loginUser
                    )
                }
                BuildCrossedText(text = R.string.or)
                BuildSocialNetworkList(list = networkItems)
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
    onLogin: (mail: String, password: String, onSuccess: (Screen) -> Unit) -> Unit
) {
    onLogin(email, password) { screen ->
        navController.navigate(screen) {
            popUpTo(AppAuth.Login) {
                inclusive = true
            }
        }
    }
}