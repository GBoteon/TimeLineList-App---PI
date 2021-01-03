package com.example.timelinelist.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.timelinelist.Constants
import com.example.timelinelist.RepositoryFilmes
import com.example.timelinelist.helpers.BaseFilmeDetalhe
import com.example.timelinelist.helpers.EssencialFilme
import com.example.timelinelist.repository
import kotlinx.coroutines.launch

class FilmesFragmentViewModel(application: Application): AndroidViewModel(application) {

    var listaFilme = MutableLiveData<List<EssencialFilme>>()
    var filmeDetalhe = MutableLiveData<BaseFilmeDetalhe>()


    fun getFilmes() {
        listaFilme.value =  listOf( EssencialFilme(0,577922,"Tenet","/wzJRB4MKi3yK138bJyuL9nx47y6.jpg","12/12/1996",1,1,1,1,9.5f),
            EssencialFilme(1,524047,"Destruição Final: O Último Refúgio","/2Fk3AB8E9dYIBc2ywJkxk8BTyhc.jpg","01/08/2000",1,1,1,1,9.5f))
    }
    fun getFilmesFromId(id: Int) {
        viewModelScope.launch {
            filmeDetalhe.setValue(repository.getFilmeById(id,
                Constants.API_KEY,
                Constants.LANG))
        }
    }

}