package com.example.newsreader.util

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.newsreader.presentation.articlescreen.ArticleScreen
import com.example.newsreader.presentation.newsscreen.NewsScreen
import com.example.newsreader.presentation.newsscreen.NewsScreenViewModel


@Composable
fun NavGraphSetup(navController: NavHostController) {
    val arg_key = "web_url"
    NavHost(navController = navController, startDestination = "news_screen") {
        composable("news_screen") {
            val viewmodel : NewsScreenViewModel = hiltViewModel()
            NewsScreen(state = viewmodel.state, onEvent = viewmodel::onEvent, onReadFullStoryButtonClicked = {
                navController.navigate("article_screen?$arg_key=$it")
            })
        }
        composable(route = "article_screen?$arg_key={$arg_key}",
            arguments = listOf(navArgument(name = arg_key) {
                type = androidx.navigation.NavType.StringType
              })
        ){
            Log.d("=======", "ArticleScreen: ${it.arguments?.getString(arg_key)}")
            it.arguments?.getString(arg_key)?.let { it1 ->
                ArticleScreen(url = it1){
                    navController.navigateUp()
                }
            }
        }

    }
}



