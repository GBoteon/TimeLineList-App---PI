package com.example.timelinelist.models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.timelinelist.helpers.Filme

class FilmesFragmentViewModel(application: Application): AndroidViewModel(application) {
    private val context = getApplication<Application>().applicationContext

    fun getFilmes(): ArrayList<Filme> {
        return arrayListOf(Filme("A volta dos que não foram"), Filme("Vovozona"))
    }

}