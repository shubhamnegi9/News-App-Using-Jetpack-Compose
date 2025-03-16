package com.shubham.newsapp.network

import com.shubham.newsapp.models.TopNewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {
    @GET("top-headlines")
    fun getTopNewsArticles(
        @Query("country") country: String,
        @Query("apiKey") apiKey: String
    ): Call<TopNewsResponse>

    @GET("top-headlines")
    fun getTopNewsArticlesByCategory(
        @Query("category") category: String,
        @Query("apiKey") apiKey: String
    ): Call<TopNewsResponse>

    @GET("everything")
    fun getTopNewsArticlesBySources(
        @Query("sources") sources: String,
        @Query("apiKey") apiKey: String
    ): Call<TopNewsResponse>

    @GET("everything")
    fun getTopNewsArticlesByQuery(
        @Query("q") query: String,
        @Query("apiKey") apiKey: String
    ): Call<TopNewsResponse>
}