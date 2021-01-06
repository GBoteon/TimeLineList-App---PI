package com.example.timelinelist.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.timelinelist.*
import com.example.timelinelist.helpers.*
import kotlinx.coroutines.launch

class SeriesFragmentViewModel(private val repositoryDB: RepositorySeries) : ViewModel() {

    var listaSerie = MutableLiveData<List<EssencialSerie>>()
    var serieDetalhe = MutableLiveData<BaseSerieDetalhe>()

    fun getSeriesFromId(id: Int) {
        viewModelScope.launch {
            serieDetalhe.setValue(repository.getSerieById(id,
                Constants.API_KEY,
                Constants.LANG))
        }
    }

    fun getSeries() {
        viewModelScope.launch {
            listaSerie.value = repositoryDB.getAllSeriesTask()
        }
    }
    fun addSerie(serie: EssencialSerie){
        viewModelScope.launch {
            repositoryDB.addSerieTask(serie)
        }
    }
    fun updateSerie(serie: EssencialSerie){
        viewModelScope.launch {
            repositoryDB.updateSerieTask(serie)
        }
    }
    fun deleteSerie(id: Int){
        viewModelScope.launch {
            repositoryDB.deleteSerieTask(id)
        }
    }
}