package com.example.myfootballcollection.ui.composeUtils

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfootballcollection.ui.theme.Gold

@Composable
fun DefaultButton(
    modifier: Modifier = Modifier,
    label: Int,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        shape = RoundedCornerShape(5.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Gold
        ),
        onClick = onClick
    ) {
        Text(
            text = stringResource(id = label),
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.secondary
        )
    }
}