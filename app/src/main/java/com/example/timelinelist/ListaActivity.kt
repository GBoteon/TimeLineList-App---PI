package com.example.timelinelist

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import kotlinx.android.synthetic.main.activity_lista.*

class ListaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        val mode = resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)
        when (mode) {
            Configuration.UI_MODE_NIGHT_YES -> {image_logo.setImageResource(R.drawable.nome_escuro)}
            Configuration.UI_MODE_NIGHT_NO -> {image_logo.setImageResource(R.drawable.nome_claro)}
            Configuration.UI_MODE_NIGHT_UNDEFINED -> {image_logo.setImageResource(R.drawable.nome_claro)}
        }
    }
}