package com.example.newsreader.domain.model

data class NewsResponse(
    val articles: List<Article>,
    val status: String?,
    val totalResults: Int
)