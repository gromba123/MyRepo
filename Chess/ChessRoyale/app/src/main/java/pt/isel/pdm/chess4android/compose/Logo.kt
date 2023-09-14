package pt.isel.pdm.chess4android.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pt.isel.pdm.chess4android.R
import pt.isel.pdm.chess4android.ui.theme.White

@Composable
fun BuildLogo() {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Chess",
            fontSize = 40.sp,
            color = White,
            fontWeight = FontWeight.Bold
        )
        Image (
            painter = painterResource(id = R.drawable.ic_white_king),
            contentDescription = "",
            modifier = Modifier.size(100.dp)
        )
        Text(
            text = "Royal",
            fontSize = 40.sp,
            color = White,
            fontWeight = FontWeight.Bold
        )
    }
}