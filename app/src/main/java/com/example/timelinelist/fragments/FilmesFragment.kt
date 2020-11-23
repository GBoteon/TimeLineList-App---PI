package com.example.timelinelist.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.timelinelist.R
import com.example.timelinelist.models.FilmesFragmentViewModel
import androidx.fragment.app.viewModels
import com.example.timelinelist.activities.DetalheFilmeActivity
import com.example.timelinelist.activities.ListaActivity
import com.example.timelinelist.adapters.ListaFilmesAdapter
import kotlinx.android.synthetic.main.fragment_filmes.view.*

class FilmesFragment : Fragment(), ListaFilmesAdapter.OnFilmeClickListener {

    private val viewModel: FilmesFragmentViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_filmes, container, false)

        var listaFilmes = viewModel.getFilmes()
        var adapter =  ListaFilmesAdapter(listaFilmes, this)

        view.recyclerview_filmes.adapter = adapter
        view.recyclerview_filmes.layoutManager = LinearLayoutManager(context)
        view.recyclerview_filmes.setHasFixedSize(true)

        view.searchview_pesquisa_filmes.setOnQueryTextListener(object: androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }

        })

        return view

    }
    override fun filmeClick(position: Int) {
        println(position)
        startActivity(Intent(context, DetalheFilmeActivity::class.java))
    }
}