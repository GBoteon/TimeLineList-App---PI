package com.example.timelinelist.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.timelinelist.Constants.API_KEY
import com.example.timelinelist.Constants.LANG
import com.example.timelinelist.helpers.*
import com.example.timelinelist.repository
import kotlinx.coroutines.launch

class PesquisaViewModel(application: Application): AndroidViewModel(application) {
    private val context = getApplication<Application>().applicationContext
    var listaObra = MutableLiveData<ArrayList<Obra>>()
    var listaFilmes = MutableLiveData<BaseFilmeBusca>()
    var listaSeries = MutableLiveData<BaseSerieBusca>()
    var listaGeneros = MutableLiveData<BaseGenres>()
    var listaFilmesDetalhe = MutableLiveData<BaseFilmeDetalhe>()
    var listaSeriesDetalhe = MutableLiveData<BaseSerieDetalhe>()

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
    fun getSeriesFromApi(query: String): BaseSerieBusca? {
        viewModelScope.launch {
            listaSeries.setValue(repository.getSeries(API_KEY,LANG,query))
        }
        return listaSeries.value
    }
    fun getFilmesFromApi(query: String): BaseFilmeBusca? {
        viewModelScope.launch {
            listaFilmes.setValue(repository.getFilmes(API_KEY,LANG,query))
        }
        return listaFilmes.value
    }
    fun getPopularFilmes() {
        viewModelScope.launch {
            listaFilmes.setValue(repository.getPopularFilmes(API_KEY,LANG))
        }
    }
    fun getPopularSeries() {
        viewModelScope.launch {
            listaSeries.setValue(repository.getPopularSeries(API_KEY,LANG))
        }
    }
    fun getFilmesFromId(id: Int) {
        viewModelScope.launch {
            listaFilmesDetalhe.setValue(repository.getFilmeById(id,API_KEY,LANG))
        }
    }
    fun getSeriesFromId(id: Int) {
        viewModelScope.launch {
            listaSeriesDetalhe.setValue(repository.getSerieById(id,API_KEY,LANG))
        }
    }
    fun getGenres(tipo:String): BaseGenres? {
        viewModelScope.launch {
            listaGeneros.setValue(repository.getGenero(tipo,API_KEY,LANG))
        }
        return listaGeneros.value
    }

}