package com.manasomali.timelinelist.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.manasomali.timelinelist.AccessFilmes
import com.manasomali.timelinelist.helpers.EssencialFilme

@Database(entities = [EssencialFilme::class], version = 1, exportSchema=true)
abstract class BaseDadosFilmes: RoomDatabase(){


    abstract fun filmesDAO(): AccessFilmes

    companion object {
        @Volatile
        private var instance: BaseDadosFilmes? = null
        private val LOCK = Any()

        operator fun invoke(context: Context?) = instance
            ?: synchronized(LOCK) {
                instance?: buildDatabase(context!!).also { instance = it }
            }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context,
            BaseDadosFilmes::class.java, "database_filmes.db"
        ).fallbackToDestructiveMigration().build()
    }

}