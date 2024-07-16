package pt.isel.pdm.chess4android.compose

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pt.isel.pdm.chess4android.ui.screens.menu.ImageListItem

@Composable
fun BuildSocialNetworkList(
    list: List<ImageListItem>,
    @StringRes label: Int
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        list.forEach {
            BuildSocialNetworkButton(
                item = it,
                label = label
            )
        }
    }
}

@Composable
private fun BuildSocialNetworkButton(
    item: ImageListItem,
    @StringRes label: Int
) {
    Button(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Black
        ),
        shape = RectangleShape,
        onClick = item.onClick
    ) {
        Row(
            modifier = Modifier
                .height(IntrinsicSize.Max)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image (
                painter = painterResource(id = item.icon),
                contentDescription = stringResource(id = item.description),
                modifier = Modifier.size(30.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = stringResource(id = label, stringResource(id = item.title)),
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}