package com.example.timelinelist.helpers

data class ResultsFilme (
	val id : Int,
	val popularity : Double,
	val vote_count : Int,
	val release_date : String,
	val adult : Boolean,
	val backdrop_path : String,
	val title : String,
	val genre_ids : List<Int>,
	val original_language : String,
	val original_title : String,
	val poster_path : String,
	val overview : String,
	val video : Boolean,
	val vote_average : Double
)