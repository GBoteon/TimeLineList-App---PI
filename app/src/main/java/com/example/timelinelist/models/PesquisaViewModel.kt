package com.example.timelinelist.models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.timelinelist.Constants.API_KEY
import com.example.timelinelist.Constants.LANG
import com.example.timelinelist.helpers.BaseFilme
import com.example.timelinelist.helpers.BaseSerie
import com.example.timelinelist.helpers.Obra
import com.example.timelinelist.repository
import kotlinx.coroutines.launch

class PesquisaViewModel(application: Application): AndroidViewModel(application) {
    private val context = getApplication<Application>().applicationContext
    var listaObra = MutableLiveData<ArrayList<Obra>>()
    var listaObraFromApi = MutableLiveData<BaseFilme>()
    var listaPopularFilmes = MutableLiveData<BaseFilme>()
    var listaPopularSeries = MutableLiveData<BaseSerie>()

    fun getObras() {
        viewModelScope.launch {
            listaObra.value =  arrayListOf( Obra("Weastworld"),
                Obra("Station 19"),
                Obra("The Handmaid's Tale"),
                Obra("Siren"),
                Obra("Mr. Robot"),
                Obra("The Resident"),
                Obra("Big Little Lies"),
                Obra("La Casa de Papel"),
                Obra("Punisher"),
                Obra("Ozark"),
                Obra("O Atirador"),
                Obra("This is Us"),
                Obra("The Mist"),
                Obra("Unabomber"),
                Obra("Batalha de Confeiteiros 2018")
            )
        }
    }
    fun getObrasFromApi(query: String) {
        viewModelScope.launch {
            listaObraFromApi.value = repository.getFilmes(API_KEY,LANG,query)
        }
    }
    fun getPopularFilmes() {
        viewModelScope.launch {
            listaPopularFilmes.value = repository.getPopularFilmes(API_KEY,LANG)
        }
    }
    fun getPopularSeries() {
        viewModelScope.launch {
            listaPopularSeries.value = repository.getPopularSeries(API_KEY,LANG)
        }
    }

}