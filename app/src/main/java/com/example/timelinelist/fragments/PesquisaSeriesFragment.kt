package com.example.timelinelist.fragments


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.timelinelist.R
import com.example.timelinelist.activities.DetalheSerieActivity
import com.example.timelinelist.adapters.ListaSeriePesquisaAdapter
import com.example.timelinelist.helpers.BaseSerieBusca
import com.example.timelinelist.viewmodels.PesquisaViewModel
import kotlinx.android.synthetic.main.fragment_pesquisafilmes.view.*
import kotlinx.android.synthetic.main.fragment_pesquisaseries.view.*

class PesquisaSeriesFragment : Fragment(), ListaSeriePesquisaAdapter.OnObraSerieClickListener {
    private val viewModel: PesquisaViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_pesquisaseries, container, false)
        viewModel.getPopularSeries()
        view.recyclerview_series_pesquisa.layoutManager = LinearLayoutManager(context)
        view.recyclerview_series_pesquisa.setHasFixedSize(true)
        viewModel.listaSeries.observe(viewLifecycleOwner) {
            println(it.toString())
            var adapter =  ListaSeriePesquisaAdapter(it, this)
            view.recyclerview_series_pesquisa.adapter = adapter
            adapter.notifyDataSetChanged()
            view.progressbar_loading_series.visibility = View.INVISIBLE
        }
        return view
    }
    override fun obraSerieClick(position: Int) {
        var idClick = viewModel.listaSeries.value?.results?.get(position)?.id as Int
        viewModel.getSeriesFromId(idClick)
        viewModel.listaSeriesDetalhe.observe(viewLifecycleOwner) {
            val intent = Intent(context, DetalheSerieActivity::class.java)
            intent.putExtra("serieClick", it)
            startActivity(intent)
        }

    }
    internal fun atualizaListaSeries(text:String) {
        var listaSerieNova = viewModel.getSeriesFromApi(text) as BaseSerieBusca
        var adapter =  ListaSeriePesquisaAdapter(listaSerieNova, this)
        view?.recyclerview_filmes_pesquisa?.adapter = adapter
        adapter.notifyDataSetChanged()
    }
}