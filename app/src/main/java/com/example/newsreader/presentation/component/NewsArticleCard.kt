package com.example.newsreader.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.newsreader.domain.model.Article

@Composable
fun NewsArticleCard(
    modifier: Modifier = Modifier, article: Article, onCardClicked: (Article) -> Unit = {}
) {
    Card(modifier = modifier.clickable { onCardClicked(article) }) {
        ImageHolder(imageUrl = article.urlToImage ?: "")
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = article.title ?: "", style = MaterialTheme.typography.titleMedium,
                maxLines = 1, overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.padding(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = article.source?.name ?: "", style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = article.publishedAt ?: "", style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}