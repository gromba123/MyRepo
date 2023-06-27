package pt.isel.pdm.chess4android.ui.screens.menu

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

data class MenuItem(
    @StringRes val title: Int,
    @StringRes val description: Int,
    val icon: ImageVector,
    val onClick: () -> Unit
)

@Composable
fun BuildMenuListItems(
    navController: NavController,
    items: List<MenuItem>
) {
    BuildMenuLayout(navController = navController) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(20.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            items(items = items) { item ->
                BuildMenuButton(item)
            }
        }
    }
}

@Composable
fun BuildMenuButton(
    item: MenuItem
) {
    Button(
        modifier = Modifier.padding(5.dp),
        onClick = item.onClick
    ) {
        Row(
            modifier = Modifier.height(IntrinsicSize.Max).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon (
                imageVector = item.icon,
                contentDescription = stringResource(id = item.description),
                tint = Color.White,
                modifier = Modifier.size(30.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = stringResource(id = item.title),
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = MaterialTheme.colors.secondary
                )
                Text(
                    text = stringResource(id = item.description),
                    fontSize = 10.sp,
                    color = MaterialTheme.colors.secondary
                )
            }
        }
    }
}