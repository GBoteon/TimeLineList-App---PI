package com.example.timelinelist.helpers

data class BaseSerie(
    val results: ArrayList<ResultsSerie>,
    val total_results: Int,
    val page: Int,
    val total_pages: Int
)