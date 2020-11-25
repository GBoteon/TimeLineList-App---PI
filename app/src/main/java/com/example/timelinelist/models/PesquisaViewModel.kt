package com.example.timelinelist.models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.timelinelist.helpers.Filme
import com.example.timelinelist.helpers.Obra

class PesquisaViewModel(application: Application): AndroidViewModel(application) {
    private val context = getApplication<Application>().applicationContext

    fun getObras(): ArrayList<Obra> {
        return arrayListOf( Obra("Mama mia"),
            Obra("Os Estranhos - Caçada Noturna"),
            Obra("A Wrinkle in Time"),
            Obra("Love, Simon"),
            Obra("John Wick 2"),
            Obra("Burnt"),
            Obra("Todo Dia"),
            Obra("Jogador Numero Um"),
            Obra("Continue Assistindo"),
            Obra("Slender"),
            Obra("Koe no Katashi"),
            Obra("Truth or Dare"),
            Obra("Próxima Parada: Apocalípse"),
            Obra("Operação Red Sparrow"),
            Obra("A Vida é Uma Festa"))
    }

}