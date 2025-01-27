package com.example.myfootballcollection.ui.composeUtils

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.myfootballcollection.ui.theme.Gold

data class ImageListItem(
    @StringRes val title: Int,
    @StringRes val description: Int,
    @DrawableRes val icon: Int,
    val onClick: () -> Unit
)

@Composable
fun BuildSocialNetworkList(
    list: List<ImageListItem>
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        list.forEach {
            BuildSocialNetworkButton(
                item = it
            )
        }
    }
}

@Composable
private fun BuildSocialNetworkButton(
    item: ImageListItem
) {
    Button(
        modifier = Modifier.padding(5.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Gold
        ),
        shape = RoundedCornerShape(5.dp),
        onClick = item.onClick
    ) {
        Row(
            modifier = Modifier
                .height(IntrinsicSize.Max)
                .width(50.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image (
                painter = painterResource(id = item.icon),
                contentDescription = stringResource(id = item.description),
                modifier = Modifier.size(30.dp)
            )
        }
    }
}