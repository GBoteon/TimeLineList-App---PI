package com.example.timelinelist

import com.example.timelinelist.Constants.API_URL
import com.example.timelinelist.helpers.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieAPI {

    @GET("movie/{id}")
    suspend fun getFilmeById(
        @Path("id") id: Int,
        @Query("api_key") api: String,
        @Query("language") language: String
    ): BaseFilmeDetalhe

    @GET("tv/{id}")
    suspend fun getSerieById(
        @Path("id") id: Int,
        @Query("api_key") api: String,
        @Query("language") language: String
    ): BaseSerieDetalhe

    @GET("movie/popular")
    suspend fun getPopularFilmes(
        @Query("api_key") api: String,
        @Query("language") language: String
    ): BaseFilmeBusca

    @GET("tv/popular")
    suspend fun getPopularSeries(
        @Query("api_key") api: String,
        @Query("language") language: String
    ): BaseSerieBusca

    @GET("search/movie")
    suspend fun getFilmes(
        @Query("api_key") api: String,
        @Query("language") language: String,
        @Query("query") query: String
    ): BaseFilmeBusca

    @GET("search/tv")
    suspend fun getSeries(
        @Query("api_key") api: String,
        @Query("language") language: String,
        @Query("query") query: String
    ): BaseSerieBusca

    @GET("genre/{tipo}/list")
    suspend fun getGenero(
        @Path("tipo") tipo: String,
        @Query("api_key") api: String,
        @Query("language") language: String
    ): BaseGenres

}
val retrofit = Retrofit.Builder()
    .baseUrl(API_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val repository: MovieAPI = retrofit.create(MovieAPI::class.java)