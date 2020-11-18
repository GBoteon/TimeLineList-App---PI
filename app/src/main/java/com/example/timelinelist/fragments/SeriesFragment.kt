package com.example.timelinelist.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.timelinelist.R
import com.example.timelinelist.adapters.ListaSeriesAdapter
import com.example.timelinelist.models.SeriesFragmentViewModel
import kotlinx.android.synthetic.main.fragment_series.view.*

class SeriesFragment : Fragment() {
    private val viewModel: SeriesFragmentViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater!!.inflate(R.layout.fragment_series, container, false)

        var listaSeries = viewModel.getSeries()
        var adapter =  ListaSeriesAdapter(listaSeries)

        view.recyclerview_series.adapter = adapter
        view.recyclerview_series.layoutManager = LinearLayoutManager(context)
        view.recyclerview_series.setHasFixedSize(true)

        return view
    }
}