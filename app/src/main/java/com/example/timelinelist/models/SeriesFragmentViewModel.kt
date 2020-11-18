package com.example.timelinelist.models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.timelinelist.helpers.Filme
import com.example.timelinelist.helpers.Serie

class SeriesFragmentViewModel(application: Application): AndroidViewModel(application) {
    private val context = getApplication<Application>().applicationContext
    fun getSeries(): ArrayList<Serie> {
        return arrayListOf(Serie("The Walking Dead"), Serie("The Mandalorian"))
    }
}