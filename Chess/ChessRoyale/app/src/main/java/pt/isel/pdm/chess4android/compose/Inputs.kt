package pt.isel.pdm.chess4android.compose

import androidx.annotation.StringRes
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.sp
import pt.isel.pdm.chess4android.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuildDefaultOutlinedTextField(
    @StringRes placeholder: Int,
    value: String,
    onChange: (newValue: String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        label = {
            Text(
                text = stringResource(id = placeholder),
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
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuildPasswordOutlinedTextField(
    @StringRes placeholder: Int,
    value: String,
    onChange: (newValue: String) -> Unit
) {
    var showPassword by remember { mutableStateOf(false) }
    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        label = {
            Text(
                text = stringResource(id = placeholder),
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