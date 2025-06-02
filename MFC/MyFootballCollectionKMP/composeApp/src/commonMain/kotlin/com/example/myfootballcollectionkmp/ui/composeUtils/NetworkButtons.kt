package com.example.myfootballcollectionkmp.ui.composeUtils

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
import androidx.compose.ui.unit.dp
import com.example.myfootballcollectionkmp.ui.theme.Gold
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

data class ImageListItem(
    val title: StringResource,
    val description: StringResource,
    val icon: DrawableResource,
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
                painter = painterResource(resource = item.icon),
                contentDescription = stringResource(resource = item.description),
                modifier = Modifier.size(30.dp)
            )
        }
    }
}