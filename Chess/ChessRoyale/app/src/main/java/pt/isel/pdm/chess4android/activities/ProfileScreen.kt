package pt.isel.pdm.chess4android.activities

import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pt.isel.pdm.chess4android.R

@Composable
fun BuildProfileScreen() {
    val context = LocalContext.current
    BuildButton(stringResource(id = R.string.credits)) {
        context.startActivity(Intent(context, CreditsActivity::class.java))
    }
}

@Composable
private fun BuildButton(
    label: String,
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier.fillMaxSize(),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black),
        border = BorderStroke(2.dp, Color.White),
        shape = RoundedCornerShape(10),
        onClick = onClick
    ) {
        Text(
            text = label,
            color = Color.White,
            fontSize = 20.sp
        )
    }
}