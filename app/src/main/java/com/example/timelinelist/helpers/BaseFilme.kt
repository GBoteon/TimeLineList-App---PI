package com.example.timelinelist.helpers

data class BaseFilme (
	val results : ArrayList<ResultsFilme>,
	val total_results : Int,
	val page : Int,
	val total_pages : Int
)