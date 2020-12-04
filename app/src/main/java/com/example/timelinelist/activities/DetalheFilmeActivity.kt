package com.example.timelinelist.activities

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.view.animation.TranslateAnimation
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.timelinelist.Constants.BASE_IMAGE_URL
import com.example.timelinelist.R
import com.example.timelinelist.helpers.ResultsFilme
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detalhefilme.*
import java.text.SimpleDateFormat
import java.util.*


class DetalheFilmeActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhefilme)
        textview_nomefilme.isSelected = true
        imageview_voltar_filmetolista.setOnClickListener { finish() }
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


        var filmeAtual = intent.getSerializableExtra("filmeClick") as? ResultsFilme
        var poster = "${BASE_IMAGE_URL}.${filmeAtual?.poster_path}"
        var titulo = filmeAtual?.title

        textview_nomefilme.text = titulo
        Picasso.get().load(Uri.parse(poster)).into(imageview_filme)
        var animSlide = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.up)
        imageview_filme.startAnimation(animSlide)

    }
    private fun updateLabel(myCalendar: Calendar) {
        val myFormat = "dd/MM/yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        edittext_data.setText(sdf.format(myCalendar.getTime()))
    }
}