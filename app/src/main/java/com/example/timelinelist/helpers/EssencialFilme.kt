package com.example.timelinelist.helpers

import java.io.Serializable

data class EssencialFilme(
    val id: Int,
    val title: String,
    val runtime: Int,
    val status: String,
    val releaseDate: String,
    val voteAverage: Double,
    val overview: String,
    val backdropPath: String,
    val posterPath: String,
    var dataAssistidoPessoal: String,
    var informacoesPessoal: ArrayList<Boolean>
): Serializable {
    fun getId(): String {
        return id.toString()
    }
    fun getTitulo(): String {
        return title
    }
    fun getTempo(): String {
        return title
    }
    fun getEstado(): String {
        return status
    }
    fun getDataDeLancamento(): String {
        return releaseDate
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
        return posterPath.toString()
    }
}