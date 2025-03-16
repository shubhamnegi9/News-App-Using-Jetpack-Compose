package com.shubham.newsapp

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.shubham.newsapp.components.BottomMenu
import com.shubham.newsapp.models.ArticleCategory
import com.shubham.newsapp.models.TopNewsArticle
import com.shubham.newsapp.network.NewsManager
import com.shubham.newsapp.screens.Categories
import com.shubham.newsapp.screens.DetailScreen
import com.shubham.newsapp.screens.Sources
import com.shubham.newsapp.screens.TopNews

@Composable
fun NewsApp(newsManager: NewsManager) {

    val articles = mutableListOf<TopNewsArticle>()
    articles.addAll(newsManager.newsResponse.value.articles ?: listOf(TopNewsArticle()))

    Log.d("NewsArticles", "NewsArticles: $articles")

    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    Log.d("CurrentRoute", "currentRoute: $currentRoute")

    // Shows bottom bar only on selected list of routes
    val bottomBarVisible = currentRoute in listOf(
        BottomMenuScreens.TopNews.route,
        BottomMenuScreens.Categories.route,
        BottomMenuScreens.Sources.route
    )

    Scaffold(bottomBar = {
        if(bottomBarVisible)
            BottomMenu(navController, currentRoute)
    }) { innerPadding ->

        if (articles != null) {
            Navigation(innerPadding, navController, articles, newsManager)
        }
    }
}

@Composable
fun Navigation(innerPadding: PaddingValues, navController: NavHostController, articles: MutableList<TopNewsArticle>, newsManager: NewsManager) {


    NavHost(
        navController = navController,
        startDestination = BottomMenuScreens.TopNews.route    // startDestination represents first screen that will be shown after launching app
    ) {
        // Add the bottom navigation to the Navigation graph
        bottomNavigation(innerPadding, navController, articles, newsManager)

        /*
            Commented as it is called from bottomNavigation

            composable("TopNews") {
                TopNews(innerPadding, navController)
            }
         */

        composable("Details/{index}",                              // added a placeholder for the argument to be received
            arguments = listOf(navArgument("index"){
                type = NavType.IntType                                    // provide the argument type using NavType
            })) {    navBackStackEntry ->
                // receive the index from NavBackStackEntry, we use getInt since its of Int type and pass in the key
                val index = navBackStackEntry.arguments?.getInt("index")
                // pass in the id to getNewsDataFromId method created in MockData to retrieve selected news
                // val newsData = MockData.getNewsDataFromId(newsId)


                index?.let {
                    val topArticles = mutableListOf<TopNewsArticle>()
                    // If search text query is not empty, then show the articles from search text query otherwise show all the articles
                    if(newsManager.searchQuery.value != "") {
                        topArticles.clear()
                        topArticles.addAll(newsManager.searchQueryResponse.value.articles ?: listOf())
                    } else {
                        topArticles.clear()
                        topArticles.addAll(newsManager.newsResponse.value.articles ?: listOf())
                    }
                    val article = topArticles[index]
                    // provide newsData as a value to detail screen
                    DetailScreen(innerPadding, navController, article)
                }

        }

    }
}

fun NavGraphBuilder.bottomNavigation(innerPadding: PaddingValues, navController: NavController, articles: List<TopNewsArticle>, newsManager: NewsManager) {
    composable(BottomMenuScreens.TopNews.route) {
        TopNews(innerPadding, navController, articles, newsManager)
    }
    composable(BottomMenuScreens.Categories.route) {
        newsManager.getArticlesByCategory(ArticleCategory.BUSINESS.categoryName)    // Getting articles for "business" category if none of tabs are selected initially
        Categories(innerPadding, navController, newsManager)
    }
    composable(BottomMenuScreens.Sources.route) {
        Sources(innerPadding, navController, newsManager)
    }
}