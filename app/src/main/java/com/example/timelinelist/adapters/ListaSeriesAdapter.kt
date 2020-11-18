package com.example.timelinelist.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.timelinelist.R
import com.example.timelinelist.helpers.Serie

class ListaSeriesAdapter(private val listSerie: ArrayList<Serie>): RecyclerView.Adapter<ListaSeriesAdapter.ListaSeriesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListaSeriesViewHolder {
        var itemView = LayoutInflater.from(parent.context).inflate(R.layout.serie_item, parent, false)
        return ListaSeriesViewHolder(itemView)
    }

    override fun getItemCount() = listSerie.size

    override fun onBindViewHolder(holder: ListaSeriesViewHolder, position: Int) {
        var serie = listSerie.get(position)
        holder.nome_serie.text = serie.nome
    }
    class ListaSeriesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val nome_serie: TextView = itemView.findViewById(R.id.nome_serie)
    }


}