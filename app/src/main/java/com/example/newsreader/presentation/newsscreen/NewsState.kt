package com.example.newsreader.presentation.newsscreen

import com.example.newsreader.domain.model.Article
import java.lang.Error

data class NewsState(
    val isLoading: Boolean = false,
    val articles: List<Article> = emptyList(),
    val error: String? = null,
    val isSearchBarVisible: Boolean = false,
    val selectedArticle: Article? = null,
    val category: String = "General",
    val searchQuery: String = "",
)