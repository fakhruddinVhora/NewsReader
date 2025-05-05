package com.example.newsreader.presentation.newsscreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.util.fastCbrt
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsreader.domain.model.Article
import com.example.newsreader.domain.repository.NewsRepository
import com.example.newsreader.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NewsScreenViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {

    var state by mutableStateOf(NewsState())

    fun onEvent(event: NewsScreenEvent) {
        when (event) {
            is NewsScreenEvent.onNewsCategoryChanged -> {
                state = state.copy(category = event.category)
                getNewsArticles(state.category)
            }

            is NewsScreenEvent.onNewsCardClicked -> {
                state = state.copy(selectedArticle = event.article)
            }

            is NewsScreenEvent.onSearchQueryChanged -> {}
            is NewsScreenEvent.onCloseIconClicked -> {}
            is NewsScreenEvent.onSearchIconClicked -> {}
        }
    }

    init {
        getNewsArticles("general")
    }

    private fun getNewsArticles(category: String) {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            when (val result = newsRepository.getTopHeadlines(category)) {
                is Resource.Success -> {
                    state = state.copy(articles = result.data ?: emptyList(), isLoading = false)
                }

                is Resource.Error -> {
                    state = state.copy(
                        articles = emptyList(),
                        isLoading = false,
                        error = result.message ?: "An unexpected error occurred"
                    )
                }
            }
        }
    }
}