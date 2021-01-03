package com.example.timelinelist.activities

import android.animation.LayoutTransition
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
import android.graphics.text.LineBreaker
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.view.View.*
import android.view.animation.AnimationUtils
import android.widget.RatingBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.timelinelist.Constants.BASE_IMAGE_URL
import com.example.timelinelist.R
import com.example.timelinelist.helpers.BaseFilmeDetalhe
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detalhefilme.*
import kotlinx.android.synthetic.main.activity_detalhefilme.imageview_compartilhar
import kotlinx.android.synthetic.main.activity_detalheserie.*
import java.text.SimpleDateFormat
import java.util.*


class DetalheFilmeActivity : AppCompatActivity() {

    companion object {
        private const val MAX_LINES_COLLAPSED = 3
        private const val INITIAL_IS_COLLAPSED = true
    }
    private var isCollapsed = INITIAL_IS_COLLAPSED

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhefilme)
        requestedOrientation = SCREEN_ORIENTATION_PORTRAIT
        if(intent.getStringExtra("origem")=="Pesquisa") {
            button_delete_filme.visibility = GONE
            button_salvareditar_filme.text = "SALVAR"
        } else {
            button_salvareditar_filme.text = "EDITAR"
        }
        imageview_voltar_filmetolista.setOnClickListener {
            if(intent.getStringExtra("origem")=="ListaPessoal") {
                startActivity(Intent(this,ListaActivity::class.java))
            } else {
                startActivity(Intent(this,PesquisaActivity::class.java))
            }
        }
        textview_nomefilme.setOnClickListener {
            cardview_detalheposter_filme.visibility = View.VISIBLE
            cardview_detalheposter_filme.background.alpha = 150
        }
        cardview_detalheposter_filme.setOnClickListener {
            cardview_detalheposter_filme.visibility = View.INVISIBLE
        }
        edittext_nota_filme.setOnClickListener {
            cardview_rating_filme.visibility = VISIBLE
            cardview_detalheposter_filme.background.alpha = 150
        }
        ratingbar_nota_filme.setOnRatingBarChangeListener { _, rating, _ ->
            edittext_nota_filme.setText((rating*2).toString())
        }
        button_ok_nota_filme.setOnClickListener {
            cardview_rating_filme.visibility = INVISIBLE
            var value = ratingbar_nota_filme.rating
            edittext_nota_filme.setText((value*2).toString())
        }
        button_cancel_nota_filme.setOnClickListener {
            cardview_rating_filme.visibility = INVISIBLE
        }
        imageview_compartilhar.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, textview_nomefilme.text)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
        val myCalendar: Calendar = Calendar.getInstance()
        val date =
            OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, monthOfYear)
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateLabel(myCalendar)
            }
        edittext_data_filme.setOnClickListener {
            DatePickerDialog(
                this@DetalheFilmeActivity, R.style.DialogTheme, date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        var filmeAtual = intent.getSerializableExtra("filmeClick") as BaseFilmeDetalhe
        if(intent.getStringExtra("origem")=="ListaPessoal") {
            // TODO
        }
        textview_nomefilme.isSelected = true
        textview_nomefilme.text = filmeAtual.getTitulo()
        textview_duracaofilme.text = filmeAtual.getTempo()
        textview_statusfilme.text = filmeAtual.getEstado()
        textview_lancamentofilme.text = filmeAtual.getDataDeLancamento()
        textview_notafilme.text = filmeAtual.getMediaVotos()

        textview_descricaofilme.text = filmeAtual.getSinopse()
        var animSlide = AnimationUtils.loadAnimation(applicationContext, R.anim.up)
        if (filmeAtual.getSinopse()=="") {
            root_descricaofilme.visibility= View.GONE
            animSlide = AnimationUtils.loadAnimation(applicationContext,
                R.anim.up_without_description)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            textview_descricaofilme.justificationMode = LineBreaker.JUSTIFICATION_MODE_INTER_WORD
        }
        textview_descricaofilme.setOnClickListener {
            if (isCollapsed) {
                textview_descricaofilme.maxLines = Int.MAX_VALUE
            } else {
                textview_descricaofilme.maxLines = MAX_LINES_COLLAPSED
            }
            isCollapsed = !isCollapsed
        }

        var poster = "${BASE_IMAGE_URL}.${filmeAtual.getPoster()}"
        Picasso.get().load(Uri.parse(poster)).placeholder(R.drawable.ic_logo).into(imageview_filme)
        Picasso.get().load(Uri.parse(poster)).placeholder(R.drawable.ic_logo).into(
            imageview_detalheposter_filme)
        imageview_filme.startAnimation(animSlide)
        applyLayoutTransition()

    }
    private fun updateLabel(myCalendar: Calendar) {
        val myFormat = "dd/MM/yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        edittext_data_filme.setText(sdf.format(myCalendar.getTime()))
    }

    private fun applyLayoutTransition() {
        val transition = LayoutTransition()
        transition.setDuration(500)
        transition.enableTransitionType(LayoutTransition.CHANGING)
        root_descricaofilme.layoutTransition = transition
    }
}