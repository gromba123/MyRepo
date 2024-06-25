package com.example.slbenficaapp.ui.menu.home

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.slbenficaapp.R
import com.example.slbenficaapp.ui.theme.White

data class NewsItem(
    @DrawableRes val imageId: Int,
    val description: String,
    val date: String
)

@Composable
fun BuildNews() {
    val news = listOf(
        NewsItem(
            imageId = R.drawable.rbs_analise,
            description = "Análise: RB Salzburg",
            date = "11/12/2023"
        ),
        NewsItem(
            imageId = R.drawable.rbs_antevisao,
            description = "Antevisão: RB Salzburg",
            date = "11/12/2023"
        ),
        NewsItem(
            imageId = R.drawable.marcel_matz,
            description = "Benfica vs Piacenza: As palavras de Marcel Matz",
            date = "31/11/2023"
        ),
        NewsItem(
            imageId = R.drawable.hoquei,
            description = "Depois de Alverca, o seu a seu dono",
            date = "31/06/2023"
        ),
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(25.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        news.forEach {
            BuildCard(item = it)
        }
    }
}

@Composable
private fun BuildCard(
    item: NewsItem
) {
    Card(
        shape = RectangleShape,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
    ) {
        Image(
            painter = painterResource(id = item.imageId),
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            contentScale = ContentScale.FillWidth
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(text = item.date, fontSize = 14.sp, color = White)
        Text(text = item.description, fontSize = 18.sp, color = White)
    }
}