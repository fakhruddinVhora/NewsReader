package com.example.newsreader.presentation.newsscreen

import com.example.newsreader.domain.model.Article

sealed class NewsScreenEvent {
    data class onNewsCardClicked(val article: Article) : NewsScreenEvent()
    data class onNewsCategoryChanged(val category: String) : NewsScreenEvent()
    data class onSearchQueryChanged(val searchQuery: String) : NewsScreenEvent()
    object onSearchIconClicked : NewsScreenEvent()
    object onCloseIconClicked : NewsScreenEvent()

}