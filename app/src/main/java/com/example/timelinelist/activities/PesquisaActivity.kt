package com.example.timelinelist.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.timelinelist.R
import com.example.timelinelist.adapters.ListaObrasAdapter
import com.example.timelinelist.models.PesquisaViewModel
import kotlinx.android.synthetic.main.activity_estatisticas.*
import kotlinx.android.synthetic.main.activity_pesquisa.*
import kotlinx.android.synthetic.main.fragment_filmes.view.*


class PesquisaActivity : AppCompatActivity(), ListaObrasAdapter.OnObraClickListener {
    private val viewModel: PesquisaViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pesquisa)
        imageview_voltar_pesquisatolista.setOnClickListener{ startActivity(
            Intent(
                this,
                ListaActivity::class.java
            )
        ) }

        edittext_busca.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                progressbar_loading.visibility = View.VISIBLE
                hideSoftKeyboard(this)
                viewModel.getObrasFromApi(edittext_busca.text.toString())
                return@OnEditorActionListener true
            }
            false
        })


        viewModel.listaObraFromApi.observe(this) {
            var adapter =  ListaObrasAdapter(it, this)
            recyclerview_obras.adapter = adapter
            recyclerview_obras.layoutManager = LinearLayoutManager(this)
            recyclerview_obras.setHasFixedSize(true)
            progressbar_loading.visibility = View.INVISIBLE
        }


    }


    override fun obraClick(position: Int) {
        Toast.makeText(this, "Click Obra", Toast.LENGTH_SHORT).show()
    }

    fun hideSoftKeyboard(activity: Activity) {
        val inputMethodManager: InputMethodManager = activity.getSystemService(
            INPUT_METHOD_SERVICE
        ) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(
            activity.currentFocus!!.windowToken, 0
        )
    }

}