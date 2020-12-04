package com.example.timelinelist.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import com.example.timelinelist.Constants
import com.example.timelinelist.R
import com.example.timelinelist.helpers.ResultsFilme
import com.example.timelinelist.helpers.ResultsSerie
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detalhefilme.*
import kotlinx.android.synthetic.main.activity_detalheserie.*
import kotlinx.android.synthetic.main.activity_detalheserie.imageview_compartilhar


class DetalheSerieActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalheserie)
        textview_nomeserie.isSelected = true
        imageview_voltar_serietolista.setOnClickListener { finish() }

        imageview_compartilhar.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, textview_nomeserie.text)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }

        var serieAtual = intent.getSerializableExtra("serieClick") as? ResultsSerie
        var poster = "${Constants.BASE_IMAGE_URL}.${serieAtual?.poster_path}"
        var titulo = serieAtual?.name

        textview_nomeserie.text = titulo
        Picasso.get().load(Uri.parse(poster)).into(imageview_serie)
        var animSlide = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.up)
        imageview_serie.startAnimation(animSlide)

    }
}