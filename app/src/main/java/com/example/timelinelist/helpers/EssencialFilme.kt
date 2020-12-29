package com.example.timelinelist.helpers

import android.annotation.SuppressLint
import com.example.timelinelist.Constants
import java.io.Serializable
import java.text.SimpleDateFormat

data class EssencialFilme(
    val id: Int,
    val title: String,
    val backdropPath: String,
    var dataAssistidoPessoal: String,
    var informacoesPessoal: ArrayList<Boolean>,
    var notaPessoal: Float
): Serializable