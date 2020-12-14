package com.example.timelinelist.helpers

import java.io.Serializable

data class EssencialSerie(
    val id: Int,
    val name: String,
    val numberOfSeasons: Int,
    val status: String,
    val firstAirDate: String,
    val voteAverage: Double,
    val overview: String,
    val backdropPath: String,
    val posterPath: String,
    var statusPessoal: ArrayList<Boolean>
): Serializable {
    fun getId(): String {
        return id.toString()
    }
    fun getTitulo(): String {
        return name
    }
    fun getTemporadas(): String {
        return numberOfSeasons.toString()
    }
    fun getEstado(): String {
        return status
    }
    fun getDataDeLancamento(): String {
        return firstAirDate
    }
    fun getMediaVotos(): String {
        return voteAverage.toString()
    }
    fun getSinopse(): String {
        return overview
    }
    fun getWallpaper(): String {
        return backdropPath
    }
    fun getPoster(): String {
        return posterPath
    }
}