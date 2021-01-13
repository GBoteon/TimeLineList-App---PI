package com.manasomali.timelinelist.activities

import android.animation.LayoutTransition
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Intent
import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
import android.graphics.text.LineBreaker
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.View.*
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.manasomali.timelinelist.Constants.BASE_IMAGE_URL
import com.manasomali.timelinelist.R
import com.manasomali.timelinelist.RepositoryFilmes
import com.manasomali.timelinelist.RepositoryImplementationFilmes
import com.manasomali.timelinelist.database.BaseDadosFilmes
import com.manasomali.timelinelist.helpers.BaseFilmeDetalhe
import com.manasomali.timelinelist.helpers.EssencialFilme
import com.manasomali.timelinelist.viewmodels.FilmesFragmentViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detalhefilme.*
import kotlinx.android.synthetic.main.activity_detalhefilme.imageview_compartilhar
import kotlinx.android.synthetic.main.activity_detalheserie.*
import kotlinx.android.synthetic.main.filme_item.*
import java.text.SimpleDateFormat
import java.util.*


class DetalheFilmeActivity : AppCompatActivity() {

    companion object {
        private const val MAX_LINES_COLLAPSED = 3
        private const val INITIAL_IS_COLLAPSED = true
    }
    private var isCollapsed = INITIAL_IS_COLLAPSED

    lateinit var repositoryFilmes: RepositoryFilmes
    lateinit var dbFilmes: BaseDadosFilmes

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhefilme)
        requestedOrientation = SCREEN_ORIENTATION_PORTRAIT

        dbFilmes = BaseDadosFilmes.invoke(this)
        repositoryFilmes = RepositoryImplementationFilmes(dbFilmes.filmesDAO())
        val viewModel by viewModels<FilmesFragmentViewModel> {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                    return FilmesFragmentViewModel(repositoryFilmes) as T
                }
            }
        }

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
            textview_notaselecionada_filme.setText((rating*2).toString())
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
        edittext_dataassistido_filme.setOnClickListener {
            DatePickerDialog(
                this@DetalheFilmeActivity, R.style.DialogTheme, date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        var filmeAtual = intent.getSerializableExtra("filmeClick") as BaseFilmeDetalhe
        var idunico = 0
        if (intent.getStringExtra("origem")=="ListaPessoal") {
            var filmeDB = intent.getSerializableExtra("filmeDB") as EssencialFilme
            idunico = filmeDB.id!!
            edittext_dataassistido_filme.text = filmeDB.dataAssistidoPessoal
            edittext_nota_filme.text = filmeDB.notaPessoal
            if (filmeDB.cinema==1) checkbox_cinema.isChecked = true
            if (filmeDB.dormiu==1) checkbox_dormiu.isChecked = true
            if (filmeDB.chorou==1) checkbox_chorou.isChecked = true
            if (filmeDB.favorito==1) checkbox_favorito.isChecked = true
        }
        button_salvareditar_filme.setOnClickListener {
            if(intent.getStringExtra("origem")=="Pesquisa") {
                viewModel.addFilme(EssencialFilme(
                    null,
                    filmeAtual.id,
                    filmeAtual.title,
                    filmeAtual.backdropPath,
                    edittext_dataassistido_filme.text.toString(),
                    getCheckboxCinema(),
                    getCheckboxDormiu(),
                    getCheckboxChorou(),
                    getCheckboxFavorito(),
                    edittext_nota_filme.text.toString()
                    ))
                Toast.makeText(this, "Filme Adicionado", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this,ListaActivity::class.java))
            } else {
                viewModel.updateFilme(EssencialFilme(
                    idunico,
                    filmeAtual.id,
                    filmeAtual.title,
                    filmeAtual.backdropPath,
                    edittext_dataassistido_filme.text.toString(),
                    getCheckboxCinema(),
                    getCheckboxDormiu(),
                    getCheckboxChorou(),
                    getCheckboxFavorito(),
                    edittext_nota_filme.text.toString()
                ))
                Toast.makeText(this, "Filme Atualizado", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this,ListaActivity::class.java))
            }
        }
        button_delete_filme.setOnClickListener {
            if(intent.getStringExtra("origem")=="ListaPessoal") {
                viewModel.deleteFilme(idunico)
                Toast.makeText(this, "Filme Removido", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this,ListaActivity::class.java))
            }
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
        edittext_dataassistido_filme.setText(sdf.format(myCalendar.getTime()))
    }

    private fun applyLayoutTransition() {
        val transition = LayoutTransition()
        transition.setDuration(500)
        transition.enableTransitionType(LayoutTransition.CHANGING)
        root_descricaofilme.layoutTransition = transition
    }
    private fun getCheckboxCinema(): Int {
        return if(checkbox_cinema.isChecked) {1} else {0}
    }
    private fun getCheckboxDormiu(): Int {
        return if(checkbox_dormiu.isChecked) {1} else {0}
    }
    private fun getCheckboxChorou(): Int {
        return if(checkbox_chorou.isChecked) {1} else {0}
    }
    private fun getCheckboxFavorito(): Int {
        return if(checkbox_favorito.isChecked) {1} else {0}
    }
}