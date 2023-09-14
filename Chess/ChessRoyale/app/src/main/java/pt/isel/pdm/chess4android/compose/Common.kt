package pt.isel.pdm.chess4android.compose

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pt.isel.pdm.chess4android.ui.theme.White

@Composable
fun BuildCrossedText(
    @StringRes text: Int
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Divider(
            modifier = Modifier.fillMaxWidth(),
            color = White
        )
        Text(
            text = stringResource(id = text),
            color = White,
            fontSize = 14.sp,
            modifier = Modifier
                .background(color = MaterialTheme.colors.surface)
                .padding(
                    start = 5.dp,
                    end = 5.dp,
                    bottom = 5.dp
                )
        )
    }
}