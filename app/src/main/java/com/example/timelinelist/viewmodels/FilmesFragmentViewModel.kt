package com.example.timelinelist.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.timelinelist.*
import com.example.timelinelist.helpers.BaseFilmeDetalhe
import com.example.timelinelist.helpers.EssencialFilme
import kotlinx.coroutines.launch

class FilmesFragmentViewModel(private val repositoryDB: RepositoryFilmes) : ViewModel() {

    var listaFilme = MutableLiveData<List<EssencialFilme>>()
    var filmeDetalhe = MutableLiveData<BaseFilmeDetalhe>()

    fun getFilmesFromId(id: Int) {
        viewModelScope.launch {
            filmeDetalhe.setValue(repository.getFilmeById(id,
                Constants.API_KEY,
                Constants.LANG))
        }
    }
    fun getFilmes() {
        viewModelScope.launch {
            listaFilme.value = repositoryDB.getAllFilmesTask()
        }
    }
    fun addFilme(filme: EssencialFilme){
        viewModelScope.launch {
            repositoryDB.addFilmeTask(filme)
        }
    }
    fun updateFilme(filme: EssencialFilme){
        viewModelScope.launch {
            repositoryDB.updateFilmeTask(filme)
        }
    }
    fun deleteFilme(id: Int){
        viewModelScope.launch {
            repositoryDB.deleteFilmeTask(id)
        }
    }

}