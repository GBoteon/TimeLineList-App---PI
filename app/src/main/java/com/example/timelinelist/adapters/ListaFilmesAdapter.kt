package com.example.timelinelist.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.timelinelist.Constants
import com.example.timelinelist.R
import com.example.timelinelist.helpers.EssencialFilme
import com.squareup.picasso.Picasso
import java.util.*
import kotlin.collections.ArrayList

class ListaFilmesAdapter(
    private val listFilmes: List<EssencialFilme>,
    val listener: OnFilmeClickListener
): RecyclerView.Adapter<ListaFilmesAdapter.ListaFilmeViewHolder>(), Filterable {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListaFilmeViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.filme_item,
            parent,
            false)
        return ListaFilmeViewHolder(itemView)
    }

    override fun getItemCount() = filmesFilterList.size

    override fun onBindViewHolder(holder: ListaFilmeViewHolder, position: Int) {

        var filme = filmesFilterList[position]
        holder.nome_filme.text = filme.title
        holder.data_filme.text = filme.dataAssistidoPessoal
        holder.notapessoal_filme.text = "${filme.notaPessoal}/10"

        if(filme.cinema==1) {holder.imageview_cinema.visibility = VISIBLE}
        if(filme.dormiu==1) {holder.imageview_dormiu.visibility = VISIBLE}
        if(filme.chorou==1) {holder.imageview_chorou.visibility = VISIBLE}
        if(filme.favorito==1) {holder.imageview_favorito.visibility = VISIBLE}

        Picasso.get().load(Uri.parse("${Constants.BASE_IMAGE_URL}${filme.backdropPath}")).placeholder(
            R.drawable.ic_logo).into(
            holder.backdrop_filme)

    }
    inner class ListaFilmeViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val nome_filme: TextView = itemView.findViewById(R.id.textview_nome_filme)
        val data_filme: TextView = itemView.findViewById(R.id.textview_data_filme)
        val notapessoal_filme: TextView = itemView.findViewById(R.id.textview_notapessoal_filme)
        val imageview_cinema: ImageView = itemView.findViewById(R.id.imageview_cinema)
        val imageview_dormiu: ImageView = itemView.findViewById(R.id.imageview_dormiu)
        val imageview_chorou: ImageView = itemView.findViewById(R.id.imageview_chorou)
        val imageview_favorito: ImageView = itemView.findViewById(R.id.imageview_favorito)
        val backdrop_filme: ImageView = itemView.findViewById(R.id.imageview_backdrop_filme)
        init {
            itemView.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            val position = adapterPosition
            if (RecyclerView.NO_POSITION != position) {
                listener.filmeClick(position)
            }
        }
    }
    interface OnFilmeClickListener {
        fun filmeClick(position: Int)

    }

    var filmesFilterList = listOf<EssencialFilme>()

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
                    val resultList = ArrayList<EssencialFilme>()
                    for (row in listFilmes) {
                        if ((row.title.toLowerCase().contains(charSearch.toLowerCase()))||(row.dataAssistidoPessoal.toLowerCase().contains(
                                charSearch.toLowerCase()))) {
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
                filmesFilterList = results?.values as ArrayList<EssencialFilme>
                notifyDataSetChanged()
            }

        }
    }

}