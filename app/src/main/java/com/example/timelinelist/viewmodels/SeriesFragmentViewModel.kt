package com.example.timelinelist.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.timelinelist.Constants
import com.example.timelinelist.helpers.*
import com.example.timelinelist.repository
import kotlinx.coroutines.launch

class SeriesFragmentViewModel(application: Application): AndroidViewModel(application) {

    var listaSerie = MutableLiveData<List<EssencialSerie>>()
    var serieDetalhe = MutableLiveData<BaseSerieDetalhe>()

    fun getSeries() {
        listaSerie.value =  listOf( EssencialSerie(0,82856,"The Mandalorian","/o7qi2v4uWQ8bZ1tW3KI0Ztn2epk.jpg", "12/12/1996", "Acompanhando", 9.4f),
            EssencialSerie(1,71712,"The Good Doctor: O Bom Doutor","/zlXPNnnUlyg6KyvvjGd2ZxG6Tnw.jpg", "12/12/1996", "Acompanhando", 9.3f))

    }
    fun getSeriesFromId(id: Int) {
        viewModelScope.launch {
            serieDetalhe.setValue(repository.getSerieById(id,
                Constants.API_KEY,
                Constants.LANG))
        }
    }
}