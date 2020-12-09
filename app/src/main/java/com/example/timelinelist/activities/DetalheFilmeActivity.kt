package com.example.timelinelist.activities

import android.animation.LayoutTransition
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
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

        textview_nomefilme.isSelected = true
        imageview_voltar_filmetolista.setOnClickListener {
            startActivity(Intent(this,PesquisaActivity::class.java))
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

        edittext_data.setOnClickListener {
            DatePickerDialog(
                this@DetalheFilmeActivity, R.style.DialogTheme, date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        var filmeAtual = intent.getSerializableExtra("filmeClick") as BaseFilmeDetalhe

        var poster = "${BASE_IMAGE_URL}.${filmeAtual.posterPath}"
        var titulo = filmeAtual.title
        var descricao = filmeAtual.overview
        var animSlide = AnimationUtils.loadAnimation(applicationContext, R.anim.up)
        if (descricao=="") {
            root_descricaofilme.visibility= View.GONE
            animSlide = AnimationUtils.loadAnimation(applicationContext,
                R.anim.up_without_description)
        }

        textview_nomefilme.text = titulo
        textview_descricaofilme.text = descricao
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            textview_descricaofilme.justificationMode = LineBreaker.JUSTIFICATION_MODE_INTER_WORD
        }
        textview_descricaofilme.setOnClickListener {
            if (isCollapsed) {
                textview_descricaofilme.maxLines = Int.MAX_VALUE
            } else {
                textview_descricaofilme.maxLines = Companion.MAX_LINES_COLLAPSED
            }
            isCollapsed = !isCollapsed
        }
        textview_duracaofilme.text = "${filmeAtual.runtime} min"
        textview_statusfilme.text = filmeAtual.status
        var data = ""
        val formatter = SimpleDateFormat("yyyy")
        val dateFormat = SimpleDateFormat("yyyy-mm-dd")
        if (filmeAtual.releaseDate!="") {
            @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
            data = formatter.format(dateFormat.parse(filmeAtual.releaseDate))
        }
        textview_lancamentofilme.text = data
        textview_notafilme.text = "${filmeAtual.voteAverage}/10"

        Picasso.get().load(Uri.parse(poster)).placeholder(R.drawable.ic_logo).into(imageview_filme)
        imageview_filme.startAnimation(animSlide)
        applyLayoutTransition()
    }
    private fun updateLabel(myCalendar: Calendar) {
        val myFormat = "dd/MM/yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        edittext_data.setText(sdf.format(myCalendar.getTime()))
    }

    private fun applyLayoutTransition() {
        val transition = LayoutTransition()
        transition.setDuration(500)
        transition.enableTransitionType(LayoutTransition.CHANGING)
        root_descricaofilme.layoutTransition = transition
    }
}