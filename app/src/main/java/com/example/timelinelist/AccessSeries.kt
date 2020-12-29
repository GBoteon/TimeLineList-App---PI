package com.example.timelinelist

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.timelinelist.helpers.BaseSerieDetalhe

@Dao
interface AccessSeries {
    @Query("SELECT * FROM series")
    suspend fun getAllSeries(): List<BaseSerieDetalhe>

    @Insert
    suspend fun addSeries(serie: BaseSerieDetalhe)

    @Update
    suspend fun updateSeries(serie: BaseSerieDetalhe)

    @Query("DELETE FROM series WHERE id = :serieId")
    suspend fun deleteSeries(serieId: Int)

    @Query("SELECT * FROM series WHERE id = :serieId")
    suspend fun selectSerie(serieId: Int)
}