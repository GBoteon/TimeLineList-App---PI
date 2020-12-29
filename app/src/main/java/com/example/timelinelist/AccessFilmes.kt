package com.example.timelinelist

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.timelinelist.helpers.BaseFilmeDetalhe

@Dao
interface AccessFilmes {
    @Query("SELECT * FROM filmes")
    suspend fun getAllFilmes(): List<BaseFilmeDetalhe>

    @Insert
    suspend fun addFilme(filme: BaseFilmeDetalhe)

    @Update
    suspend fun updateFilme(filme: BaseFilmeDetalhe)

    @Query("DELETE FROM filmes WHERE id = :filmeId")
    suspend fun deleteFilme(filmeId: Int)

    @Query("SELECT * FROM filmes WHERE id = :filmeId")
    suspend fun selectFilme(filmeId: Int)
}