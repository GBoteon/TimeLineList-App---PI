package com.example.timelinelist.fragments


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.timelinelist.R
import com.example.timelinelist.activities.DetalheSerieActivity
import com.example.timelinelist.adapters.ListaFilmesAdapter
import com.example.timelinelist.adapters.ListaSeriePesquisaAdapter
import com.example.timelinelist.adapters.ListaSeriesAdapter
import com.example.timelinelist.models.PesquisaViewModel
import com.example.timelinelist.models.SeriesFragmentViewModel
import kotlinx.android.synthetic.main.fragment_filmes.view.*
import kotlinx.android.synthetic.main.fragment_pesquisaseries.view.*
import kotlinx.android.synthetic.main.fragment_series.view.*

class PesquisaSeriesFragment : Fragment(), ListaSeriePesquisaAdapter.OnObraSerieClickListener {
    private val viewModel: PesquisaViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_pesquisaseries, container, false)
        viewModel.getPopularSeries()
        viewModel.listaPopularSeries.observe(viewLifecycleOwner) {
            var adapter =  ListaSeriePesquisaAdapter(it, this)
            view.recyclerview_series_pesquisa.adapter = adapter
            view.recyclerview_series_pesquisa.layoutManager = LinearLayoutManager(context)
            view.recyclerview_series_pesquisa.setHasFixedSize(true)
        }
        return view
    }
    override fun obraSerieClick(position: Int) {
        println(position)
        startActivity(Intent(context, DetalheSerieActivity::class.java))
    }
}