package com.example.newsreader.data.repository

import com.example.newsreader.data.remote.NewsApi
import com.example.newsreader.domain.model.Article
import com.example.newsreader.domain.repository.NewsRepository
import com.example.newsreader.util.Resource

class NewsRepositoryImpl(
    private val newsApi: NewsApi,
) : NewsRepository {
    override suspend fun getTopHeadlines(category: String): Resource<List<Article>> {
        return try {
            val news = newsApi.getBreakingNews(category = category)
            Resource.Success(news.articles)

        } catch (e: Exception) {
            Resource.Error("Failed to fetch Error: ${e.message}")
        }
    }
}