package com.example.newsreader

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.newsreader.presentation.newsscreen.NewsScreen
import com.example.newsreader.presentation.newsscreen.NewsScreenViewModel
import com.example.newsreader.presentation.theme.NewsReaderTheme
import com.example.newsreader.util.NavGraphSetup
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewsReaderTheme {
                val navController = rememberNavController()
                NavGraphSetup(navController)
            }
        }
    }
}
