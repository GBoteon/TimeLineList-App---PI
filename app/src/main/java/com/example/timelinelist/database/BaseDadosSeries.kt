package com.example.timelinelist.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.timelinelist.AccessFilmes
import com.example.timelinelist.AccessSeries
import com.example.timelinelist.helpers.EssencialSerie

@Database(entities = [EssencialSerie::class], version = 1, exportSchema=true)
abstract class BaseDadosSeries: RoomDatabase(){

    abstract fun seriesDAO() : AccessSeries

    companion object {
        @Volatile
        private var instance: BaseDadosSeries? = null
        private val LOCK = Any()

        operator fun invoke(context: Context?) = instance
            ?: synchronized(LOCK) {
                instance?: buildDatabase(context!!).also { instance = it }
            }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context,
            BaseDadosSeries::class.java, "database_series.db"
        ).fallbackToDestructiveMigration().build()
    }
}