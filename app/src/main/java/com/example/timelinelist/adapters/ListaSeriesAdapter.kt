package com.example.timelinelist.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.timelinelist.R
import com.example.timelinelist.helpers.Filme
import com.example.timelinelist.helpers.Serie

class ListaSeriesAdapter(private val listSerie: ArrayList<Serie>): RecyclerView.Adapter<ListaSeriesAdapter.ListaSeriesViewHolder>(), Filterable {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListaSeriesViewHolder {
        var itemView = LayoutInflater.from(parent.context).inflate(R.layout.serie_item, parent, false)
        return ListaSeriesViewHolder(itemView)
    }

    override fun getItemCount() = seriesFilterList.size

    override fun onBindViewHolder(holder: ListaSeriesViewHolder, position: Int) {
        var serie = seriesFilterList.get(position)
        holder.nome_serie.text = serie.nome
    }
    class ListaSeriesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val nome_serie: TextView = itemView.findViewById(R.id.nome_serie)
    }


    var seriesFilterList = ArrayList<Serie>()

    init {
        seriesFilterList = listSerie
    }
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    seriesFilterList = listSerie
                } else {
                    val resultList = ArrayList<Serie>()
                    for (row in listSerie) {
                        if (row.nome.toLowerCase().contains(charSearch.toLowerCase())) {
                            resultList.add(row)
                        }
                    }
                    seriesFilterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = seriesFilterList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                seriesFilterList = results?.values as ArrayList<Serie>
                notifyDataSetChanged()
            }

        }
    }
}