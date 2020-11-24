package com.example.timelinelist.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.timelinelist.R
import kotlinx.android.synthetic.main.activity_estatisticas.*

class EstatisticasActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_estatisticas)
        imageview_voltar_estatisticastoperfil.setOnClickListener{ startActivity(Intent(this, PerfilActivity::class.java)) }
    }
}