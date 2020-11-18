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

class ListaFilmesAdapter(private val listFilmes: ArrayList<Filme>): RecyclerView.Adapter<ListaFilmesAdapter.ListaFilmeViewHolder>(), Filterable {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListaFilmeViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.filme_item, parent, false)
        return ListaFilmeViewHolder(itemView)
    }

    override fun getItemCount() = filmesFilterList.size

    override fun onBindViewHolder(holder: ListaFilmeViewHolder, position: Int) {
        var filme = filmesFilterList.get(position)
        holder.nome_filme.text = filme.nome
    }
    class ListaFilmeViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val nome_filme: TextView = itemView.findViewById(R.id.nome_filme)
    }

    var filmesFilterList = ArrayList<Filme>()

    init {
        filmesFilterList = listFilmes
    }
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    filmesFilterList = listFilmes
                } else {
                    val resultList = ArrayList<Filme>()
                    for (row in listFilmes) {
                        if (row.nome.toLowerCase().contains(charSearch.toLowerCase())) {
                            resultList.add(row)
                        }
                    }
                    filmesFilterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = filmesFilterList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filmesFilterList = results?.values as ArrayList<Filme>
                notifyDataSetChanged()
            }

        }
    }

}