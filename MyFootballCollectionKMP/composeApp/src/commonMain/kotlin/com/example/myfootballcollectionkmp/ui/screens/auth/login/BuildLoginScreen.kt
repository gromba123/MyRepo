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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myfootballcollectionkmp.navigation.AppAuth
import com.example.myfootballcollectionkmp.navigation.Screen
import com.example.myfootballcollectionkmp.ui.composeUtils.BuildCrossedText
import com.example.myfootballcollectionkmp.ui.composeUtils.BuildDefaultOutlinedTextField
import com.example.myfootballcollectionkmp.ui.composeUtils.BuildPasswordOutlinedTextField
import com.example.myfootballcollectionkmp.ui.composeUtils.BuildSocialNetworkList
import com.example.myfootballcollectionkmp.ui.composeUtils.DefaultButton
import com.example.myfootballcollectionkmp.ui.composeUtils.ImageListItem
import com.example.myfootballcollectionkmp.ui.theme.White
import myfootballcollectionkmp.composeapp.generated.resources.Res
import myfootballcollectionkmp.composeapp.generated.resources.facebook
import myfootballcollectionkmp.composeapp.generated.resources.forgot_password
import myfootballcollectionkmp.composeapp.generated.resources.google
import myfootballcollectionkmp.composeapp.generated.resources.havent_sign_up
import myfootballcollectionkmp.composeapp.generated.resources.ic_ball
import myfootballcollectionkmp.composeapp.generated.resources.ic_facebook
import myfootballcollectionkmp.composeapp.generated.resources.ic_google
import myfootballcollectionkmp.composeapp.generated.resources.ic_x
import myfootballcollectionkmp.composeapp.generated.resources.login
import myfootballcollectionkmp.composeapp.generated.resources.mail
import myfootballcollectionkmp.composeapp.generated.resources.or
import myfootballcollectionkmp.composeapp.generated.resources.password
import myfootballcollectionkmp.composeapp.generated.resources.x
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

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
                painter = painterResource(resource = Res.drawable.ic_ball),
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
                                title = Res.string.google,
                                description = Res.string.google,
                                icon = Res.drawable.ic_google,
                                onClick = {}
                            ),
                            ImageListItem(
                                title = Res.string.x,
                                description = Res.string.x,
                                icon = Res.drawable.ic_x,
                                onClick = {}
                            ),
                            ImageListItem(
                                title = Res.string.facebook,
                                description = Res.string.facebook,
                                icon = Res.drawable.ic_facebook,
                                onClick = {}
                            ),
                        )
                    )
                }
                var email by remember { mutableStateOf("") }
                var password by remember { mutableStateOf("") }
                BuildDefaultOutlinedTextField(
                    placeholder = Res.string.mail,
                    value = email,
                    onChange = { email = it }
                )
                BuildPasswordOutlinedTextField(
                    placeholder = Res.string.password,
                    value = password,
                    onChange = { password = it }
                )
                Text(
                    text = stringResource(resource = Res.string.forgot_password),
                    fontSize = 14.sp,
                    color = White,
                    modifier = Modifier.clickable { }
                )
                DefaultButton(
                    modifier = Modifier.fillMaxWidth(),
                    label = Res.string.login
                ) {
                    signIn(
                        navController = navController,
                        email = email,
                        password = password,
                        onLogin = loginUser
                    )
                }
                BuildCrossedText(text = Res.string.or)
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
                text = stringResource(resource = Res.string.havent_sign_up),
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