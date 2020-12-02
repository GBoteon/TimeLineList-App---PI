package com.example.timelinelist.helpers

data class ResultsSerie (

	val id : Int,
	val popularity : Double,
	val name : String,
	val original_name : String,
	val first_air_date : String,
	val backdrop_path : String,
	val vote_average : Double,
	val genre_ids : List<Int>,
	val overview : String,
	val original_language : String,
	val vote_count : Int,
	val poster_path : String,
	val origin_country : List<String>

)