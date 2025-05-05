package com.example.newsreader.presentation.newsscreen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.input.KeyboardType.Companion.Text
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.newsreader.domain.model.Article
import com.example.newsreader.presentation.component.CategoryTabRow
import com.example.newsreader.presentation.component.NewsArticleCard
import com.example.newsreader.presentation.component.NewsScreenTopBar
import com.example.newsreader.presentation.component.RetryContent
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsScreen(state: NewsState, onEvent: (NewsScreenEvent) -> Unit) {

    val scrollBehaviourValue = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val categories =
        listOf("Business", "Entertainment", "Health", "Science", "Sports", "Technology")
    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0.0f,
        pageCount = { categories.size })
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            onEvent(NewsScreenEvent.onNewsCategoryChanged(categories[page]))
        }
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehaviourValue.nestedScrollConnection), topBar = {
            NewsScreenTopBar(scrollBehavior = scrollBehaviourValue,
                onSearchIconClicked = { }
            )
        }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            CategoryTabRow(pagerState = pagerState, categories = categories) { index ->
                coroutineScope.launch {
                    Log.d("=======", index.toString())
                    pagerState.animateScrollToPage(index)
                }
            }
            HorizontalPager(state = pagerState) {
                NewsArticleList(
                    state,
                    onCardClicked = {},
                    onRetry = { onEvent(NewsScreenEvent.onNewsCategoryChanged(state.category)) })
            }
        }
    }
}

@Composable
fun NewsArticleList(state: NewsState, onCardClicked: (Article) -> Unit, onRetry: () -> Unit) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(state.articles.size) { index ->
            NewsArticleCard(
                article = state.articles[index]
            ) { article ->
                onCardClicked(article)
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        if (state.isLoading) {
            CircularProgressIndicator()
        }
        if (state.error != null) {
            RetryContent(error = state.error, onRetry = { onRetry() })
        }
    }
}

