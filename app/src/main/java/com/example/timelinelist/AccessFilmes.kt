package com.example.timelinelist

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.timelinelist.helpers.BaseFilmeDetalhe
import com.example.timelinelist.helpers.EssencialFilme

@Dao
interface AccessFilmes {
    @Query("SELECT * FROM filmes")
    suspend fun getAllFilmes(): List<EssencialFilme>

    @Insert
    suspend fun addFilme(filme: EssencialFilme)

    @Update
    suspend fun updateFilme(filme: EssencialFilme)

    @Query("DELETE FROM filmes WHERE id = :filmeId")
    suspend fun deleteFilme(filmeId: Int)
}