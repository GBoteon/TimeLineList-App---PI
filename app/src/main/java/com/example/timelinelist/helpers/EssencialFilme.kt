package com.example.timelinelist.helpers

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName="filmes")
data class EssencialFilme(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val filmeid: Int,
    val title: String,
    val backdropPath: String,
    var dataAssistidoPessoal: String,
    var cinema: Int,
    var dormiu: Int,
    var chorou: Int,
    var favorito: Int,
    var notaPessoal: Float
): Serializable