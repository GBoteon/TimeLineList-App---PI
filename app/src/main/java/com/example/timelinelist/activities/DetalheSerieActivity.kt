package com.example.timelinelist.activities

import android.animation.LayoutTransition
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
import android.graphics.text.LineBreaker
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.View.GONE
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
import java.util.*


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
        if(intent.getStringExtra("origem")=="Pesquisa") {
            button_delete_serie.visibility = GONE
            button_salvareditar_serie.text = "SALVAR"
        } else {
            button_salvareditar_serie.text = "EDITAR"
        }
        imageview_voltar_serietolista.setOnClickListener {
            if(intent.getStringExtra("origem")=="ListaPessoal") {
                startActivity(Intent(this,ListaActivity::class.java))
            } else {
                startActivity(Intent(this,PesquisaActivity::class.java))
            }
        }
        textview_nomeserie.setOnClickListener {
            cardview_detalheposter_serie.visibility = View.VISIBLE
            cardview_detalheposter_serie.background.alpha = 150
        }
        cardview_detalheposter_serie.setOnClickListener {
            cardview_detalheposter_serie.visibility = View.INVISIBLE
        }
        edittext_nota_serie.setOnClickListener {
            cardview_rating_serie.visibility = View.VISIBLE
            cardview_detalheposter_serie.background.alpha = 150
        }
        ratingbar_nota_serie.setOnRatingBarChangeListener { _, rating, _ ->
            edittext_nota_serie.setText((rating*2).toString())
        }
        button_ok_nota_serie.setOnClickListener {
            cardview_rating_serie.visibility = View.INVISIBLE
            var value = ratingbar_nota_serie.rating
            edittext_nota_serie.setText((value*2).toString())
        }
        button_cancel_nota_serie.setOnClickListener {
            cardview_rating_serie.visibility = View.INVISIBLE
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
        val myCalendar: Calendar = Calendar.getInstance()
        val date =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, monthOfYear)
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateLabel(myCalendar)
            }
        edittext_data_serie.setOnClickListener {
            DatePickerDialog(
                this@DetalheSerieActivity, R.style.DialogTheme, date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
        var serieAtual = intent.getSerializableExtra("serieClick") as BaseSerieDetalhe
        if(intent.getStringExtra("origem")=="ListaPessoal") {
            // TODO
        }
        textview_nomeserie.isSelected = true
        textview_nomeserie.text = serieAtual.getTitulo()
        textview_quanttempserie.text = serieAtual.getTemporadas()
        textview_statusserie.text = serieAtual.getEstado()
        textview_lancamentoserie.text = serieAtual.getDataDeLancamento()
        textview_notaserie.text = serieAtual.getMediaVotos()

        textview_descricaoserie.text = serieAtual.getSinopse()
        var animSlide = AnimationUtils.loadAnimation(applicationContext, R.anim.up)
        if (serieAtual.getSinopse()=="") {
            root_descricaoserie.visibility= View.GONE
        } else {
            animSlide = AnimationUtils.loadAnimation(applicationContext, R.anim.up_without_description)
        }
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

        var poster = "${Constants.BASE_IMAGE_URL}.${serieAtual.getPoster()}"
        Picasso.get().load(Uri.parse(poster)).placeholder(R.drawable.ic_logo).into(imageview_serie)
        Picasso.get().load(Uri.parse(poster)).placeholder(R.drawable.ic_logo).into(imageview_detalheposter_serie)

        imageview_serie.startAnimation(animSlide)
        applyLayoutTransition()
    }
    private fun updateLabel(myCalendar: Calendar) {
        val myFormat = "dd/MM/yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        edittext_data_serie.setText(sdf.format(myCalendar.getTime()))
    }
    private fun applyLayoutTransition() {
        val transition = LayoutTransition()
        transition.setDuration(500)
        transition.enableTransitionType(LayoutTransition.CHANGING)
        root_descricaoserie.layoutTransition = transition
    }
}