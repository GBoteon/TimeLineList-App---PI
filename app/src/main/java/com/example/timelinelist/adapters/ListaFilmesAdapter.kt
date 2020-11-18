package com.example.timelinelist.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.timelinelist.R
import com.example.timelinelist.helpers.Filme

class ListaFilmesAdapter(private val listFilmes: ArrayList<Filme>): RecyclerView.Adapter<ListaFilmesAdapter.ListaFilmeViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListaFilmeViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.filme_item, parent, false)
        return ListaFilmeViewHolder(itemView)
    }

    override fun getItemCount() = listFilmes.size

    override fun onBindViewHolder(holder: ListaFilmeViewHolder, position: Int) {
        var filme = listFilmes.get(position)
        holder.nome_filme.text = filme.nome
    }
    class ListaFilmeViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val nome_filme: TextView = itemView.findViewById(R.id.nome_filme)
    }


}