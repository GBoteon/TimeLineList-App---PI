package com.example.timelinelist.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.timelinelist.R
import kotlinx.android.synthetic.main.activity_detalhefilme.*

class DetalheFilmeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhefilme)
        imageview_voltar_filmetolista.setOnClickListener { startActivity(Intent(this, ListaActivity::class.java)) }
    }
}