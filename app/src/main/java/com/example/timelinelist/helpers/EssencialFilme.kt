package com.example.timelinelist.helpers

import android.annotation.SuppressLint
import com.example.timelinelist.Constants
import java.io.Serializable
import java.text.SimpleDateFormat

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
        return if (runtime.toString()==""||runtime.toString()=="0") {
            ""
        } else {
            "$runtime min"
        }
    }
    fun getEstado(): String {
        for (stat in Constants.STATUS_FILME) {
            if(status==stat.key)
                return stat.value
        }
        return ""
    }
    @SuppressLint("SimpleDateFormat")
    fun getDataDeLancamento(): String {
        val formatter = SimpleDateFormat("yyyy")
        val dateFormat = SimpleDateFormat("yyyy-mm-dd")
        return if (releaseDate!="") {
            @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
            formatter.format(dateFormat.parse(releaseDate))
        } else {
            ""
        }
    }
    fun getMediaVotos(): String {
        return "${voteAverage}/10"
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