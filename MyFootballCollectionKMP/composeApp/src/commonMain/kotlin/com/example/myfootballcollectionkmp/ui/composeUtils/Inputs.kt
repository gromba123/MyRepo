package com.example.myfootballcollectionkmp.ui.composeUtils

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfootballcollectionkmp.ui.theme.White
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun BuildDefaultOutlinedTextField(
    placeholder: StringResource,
    value: String,
    onChange: (newValue: String) -> Unit
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = value,
        onValueChange = onChange,
        label = {
            Text(
                text = stringResource(placeholder),
                color = White,
                fontSize = 14.sp
            )
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            focusedTextColor = White,
            focusedPlaceholderColor = White,
            cursorColor = White,
            focusedBorderColor = White,
            unfocusedBorderColor = White,
            unfocusedTextColor = White
        ),
        shape = RoundedCornerShape(5.dp)
    )
}

@Composable
fun BuildPasswordOutlinedTextField(
    placeholder: StringResource,
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
                text = stringResource(placeholder),
                color = White,
                fontSize = 14.sp
            )
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            focusedTextColor = White,
            focusedPlaceholderColor = White,
            cursorColor = White,
            focusedBorderColor = White,
            unfocusedBorderColor = White,
            unfocusedTextColor = White
        ),
        visualTransformation = if (showPassword) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        trailingIcon = {
            if (showPassword) {
                IconButton(onClick = { showPassword = false }) {
                    Icon(
                        imageVector = Icons.Filled.Visibility,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.secondary
                    )
                }
            } else {
                IconButton(onClick = { showPassword = true }) {
                    Icon(
                        imageVector = Icons.Filled.VisibilityOff,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        shape = RoundedCornerShape(5.dp)
    )
}