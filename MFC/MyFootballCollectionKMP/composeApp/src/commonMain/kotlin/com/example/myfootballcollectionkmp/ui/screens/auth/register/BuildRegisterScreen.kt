package com.example.myfootballcollectionkmp.ui.screens.auth.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myfootballcollectionkmp.navigation.AppAuth
import com.example.myfootballcollectionkmp.ui.composeUtils.BuildCrossedText
import com.example.myfootballcollectionkmp.ui.composeUtils.BuildDefaultOutlinedTextField
import com.example.myfootballcollectionkmp.ui.composeUtils.BuildPasswordOutlinedTextField
import com.example.myfootballcollectionkmp.ui.composeUtils.BuildSocialNetworkList
import com.example.myfootballcollectionkmp.ui.composeUtils.DefaultButton
import com.example.myfootballcollectionkmp.ui.composeUtils.ImageListItem
import com.example.myfootballcollectionkmp.ui.theme.White
import myfootballcollectionkmp.composeapp.generated.resources.Res
import myfootballcollectionkmp.composeapp.generated.resources.confirm_password
import myfootballcollectionkmp.composeapp.generated.resources.facebook
import myfootballcollectionkmp.composeapp.generated.resources.google
import myfootballcollectionkmp.composeapp.generated.resources.ic_ball
import myfootballcollectionkmp.composeapp.generated.resources.ic_facebook
import myfootballcollectionkmp.composeapp.generated.resources.ic_google
import myfootballcollectionkmp.composeapp.generated.resources.ic_x
import myfootballcollectionkmp.composeapp.generated.resources.mail
import myfootballcollectionkmp.composeapp.generated.resources.or
import myfootballcollectionkmp.composeapp.generated.resources.password
import myfootballcollectionkmp.composeapp.generated.resources.register
import myfootballcollectionkmp.composeapp.generated.resources.x
import org.jetbrains.compose.resources.painterResource

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
            painter = painterResource(resource = Res.drawable.ic_ball),
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
            var confirmPassword by remember { mutableStateOf("") }
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
            BuildPasswordOutlinedTextField(
                placeholder = Res.string.confirm_password,
                value = confirmPassword,
                onChange = { confirmPassword = it }
            )
            DefaultButton(
                modifier = Modifier.fillMaxWidth(),
                label = Res.string.register
            ) {
                signup(
                    navController = navController,
                    email = email,
                    password = password,
                    registerUser = registerUser
                )
            }
            BuildCrossedText(text = Res.string.or)
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