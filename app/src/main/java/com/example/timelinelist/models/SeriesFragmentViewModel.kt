package com.example.timelinelist.models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.timelinelist.helpers.Filme
import com.example.timelinelist.helpers.Serie

class SeriesFragmentViewModel(application: Application): AndroidViewModel(application) {
    private val context = getApplication<Application>().applicationContext
    var listaSerie = MutableLiveData<ArrayList<Serie>>()
    fun getSeries() {
        listaSerie.value =  arrayListOf( Serie("Weastworld"),
                            Serie("Station 19"),
                            Serie("The Handmaid's Tale"),
                            Serie("Siren"),
                            Serie("Mr. Robot"),
                            Serie("The Resident"),
                            Serie("Big Little Lies"),
                            Serie("La Casa de Papel"),
                            Serie("Punisher"),
                            Serie("Ozark"),
                            Serie("O Atirador"),
                            Serie("This is Us"),
                            Serie("The Mist"),
                            Serie("Unabomber"),
                            Serie("Batalha de Confeiteiros 2018"))
    }
}