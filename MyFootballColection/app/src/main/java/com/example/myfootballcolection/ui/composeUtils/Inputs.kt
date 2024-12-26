package com.example.myfootballcolection.ui.composeUtils

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.sp
import com.example.myfootballcolection.ui.theme.White

@Composable
fun BuildDefaultOutlinedTextField(
    @StringRes placeholder: Int,
    value: String,
    onChange: (newValue: String) -> Unit
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = value,
        onValueChange = onChange,
        label = {
            Text(
                text = stringResource(id = placeholder),
                color = White,
                fontSize = 14.sp
            )
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.Black,
            focusedTextColor = White,
            focusedPlaceholderColor = White,
            cursorColor = White,
            focusedBorderColor = White,
            unfocusedBorderColor = White
        )
    )
}

@Composable
fun BuildPasswordOutlinedTextField(
    @StringRes placeholder: Int,
    value: String,
    onChange: (newValue: String) -> Unit
) {
    var showPassword by remember { mutableStateOf(false) }
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = value,
        onValueChange = onChange,
        label = {
            Text(
                text = stringResource(id = placeholder),
                color = White,
                fontSize = 14.sp
            )
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.Black,
            focusedTextColor = White,
            focusedPlaceholderColor = White,
            cursorColor = White,
            focusedBorderColor = White,
            unfocusedBorderColor = White
        ),
        visualTransformation = if (showPassword) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        trailingIcon = {
            if (showPassword) {
                IconButton(onClick = { showPassword = false }) {
                    Icon(imageVector = Icons.Filled.Visibility, contentDescription = "")
                }
            } else {
                IconButton(onClick = { showPassword = true }) {
                    Icon(imageVector = Icons.Filled.VisibilityOff, contentDescription = "")
                }
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
    )
}