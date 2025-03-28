package com.example.myfootballcollection.ui.screens.auth.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myfootballcollection.R
import com.example.myfootballcollection.navigation.AppAuth
import com.example.myfootballcollection.ui.composeUtils.BuildCrossedText
import com.example.myfootballcollection.ui.composeUtils.BuildDefaultOutlinedTextField
import com.example.myfootballcollection.ui.composeUtils.BuildPasswordOutlinedTextField
import com.example.myfootballcollection.ui.composeUtils.BuildSocialNetworkList
import com.example.myfootballcollection.ui.composeUtils.DefaultButton
import com.example.myfootballcollection.ui.composeUtils.ImageListItem
import com.example.myfootballcollection.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuildRegisterScreen(
    navController: NavController,
    registerUser: (mail: String, password: String, onSuccess: () -> Unit) -> Unit
) {
    BuildContent(
        navController = navController,
        registerUser = registerUser
    )
}

@ExperimentalMaterial3Api
@Composable
private fun BuildContent(
    navController: NavController,
    registerUser: (mail: String, password: String, onSuccess: () -> Unit) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_ball),
            tint = White,
            contentDescription = "logo",
            modifier = Modifier.size(64.dp)
        )
        Spacer(modifier = Modifier.height(30.dp))
        Column(
            modifier = Modifier.width(IntrinsicSize.Max),
            horizontalAlignment = Alignment.CenterHorizontally,
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
            var confirmPassword by remember { mutableStateOf("") }
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
            DefaultButton(
                modifier = Modifier.fillMaxWidth(),
                label = R.string.register
            ) {
                signup(
                    navController = navController,
                    email = email,
                    password = password,
                    registerUser = registerUser
                )
            }
            BuildCrossedText(text = R.string.or)
            BuildSocialNetworkList(list = networkItems)
        }
    }
}

private fun signup(
    navController: NavController,
    email: String,
    password: String,
    registerUser: (mail: String, password: String, onSuccess: () -> Unit) -> Unit
) {
    registerUser(email, password) {
        navController.navigate(AppAuth.Create) {
            popUpTo(AppAuth.Register) {
                inclusive = true
            }
        }
    }
}