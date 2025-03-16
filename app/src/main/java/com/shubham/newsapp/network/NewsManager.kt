package com.shubham.newsapp.network

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.shubham.newsapp.models.ArticleCategory
import com.shubham.newsapp.models.TopNewsResponse
import com.shubham.newsapp.models.getArticleCategory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsManager {

    private val _newsResponse = mutableStateOf(TopNewsResponse())       // private variable only used in this class
    val newsResponse: State<TopNewsResponse>                            // Getter of _newsResponse private variable
        @Composable get() = remember {
            _newsResponse
        }

    private val _articleByCategory = mutableStateOf(TopNewsResponse())              // private variable only used in this class
    val articleByCategory: MutableState<TopNewsResponse>                            // Getter of _articleByCategory private variable
        @Composable get() = remember {
            _articleByCategory
        }

    private val _articleBySources = mutableStateOf(TopNewsResponse())               // private variable only used in this class
    val articleBySources: MutableState<TopNewsResponse>                             // Getter of _articleBySources private variable
        @Composable get() = remember {
            _articleBySources
        }

    private val _searchQueryResponse = mutableStateOf(TopNewsResponse())               // private variable only used in this class
    val searchQueryResponse: MutableState<TopNewsResponse>                             // Getter of _searchQueryResponse private variable
        @Composable get() = remember {
            _searchQueryResponse
        }

    val selectedCategory: MutableState<ArticleCategory?> = mutableStateOf(ArticleCategory.BUSINESS)     // Selected category tab as "business" by default
    val sourceName = mutableStateOf("abc-news")
    val searchQuery = mutableStateOf("")

    init {
        getArticles()   // Called when NewsManager is initialized
    }

    private fun getArticles() {
        val service = ApiUtils.newsService.getTopNewsArticles(ApiUtils.COUNTRY_US, ApiUtils.API_KEY)

        service.enqueue(object: Callback<TopNewsResponse> {
            override fun onResponse(call: Call<TopNewsResponse>, response: Response<TopNewsResponse>) {
                if(response.isSuccessful) {
                    _newsResponse.value = response.body()!!     // Since we know response is successful so it is safe to use !!
                    Log.d("NewsResponse", "onResponse: ${response.message()}")
                } else {
                    Log.d("Error", "onResponse: ${_newsResponse.value}")
                }
            }

            override fun onFailure(call: Call<TopNewsResponse>, t: Throwable) {
                Log.d("Error", "onFailure: ${t.message}")
            }

        })
    }

    fun getArticlesByCategory(category: String) {
        val service = ApiUtils.newsService.getTopNewsArticlesByCategory(category, ApiUtils.API_KEY)

        service.enqueue(object: Callback<TopNewsResponse> {
            override fun onResponse(call: Call<TopNewsResponse>, response: Response<TopNewsResponse>) {
                if(response.isSuccessful) {
                    _articleByCategory.value = response.body()!!     // Since we know response is successful so it is safe to use !!
                    Log.d("ArticleByCategory", "onResponse: ${response.message()}")
                } else {
                    Log.d("Error", "onResponse: ${_articleByCategory.value}")
                }
            }

            override fun onFailure(call: Call<TopNewsResponse>, t: Throwable) {
                Log.d("Error", "onFailure: ${t.message}")
            }

        })
    }

    fun getArticlesBySources() {
        val service = ApiUtils.newsService.getTopNewsArticlesBySources(sourceName.value, ApiUtils.API_KEY)

        service.enqueue(object: Callback<TopNewsResponse> {
            override fun onResponse(call: Call<TopNewsResponse>, response: Response<TopNewsResponse>) {
                if(response.isSuccessful) {
                    _articleBySources.value = response.body()!!     // Since we know response is successful so it is safe to use !!
                    Log.d("ArticleBySources", "onResponse: ${response.message()}")
                } else {
                    Log.d("Error", "onResponse: ${_articleBySources.value}")
                }
            }

            override fun onFailure(call: Call<TopNewsResponse>, t: Throwable) {
                Log.d("Error", "onFailure: ${t.message}")
            }

        })
    }

    fun getArticlesByQuery(query: String) {
        val service = ApiUtils.newsService.getTopNewsArticlesByQuery(query, ApiUtils.API_KEY)

        service.enqueue(object: Callback<TopNewsResponse> {
            override fun onResponse(call: Call<TopNewsResponse>, response: Response<TopNewsResponse>) {
                if(response.isSuccessful) {
                    _searchQueryResponse.value = response.body()!!     // Since we know response is successful so it is safe to use !!
                    Log.d("ArticleByQuery", "onResponse: ${response.message()}")
                } else {
                    Log.d("Error", "onResponse: ${_searchQueryResponse.value}")
                }
            }

            override fun onFailure(call: Call<TopNewsResponse>, t: Throwable) {
                Log.d("Error", "onFailure: ${t.message}")
            }

        })
    }

    fun onSelectedCategoryChanged(categoryName: String) {
        val newCategory = getArticleCategory(categoryName)
        selectedCategory.value = newCategory
    }

}