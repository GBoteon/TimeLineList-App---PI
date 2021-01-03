package com.example.timelinelist.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.timelinelist.AccessFilmes
import com.example.timelinelist.fragments.FilmesFragment
import com.example.timelinelist.helpers.EssencialFilme

@Database(entities = [EssencialFilme::class], version = 1, exportSchema=false)
abstract class BaseDadosFilmes: RoomDatabase(){

    abstract fun filmesDAO(): AccessFilmes

    companion object {
        @Volatile
        private var instance: BaseDadosFilmes? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance
            ?: synchronized(LOCK) {
            instance
                ?: buildDatabase(
                    context
                )
                    .also { instance = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context,
            BaseDadosFilmes::class.java, "app_data_base_filmes.db"
        ).build()
    }
}