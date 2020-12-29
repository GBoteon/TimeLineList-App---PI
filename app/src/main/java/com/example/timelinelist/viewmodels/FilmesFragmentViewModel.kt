package com.example.timelinelist.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.timelinelist.Constants
import com.example.timelinelist.helpers.BaseFilmeDetalhe
import com.example.timelinelist.helpers.EssencialFilme
import com.example.timelinelist.helpers.Filme
import com.example.timelinelist.repository
import kotlinx.coroutines.launch

class FilmesFragmentViewModel(application: Application): AndroidViewModel(application) {

    var listaFilme = MutableLiveData<ArrayList<EssencialFilme>>()
    var filmeDetalhe = MutableLiveData<BaseFilmeDetalhe>()


    fun getFilmes() {
        listaFilme.value =  arrayListOf( EssencialFilme(577922,"Tenet","/wzJRB4MKi3yK138bJyuL9nx47y6.jpg","12/12/1996",arrayListOf(false, true, false, true),9.5f),
            EssencialFilme(524047,"Destruição Final: O Último Refúgio","/2Fk3AB8E9dYIBc2ywJkxk8BTyhc.jpg","01/08/2000",arrayListOf(true, false, true, true),9.5f))
    }
    fun getFilmesFromId(id: Int) {
        viewModelScope.launch {
            filmeDetalhe.setValue(repository.getFilmeById(id,
                Constants.API_KEY,
                Constants.LANG))
        }
    }
}