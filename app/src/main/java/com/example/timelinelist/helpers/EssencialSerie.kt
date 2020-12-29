package com.example.timelinelist.helpers

import java.io.Serializable

data class EssencialSerie(
    val id: Int,
    val name: String,
    val backdropPath: String,
    var statusPessoal: ArrayList<Boolean>,
    var notaPessoal: Float
): Serializable