package com.example.timelinelist.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.timelinelist.R
import com.example.timelinelist.models.FilmesFragmentViewModel
import androidx.fragment.app.viewModels
import com.example.timelinelist.adapters.ListaFilmesAdapter
import kotlinx.android.synthetic.main.fragment_filmes.*
import kotlinx.android.synthetic.main.fragment_filmes.view.*


class FilmesFragment : Fragment() {
    private val viewModel: FilmesFragmentViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater!!.inflate(R.layout.fragment_filmes, container, false)

        var listaFilmes = viewModel.getFilmes()
        var adapter =  ListaFilmesAdapter(listaFilmes)

        view.recyclerview_filmes.adapter = adapter
        view.recyclerview_filmes.layoutManager = LinearLayoutManager(context)
        view.recyclerview_filmes.setHasFixedSize(true)
        return view

    }
}