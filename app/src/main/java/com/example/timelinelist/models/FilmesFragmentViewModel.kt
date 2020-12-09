package com.example.timelinelist.models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.timelinelist.helpers.Filme
import com.example.timelinelist.helpers.Serie

class FilmesFragmentViewModel(application: Application): AndroidViewModel(application) {
    private val context = getApplication<Application>().applicationContext
    var listaFilme = MutableLiveData<ArrayList<Filme>>()
    fun getFilmes() {
        listaFilme.value =  arrayListOf( Filme("Weastworld"),
            Filme("Station 19"),
            Filme("The Handmaid's Tale"),
            Filme("Siren"),
            Filme("Mr. Robot"),
            Filme("The Resident"),
            Filme("Big Little Lies"),
            Filme("La Casa de Papel"),
            Filme("Punisher"),
            Filme("Ozark"),
            Filme("O Atirador"),
            Filme("This is Us"),
            Filme("The Mist"),
            Filme("Unabomber"),
            Filme("Batalha de Confeiteiros 2018"))
    }

}