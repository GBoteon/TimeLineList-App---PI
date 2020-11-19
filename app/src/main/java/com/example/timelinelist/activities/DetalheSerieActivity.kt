package com.example.timelinelist.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.timelinelist.R
import kotlinx.android.synthetic.main.activity_detalhefilme.*
import kotlinx.android.synthetic.main.activity_detalheserie.*

class DetalheSerieActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalheserie)
        imageview_voltar_serietolista.setOnClickListener { startActivity(Intent(this, ListaActivity::class.java)) }

    }
}