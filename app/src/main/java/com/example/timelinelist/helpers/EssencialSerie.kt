package com.example.timelinelist.helpers

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName="series")
data class EssencialSerie(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val serieid: Int,
    val name: String,
    val backdropPath: String,
    var dataAssistidoPessoal: String,
    var statusPessoal: String,
    var notaPessoal: Float
): Serializable