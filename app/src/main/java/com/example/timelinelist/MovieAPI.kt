package com.example.timelinelist

import com.example.timelinelist.Constants.API_URL
import com.example.timelinelist.helpers.BaseFilme
import com.example.timelinelist.helpers.BaseSerie
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieAPI {

    @GET("movie")
    suspend fun getFilmeById(
        @Query("api_key") api: String,
        @Query("language") language: String
    ): BaseFilme

    @GET("serie")
    suspend fun getSerieById(
        @Query("api_key") api: String,
        @Query("language") language: String
    ): BaseSerie

    @GET("search/movie")
    suspend fun getFilmes(
        @Query("api_key") api: String,
        @Query("language") language: String,
        @Query("query") query: String
    ): BaseFilme
    @GET("search/serie")
    suspend fun getSeries(
        @Query("api_key") api: String,
        @Query("language") language: String,
        @Query("query") query: String
    ): BaseSerie
}


val retrofit = Retrofit.Builder()
    .baseUrl(API_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val repository: MovieAPI = retrofit.create(MovieAPI::class.java)