package com.example.timelinelist.adapters

import android.R.attr.data
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.timelinelist.Constants.BASE_IMAGE_URL
import com.example.timelinelist.R
import com.example.timelinelist.helpers.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.obra_item.view.*


class ListaFilmePesquisaAdapter(
    private val listObra: BaseFilme,
    val listener: OnObraFilmeClickListener
): RecyclerView.Adapter<ListaFilmePesquisaAdapter.ListaFilmePesquisaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListaFilmePesquisaViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.obra_item,
            parent,
            false)
        return ListaFilmePesquisaViewHolder(itemView)
    }

    override fun getItemCount() = listObra.results.size

    override fun onBindViewHolder(holder: ListaFilmePesquisaViewHolder, position: Int) {

        var obra = listObra.results[position]
        holder.textview_nomeobra.text = obra.title
        Picasso.get().load(Uri.parse("$BASE_IMAGE_URL${obra.backdrop_path}")).into(holder.imageview_backdropobra)

    }
    inner class ListaFilmePesquisaViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val textview_nomeobra: TextView = itemView.textview_nomeobra
        val imageview_backdropobra: ImageView = itemView.imageview_backdropobra

        init {
            itemView.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            val position = adapterPosition
            if (RecyclerView.NO_POSITION != position) {
                listener.obraFilmeClick(position)
            }
        }
    }
    interface OnObraFilmeClickListener {
        fun obraFilmeClick(position: Int)
    }

}