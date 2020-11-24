package com.example.timelinelist.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.timelinelist.R
import com.example.timelinelist.fragments.FilmesFragment
import com.example.timelinelist.helpers.Filme
import com.example.timelinelist.helpers.Obra

class ListaObrasAdapter(private val listObra: ArrayList<Obra>, val listener: OnObraClickListener): RecyclerView.Adapter<ListaObrasAdapter.ListaObraViewHolder>(), Filterable {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListaObraViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.obra_item, parent, false)
        return ListaObraViewHolder(itemView)
    }

    override fun getItemCount() = obraFilterList.size

    override fun onBindViewHolder(holder: ListaObraViewHolder, position: Int) {

        var filme = obraFilterList[position]
        holder.nome_obra.text = filme.nome
    }
    inner class ListaObraViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val nome_obra: TextView = itemView.findViewById(R.id.nome_obra)
        init {
            itemView.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            val position = adapterPosition
            if (RecyclerView.NO_POSITION != position) {
                listener.obraClick(position)
            }
        }
    }
    interface OnObraClickListener {
        fun obraClick(position: Int)

    }

    var obraFilterList = ArrayList<Obra>()

    init {
        obraFilterList = listObra
    }
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    obraFilterList = listObra
                } else {
                    val resultList = ArrayList<Obra>()
                    for (row in listObra) {
                        if (row.nome.toLowerCase().contains(charSearch.toLowerCase())) {
                            resultList.add(row)
                        }
                    }
                    obraFilterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = obraFilterList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                obraFilterList = results?.values as ArrayList<Obra>
                notifyDataSetChanged()
            }

        }
    }

}