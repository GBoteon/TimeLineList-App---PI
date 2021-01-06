package com.example.timelinelist.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.timelinelist.Constants
import com.example.timelinelist.R
import com.example.timelinelist.helpers.EssencialSerie
import com.example.timelinelist.helpers.Serie
import com.squareup.picasso.Picasso

class ListaSeriesAdapter(private val listSerie: List<EssencialSerie>, val listener: OnSerieClickListener): RecyclerView.Adapter<ListaSeriesAdapter.ListaSeriesViewHolder>(), Filterable {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListaSeriesViewHolder {
        var itemView = LayoutInflater.from(parent.context).inflate(R.layout.serie_item, parent, false)
        return ListaSeriesViewHolder(itemView)
    }

    override fun getItemCount() = seriesFilterList.size

    override fun onBindViewHolder(holder: ListaSeriesViewHolder, position: Int) {
        var serie = seriesFilterList[position]
        holder.nome_serie.text = serie.name

        for(position in Constants.STATUS_SERIE_PESSOAL)
        {
            if (serie.statusPessoal==position) {
                holder.status_serie.text = position
            }
        }
        holder.data_serie.text = serie.dataAssistidoPessoal
        holder.notapessoal_serie.text = "${serie.notaPessoal}/10"
        Picasso.get().load(Uri.parse("${Constants.BASE_IMAGE_URL}${serie.backdropPath}")).placeholder(R.drawable.ic_logo).into(
            holder.backdrop_serie)

    }
    inner class ListaSeriesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val nome_serie: TextView = itemView.findViewById(R.id.textview_nome_serie)
        val status_serie: TextView = itemView.findViewById(R.id.textview_status_serie)
        val data_serie: TextView = itemView.findViewById(R.id.textview_data_serie)
        val notapessoal_serie: TextView = itemView.findViewById(R.id.textview_notapessoal_serie)
        val backdrop_serie: ImageView = itemView.findViewById(R.id.imageview_backdrop_serie)

        init {
            itemView.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            val position = adapterPosition
            if (RecyclerView.NO_POSITION != position) {
                listener.serieClick(position)
            }
        }
    }
    interface OnSerieClickListener {
        fun serieClick(position: Int)
    }

    var seriesFilterList = listOf<EssencialSerie>()

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
                    val resultList = ArrayList<EssencialSerie>()
                    for (row in listSerie) {
                        if (row.name.toLowerCase().contains(charSearch.toLowerCase())) {
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
                seriesFilterList = results?.values as ArrayList<EssencialSerie>
                notifyDataSetChanged()
            }

        }
    }
}