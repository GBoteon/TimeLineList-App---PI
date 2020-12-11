package com.example.timelinelist.fragments


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.timelinelist.R
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.example.timelinelist.activities.DetalheFilmeActivity
import com.example.timelinelist.adapters.ListaFilmePesquisaAdapter
import com.example.timelinelist.helpers.BaseFilmeBusca
import com.example.timelinelist.viewmodels.PesquisaViewModel
import kotlinx.android.synthetic.main.fragment_pesquisafilmes.view.*

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
            view.progressbar_loading_filmes.visibility = View.INVISIBLE
        }
        return view

    }
    override fun obraFilmeClick(position: Int) {
        var idClick = viewModel.listaFilmes.value?.results?.get(position)?.id as Int
        viewModel.getFilmesFromId(idClick)
        viewModel.listaFilmesDetalhe.observe(viewLifecycleOwner) {
            val intent = Intent(context, DetalheFilmeActivity::class.java)
            intent.putExtra("filmeClick", it)
            startActivity(intent)
        }

    }
    internal fun atualizaListaFilmes(text:String) {
        var listaNovaFilmes = viewModel.getFilmesFromApi(text) as BaseFilmeBusca
        var adapter =  ListaFilmePesquisaAdapter(listaNovaFilmes, this)
        view?.recyclerview_filmes_pesquisa?.adapter = adapter
        adapter.notifyDataSetChanged()
    }

}