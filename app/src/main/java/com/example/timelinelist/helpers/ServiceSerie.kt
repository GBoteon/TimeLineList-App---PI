package com.example.timelinelist.helpers

import android.app.Service
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ServiceSerie{

    @GET("")
    suspend fun getObra(): ArrayList<Obra>
}
val keySerie = "be06be958cb361e052a8f904860bd8bf"
var langSerie = "pt-BR"
val retrofitSerie = Retrofit.Builder()
    .baseUrl("https://api.themoviedb.org/3/movie/550?api_key=$keySerie&language=$langSerie")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val serviceSerie: ServiceSerie = retrofitSerie.create(ServiceSerie::class.java)

// https://api.themoviedb.org/3/search/movie?api_key=be06be958cb361e052a8f904860bd8bf&language=en-US&query=the%20w&page=1&include_adult=false
// https://api.themoviedb.org/3/search/tv?api_key=be06be958cb361e052a8f904860bd8bf&language=en-US&page=1&query=the&include_adult=false
