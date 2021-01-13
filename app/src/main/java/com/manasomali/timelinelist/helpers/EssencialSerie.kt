package com.manasomali.timelinelist.helpers

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName="series")
data class EssencialSerie(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int?,
    @ColumnInfo(name = "serieid")
    val serieid: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "backdropPath")
    val backdropPath: String,
    @ColumnInfo(name = "dataAssistidoPessoal")
    var dataAssistidoPessoal: String,
    @ColumnInfo(name = "statusPessoal")
    var statusPessoal: String,
    @ColumnInfo(name = "notaPessoal")
    var notaPessoal: String
): Serializable