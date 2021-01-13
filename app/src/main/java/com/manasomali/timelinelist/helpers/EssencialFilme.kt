package com.manasomali.timelinelist.helpers

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName="filmes")
data class EssencialFilme(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int?,
    @ColumnInfo(name = "filmeid")
    val filmeid: Int,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "backdropPath")
    val backdropPath: String,
    @ColumnInfo(name = "dataAssistidoPessoal")
    var dataAssistidoPessoal: String,
    @ColumnInfo(name = "cinema")
    var cinema: Int,
    @ColumnInfo(name = "dormiu")
    var dormiu: Int,
    @ColumnInfo(name = "chorou")
    var chorou: Int,
    @ColumnInfo(name = "favorito")
    var favorito: Int,
    @ColumnInfo(name = "notaPessoal")
    var notaPessoal: String
): Serializable