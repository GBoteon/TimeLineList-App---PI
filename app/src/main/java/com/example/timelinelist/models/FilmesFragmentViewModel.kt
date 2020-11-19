package com.example.timelinelist.models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.timelinelist.helpers.Filme

class FilmesFragmentViewModel(application: Application): AndroidViewModel(application) {
    private val context = getApplication<Application>().applicationContext

    fun getFilmes(): ArrayList<Filme> {
        return arrayListOf( Filme("Mama mia"),
                            Filme("Os Estranhos - Caçada Noturna"),
                            Filme("A Wrinkle in Time"),
                            Filme("Love, Simon"),
                            Filme("John Wick 2"),
                            Filme("Burnt"),
                            Filme("Todo Dia"),
                            Filme("Jogador Numero Um"),
                            Filme("Continue Assistindo"),
                            Filme("Slender"),
                            Filme("Koe no Katashi"),
                            Filme("Truth or Dare"),
                            Filme("Próxima Parada: Apocalípse"),
                            Filme("Operação Red Sparrow"),
                            Filme("A Vida é Uma Festa"))
    }

}