package com.example.timelinelist.fragments


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.timelinelist.R
import com.example.timelinelist.activities.DetalheSerieActivity
import com.example.timelinelist.adapters.ListaSeriesAdapter
import com.example.timelinelist.viewmodels.SeriesFragmentViewModel
import kotlinx.android.synthetic.main.fragment_series.view.*

class SeriesFragment : Fragment(), ListaSeriesAdapter.OnSerieClickListener {

    private val viewModel: SeriesFragmentViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_series, container, false)

        viewModel.getSeries()
        viewModel.listaSerie.observe(viewLifecycleOwner) {
            var adapter =  ListaSeriesAdapter(it, this)
            view.recyclerview_series.adapter = adapter
            view.recyclerview_series.layoutManager = LinearLayoutManager(context)
            view.recyclerview_series.setHasFixedSize(true)
            view.searchview_pesquisa_series.setOnQueryTextListener(object: androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }
                override fun onQueryTextChange(newText: String?): Boolean {
                    adapter.filter.filter(newText)
                    return false
                }
            })
        }


        return view
    }
    override fun serieClick(position: Int) {
        println(position)
        startActivity(Intent(context, DetalheSerieActivity::class.java))
    }
}