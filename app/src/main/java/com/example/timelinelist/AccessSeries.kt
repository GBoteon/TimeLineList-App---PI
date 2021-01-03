package com.example.timelinelist

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.timelinelist.helpers.BaseSerieDetalhe
import com.example.timelinelist.helpers.EssencialSerie

@Dao
interface AccessSeries {
    @Query("SELECT * FROM series")
    suspend fun getAllSeries(): List<EssencialSerie>

    @Insert
    suspend fun addSeries(serie: EssencialSerie)

    @Update
    suspend fun updateSeries(serie: EssencialSerie)

    @Query("DELETE FROM series WHERE id = :serieId")
    suspend fun deleteSeries(serieId: Int)
}