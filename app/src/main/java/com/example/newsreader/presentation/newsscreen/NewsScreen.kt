package com.example.newsreader.presentation.newsscreen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.input.KeyboardType.Companion.Text
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.newsreader.domain.model.Article
import com.example.newsreader.presentation.component.CategoryTabRow
import com.example.newsreader.presentation.component.NewsArticleCard
import com.example.newsreader.presentation.component.NewsScreenTopBar
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsScreen(viewModel: NewsScreenViewModel = hiltViewModel()) {

    val scrollBehaviourValue = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val categories =
        listOf("Business", "Entertainment", "Health", "Science", "Sports", "Technology")
    val pagerState = rememberPagerState(initialPage = 0, initialPageOffsetFraction = 0.0f, pageCount = { categories.size })
    val coroutineScope = rememberCoroutineScope()

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
            HorizontalPager( state = pagerState) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(16.dp),
                    modifier = Modifier.padding(paddingValues)
                ) {
                    items(viewModel.articles.size) { index ->
                        val modifier = if (index == 0) {
                            Modifier.padding(top = 16.dp)
                        } else {
                            Modifier
                        }
                        NewsArticleCard(
                            modifier = modifier,
                            article = viewModel.articles[index]
                        ) { article ->

                        }
                    }
                }
            }

        }


    }
}

