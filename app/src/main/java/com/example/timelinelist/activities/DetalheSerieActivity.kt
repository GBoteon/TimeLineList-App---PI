package com.example.timelinelist.activities

import android.animation.LayoutTransition
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
import android.graphics.text.LineBreaker
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.timelinelist.Constants
import com.example.timelinelist.R
import com.example.timelinelist.helpers.BaseSerieDetalhe
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detalhefilme.*
import kotlinx.android.synthetic.main.activity_detalheserie.*
import kotlinx.android.synthetic.main.activity_detalheserie.imageview_compartilhar
import java.text.SimpleDateFormat


class DetalheSerieActivity : AppCompatActivity() {

    companion object {
        private const val MAX_LINES_COLLAPSED = 3
        private const val INITIAL_IS_COLLAPSED = true
    }
    private var isCollapsed = INITIAL_IS_COLLAPSED

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalheserie)
        requestedOrientation = SCREEN_ORIENTATION_PORTRAIT

        textview_nomeserie.isSelected = true
        imageview_voltar_serietolista.setOnClickListener {
            startActivity(Intent(this,PesquisaActivity::class.java))
        }

        imageview_compartilhar.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, textview_nomeserie.text)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }

        var serieAtual = intent.getSerializableExtra("serieClick") as BaseSerieDetalhe
        var poster = "${Constants.BASE_IMAGE_URL}.${serieAtual.posterPath}"
        var titulo = serieAtual.name
        var descricao = serieAtual.overview
        var quanttemp = serieAtual.numberOfSeasons
        var status = ""
        for (stat in Constants.STATUS_SERIE) {
            if(serieAtual.status==stat.key)
                status = stat.value
        }
        var lancamento = serieAtual.firstAirDate
        var nota = serieAtual.voteAverage
        var animSlide = AnimationUtils.loadAnimation(applicationContext, R.anim.up)
        if (descricao=="") {
            root_descricaoserie.visibility= View.GONE
        } else {
            animSlide = AnimationUtils.loadAnimation(applicationContext, R.anim.up_without_description)
        }
        var data = ""
        val formatter = SimpleDateFormat("yyyy")
        val dateFormat = SimpleDateFormat("yyyy-mm-dd")
        if (lancamento!="") {
            @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
            data = formatter.format(dateFormat.parse(lancamento))
        }

        textview_nomeserie.text = titulo
        textview_descricaoserie.text = descricao
        textview_quanttempserie.text = if (quanttemp==1) "$quanttemp Temp" else "$quanttemp Temps"
        textview_statusserie.text = status
        textview_lancamentoserie.text = data
        textview_notaserie.text = "$nota/10"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            textview_descricaoserie.justificationMode = LineBreaker.JUSTIFICATION_MODE_INTER_WORD
        }
        textview_descricaoserie.setOnClickListener {
            if (isCollapsed) {
                textview_descricaoserie.maxLines = Int.MAX_VALUE
            } else {
                textview_descricaoserie.maxLines = Companion.MAX_LINES_COLLAPSED
            }
            isCollapsed = !isCollapsed
        }

        Picasso.get().load(Uri.parse(poster)).placeholder(R.drawable.ic_logo).into(imageview_serie)


        imageview_serie.startAnimation(animSlide)
        applyLayoutTransition()
    }

    private fun applyLayoutTransition() {
        val transition = LayoutTransition()
        transition.setDuration(500)
        transition.enableTransitionType(LayoutTransition.CHANGING)
        root_descricaoserie.layoutTransition = transition
    }
}