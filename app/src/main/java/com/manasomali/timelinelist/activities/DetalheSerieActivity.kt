package com.manasomali.timelinelist.activities

import android.animation.LayoutTransition
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
import android.graphics.text.LineBreaker
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.View.*
import android.view.animation.AnimationUtils
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.manasomali.timelinelist.*
import com.manasomali.timelinelist.database.BaseDadosSeries
import com.manasomali.timelinelist.helpers.BaseSerieDetalhe
import com.manasomali.timelinelist.helpers.EssencialSerie
import com.manasomali.timelinelist.viewmodels.SeriesFragmentViewModel
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

    lateinit var repositorySeries: RepositorySeries
    lateinit var dbSeries: BaseDadosSeries

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalheserie)
        requestedOrientation = SCREEN_ORIENTATION_PORTRAIT

        dbSeries = BaseDadosSeries.invoke(this)
        repositorySeries = RepositoryImplementationSeries(dbSeries.seriesDAO())
        val viewModel by viewModels<SeriesFragmentViewModel> {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                    return SeriesFragmentViewModel(repositorySeries) as T
                }
            }
        }

        if(intent.getStringExtra("origem")=="Pesquisa") {
            button_delete_serie.visibility = GONE
            button_salvareditar_serie.text = "SALVAR"
        } else {
            button_salvareditar_serie.text = "EDITAR"
        }
        imageview_voltar_serietolista.setOnClickListener {
            if(intent.getStringExtra("origem")=="ListaPessoal") {
                startActivity(Intent(this, ListaActivity::class.java))
            } else {
                startActivity(Intent(this, PesquisaActivity::class.java))
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
        ratingbar_nota_serie.setOnTouchListener(object : OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                when (event?.action) {
                    MotionEvent.ACTION_MOVE -> {
                        textview_notaselecionada_filme.text = ratingbar_nota_filme.rating.toString()
                    }
                }
                return v?.onTouchEvent(event) ?: true
            }
        })
        button_ok_nota_serie.setOnClickListener {
            cardview_rating_serie.visibility = View.INVISIBLE
            var value = ratingbar_nota_serie.rating
            edittext_nota_serie.setText((value).toString())
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
        edittext_dataassistido_serie.setOnClickListener {
            DatePickerDialog(
                this@DetalheSerieActivity, R.style.DialogTheme, date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
        var serieAtual = intent.getSerializableExtra("serieClick") as BaseSerieDetalhe
        var idunico = 0
        if (intent.getStringExtra("origem")=="ListaPessoal") {
            var serieDB = intent.getSerializableExtra("serieDB") as EssencialSerie
            idunico = serieDB.id!!
            edittext_dataassistido_serie.text = serieDB.dataAssistidoPessoal
            edittext_nota_serie.text = serieDB.notaPessoal
            if (serieDB.statusPessoal == Constants.STATUS_SERIE_PESSOAL[0]) checkbox_assistindo.isChecked = true
            if (serieDB.statusPessoal == Constants.STATUS_SERIE_PESSOAL[1]) checkbox_completa.isChecked = true
            if (serieDB.statusPessoal == Constants.STATUS_SERIE_PESSOAL[2]) checkbox_emespera.isChecked = true
            if (serieDB.statusPessoal == Constants.STATUS_SERIE_PESSOAL[3]) checkbox_largada.isChecked = true
            if (serieDB.statusPessoal == Constants.STATUS_SERIE_PESSOAL[4]) checkbox_pretendoassistir.isChecked = true

        }

        button_salvareditar_serie.setOnClickListener {
            if(intent.getStringExtra("origem")=="Pesquisa") {
                viewModel.addSerie(EssencialSerie(
                    null,
                    serieAtual.id,
                    serieAtual.name,
                    serieAtual.backdropPath,
                    edittext_dataassistido_serie.text.toString(),
                    getSelectedRadioButton(),
                    edittext_nota_serie.text.toString()
                ))
                Toast.makeText(this, "Serie Adicionado", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, ListaActivity::class.java))
            } else {
                viewModel.updateSerie(EssencialSerie(
                    idunico,
                    serieAtual.id,
                    serieAtual.name,
                    serieAtual.backdropPath,
                    edittext_dataassistido_serie.text.toString(),
                    getSelectedRadioButton(),
                    edittext_nota_serie.text.toString()
                ))
                Toast.makeText(this, "Serie Atualizado", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, ListaActivity::class.java))
            }
        }
        button_delete_serie.setOnClickListener {
            if(intent.getStringExtra("origem")=="ListaPessoal") {
                viewModel.deleteSerie(idunico)
                Toast.makeText(this, "Serie Removida", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, ListaActivity::class.java))
            }
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
            animSlide = AnimationUtils.loadAnimation(applicationContext,
                R.anim.up_without_description)
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
        Picasso.get().load(Uri.parse(poster)).placeholder(R.drawable.ic_logo).into(
            imageview_detalheposter_serie)

        imageview_serie.startAnimation(animSlide)
        applyLayoutTransition()
    }
    private fun updateLabel(myCalendar: Calendar) {
        val myFormat = "dd/MM/yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        edittext_dataassistido_serie.setText(sdf.format(myCalendar.getTime()))
    }
    private fun applyLayoutTransition() {
        val transition = LayoutTransition()
        transition.setDuration(500)
        transition.enableTransitionType(LayoutTransition.CHANGING)
        root_descricaoserie.layoutTransition = transition
    }
    private fun getSelectedRadioButton(): String {
        val selectedId: Int = radiogroup_status_serie.checkedRadioButtonId
        var radioButton = findViewById<View>(selectedId) as RadioButton
        return radioButton.text.toString()
    }
}