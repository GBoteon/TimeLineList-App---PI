package com.example.timelinelist.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.timelinelist.R
import com.example.timelinelist.adapters.ListaFilmesAdapter
import com.example.timelinelist.adapters.ListaObrasAdapter
import com.example.timelinelist.models.FilmesFragmentViewModel
import com.example.timelinelist.models.PesquisaViewModel
import kotlinx.android.synthetic.main.activity_estatisticas.*
import kotlinx.android.synthetic.main.activity_pesquisa.*
import kotlinx.android.synthetic.main.fragment_filmes.view.*

class PesquisaActivity : AppCompatActivity(), ListaObrasAdapter.OnObraClickListener {
    private val viewModel: PesquisaViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pesquisa)
        imageview_voltar_pesquisatolista.setOnClickListener{ startActivity(Intent(this, ListaActivity::class.java)) }

        var listaObras = viewModel.getObras()
        var adapter =  ListaObrasAdapter(listaObras, this)

        recyclerview_obras.adapter = adapter
        recyclerview_obras.layoutManager = LinearLayoutManager(this)
        recyclerview_obras.setHasFixedSize(true)

    }

    override fun obraClick(position: Int) {
        Toast.makeText(this, "Click Obra", Toast.LENGTH_SHORT).show()
    }

}