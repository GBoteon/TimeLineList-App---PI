package com.example.timelinelist.helpers

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ServiceFilme{

    @GET("")
    suspend fun getObra(): ArrayList<Obra>
}
val keyFilme = "be06be958cb361e052a8f904860bd8bf"
var langFilme = "pt-BR"
val retrofitFilme = Retrofit.Builder()
    .baseUrl("https://api.themoviedb.org/3/movie/550?api_key=$keyFilme&language=$langFilme")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val serviceFilme:ServiceFilme = retrofitFilme.create(ServiceFilme::class.java)

// https://api.themoviedb.org/3/search/movie?api_key=be06be958cb361e052a8f904860bd8bf&language=en-US&query=the%20w&page=1&include_adult=false
// https://api.themoviedb.org/3/search/tv?api_key=be06be958cb361e052a8f904860bd8bf&language=en-US&page=1&query=the&include_adult=false
