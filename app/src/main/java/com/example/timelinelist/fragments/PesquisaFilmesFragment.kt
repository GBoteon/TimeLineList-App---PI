package com.example.timelinelist.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.timelinelist.R
import com.example.timelinelist.models.FilmesFragmentViewModel
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.example.timelinelist.activities.DetalheFilmeActivity
import com.example.timelinelist.activities.ListaActivity
import com.example.timelinelist.adapters.ListaFilmePesquisaAdapter
import com.example.timelinelist.adapters.ListaFilmesAdapter
import com.example.timelinelist.adapters.ListaObrasAdapter
import com.example.timelinelist.adapters.ListaSeriePesquisaAdapter
import com.example.timelinelist.helpers.BaseFilme
import com.example.timelinelist.models.PesquisaViewModel
import kotlinx.android.synthetic.main.activity_pesquisa.*
import kotlinx.android.synthetic.main.filme_item.view.*
import kotlinx.android.synthetic.main.fragment_filmes.view.*
import kotlinx.android.synthetic.main.fragment_pesquisafilmes.*
import kotlinx.android.synthetic.main.fragment_pesquisafilmes.view.*
import kotlinx.android.synthetic.main.fragment_pesquisaseries.view.*
import kotlinx.android.synthetic.main.fragment_pesquisaseries.view.recyclerview_series_pesquisa

class PesquisaFilmesFragment : Fragment(), ListaFilmePesquisaAdapter.OnObraFilmeClickListener {
    private val viewModel: PesquisaViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_pesquisafilmes, container, false)
        viewModel.getPopularFilmes()
        view.recyclerview_filmes_pesquisa.layoutManager = LinearLayoutManager(context)
        view.recyclerview_filmes_pesquisa.setHasFixedSize(true)
        viewModel.listaFilmes.observe(viewLifecycleOwner) {
            println(it.toString())
            var adapter =  ListaFilmePesquisaAdapter(it, this)
            view.recyclerview_filmes_pesquisa.adapter = adapter
            adapter.notifyDataSetChanged()
        }

        return view

    }
    override fun obraFilmeClick(position: Int) {
        var filmeClick = viewModel.listaFilmes.value?.results?.get(position)
        val intent = Intent(context, DetalheFilmeActivity::class.java)
        intent.putExtra("filmeClick", filmeClick)
        startActivity(intent)
    }
    internal fun atualizaListaFilmes(text:String) {
        var listaNovaFilmes = viewModel.getFilmesFromApi(text) as BaseFilme
        var adapter =  ListaFilmePesquisaAdapter(listaNovaFilmes, this)
        view?.recyclerview_filmes_pesquisa?.adapter = adapter
        adapter.notifyDataSetChanged()
    }
}