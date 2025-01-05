package com.example.myfootballcolection.ui.screens.auth.register

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
import com.example.myfootballcolection.navigation.AppAuth
import com.example.myfootballcolection.navigation.Screen
import com.example.myfootballcolection.ui.composeUtils.BuildCrossedText
import com.example.myfootballcolection.ui.composeUtils.BuildDefaultOutlinedTextField
import com.example.myfootballcolection.ui.composeUtils.BuildPasswordOutlinedTextField
import com.example.myfootballcolection.ui.composeUtils.BuildSocialNetworkList
import com.example.myfootballcolection.ui.composeUtils.ImageListItem
import com.example.myfootballcolection.ui.theme.Gold
import com.example.myfootballcolection.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuildRegisterScreen(
    navController: NavController
) {
    BuildContent(navController = navController)
}

@ExperimentalMaterial3Api
@Composable
private fun BuildContent(
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 35.dp, bottom = 20.dp),
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
            var confirmPassword by remember { mutableStateOf("") }
            BuildSocialNetworkList(list = networkItems, label = R.string.register_with)
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
            BuildPasswordOutlinedTextField(
                placeholder = R.string.confirm_password,
                value = confirmPassword,
                onChange = { confirmPassword = it }
            )
            Button(
                modifier = Modifier.fillMaxWidth(),
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Gold
                ),
                onClick = {
                    signup(
                        navController = navController,
                        email = email,
                        password = password,
                        onSignup = {_, _, _-> }
                    )
                }
            ) {
                Text(
                    text = stringResource(id = R.string.register),
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
    password: String,
    onSignup: (email: String,  password: String, onComplete: () -> Unit) -> Unit
) {
    onSignup(email, password) {
        navController.navigate(Screen.Social) {
            popUpTo(AppAuth.Register) {
                inclusive = true
            }
        }
    }
}