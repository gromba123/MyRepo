package com.example.slbenficaapp.ui.menu.home

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.slbenficaapp.R
import com.example.slbenficaapp.ui.theme.White

data class HighlightItem(
    @DrawableRes val imageId: Int,
    val description: String
)

@Composable
fun BuildHighlights() {
    val highlights = listOf(
        HighlightItem(
            imageId = R.drawable.benfica_campeao,
            description = "Campeões Nacionais"
        ),
        HighlightItem(
            imageId = R.drawable.schmidt_renova,
            description = "Roger Schmidt renova"
        ),
        HighlightItem(
            imageId = R.drawable.vencedor_supertaca,
            description = "Vencedores da Supertaça"
        ),
    )
    var index by remember {
        mutableIntStateOf(0)
    }
    Box(
       modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.BottomStart
    ) {
        Image(
            painter = painterResource(id = highlights[index].imageId), 
            contentDescription = highlights[index].description,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentScale = ContentScale.FillWidth
        )
        Column(
            modifier = Modifier.padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Text(text = highlights[index].description, fontSize = 24.sp, color = White)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowLeft,
                    contentDescription = "",
                    modifier = Modifier
                        .size(20.dp)
                        .clickable { index = calculateIndex(index - 1, highlights.size) },
                    tint = White
                )
                repeat(highlights.size) {
                    val imageVector = if (it == index) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder
                    Icon(
                        imageVector = imageVector,
                        contentDescription = "",
                        tint = White,
                        modifier = Modifier
                            .size(20.dp)
                    )
                }
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowRight,
                    contentDescription = "",
                    modifier = Modifier
                        .size(20.dp)
                        .clickable { index = calculateIndex(index + 1, highlights.size) },
                    tint = White
                )
            }
        }

    }
}

private fun calculateIndex(currentIndex: Int, listSize: Int) =
    if (currentIndex> listSize - 1) 0
    else if (currentIndex < 0) listSize - 1
    else currentIndex
