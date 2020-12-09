package com.example.timelinelist.models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.timelinelist.Constants
import com.example.timelinelist.helpers.BaseFilmeDetalhe
import com.example.timelinelist.helpers.BaseSerieDetalhe
import com.example.timelinelist.helpers.Filme
import com.example.timelinelist.helpers.Serie
import com.example.timelinelist.repository
import kotlinx.coroutines.launch

class DetalheFilmeViewModel(application: Application): AndroidViewModel(application) {
    var listaFilmesDetalhe = MutableLiveData<BaseFilmeDetalhe>()

    fun getFilmesFromId(id: Int) {
        viewModelScope.launch {
            listaFilmesDetalhe.setValue(repository.getFilmeById(id,
                Constants.API_KEY,
                Constants.LANG))
        }
    }

}