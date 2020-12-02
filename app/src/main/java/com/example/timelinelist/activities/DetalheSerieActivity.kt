package com.example.timelinelist.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.timelinelist.R
import kotlinx.android.synthetic.main.activity_detalheserie.*
import kotlinx.android.synthetic.main.activity_detalheserie.imageview_compartilhar


class DetalheSerieActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalheserie)
        imageview_voltar_serietolista.setOnClickListener { startActivity(Intent(this, ListaActivity::class.java)) }

        imageview_compartilhar.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, textview_nomeserie.text)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
    }
}