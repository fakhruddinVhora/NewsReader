package com.example.newsreader.domain.repository

import com.example.newsreader.domain.model.Article
import com.example.newsreader.util.Resource

interface NewsRepository {

    suspend fun getTopHeadlines(
        category : String
    ): Resource<List<Article>>
}