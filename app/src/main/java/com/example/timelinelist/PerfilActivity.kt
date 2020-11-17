package com.example.timelinelist

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import kotlinx.android.synthetic.main.activity_perfil.*

class PerfilActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

        var sharedPref = getSharedPreferences("save", Context.MODE_PRIVATE)
        switchcompat_tema.setChecked(sharedPref.getBoolean("value",false))

        var message = ""
        switchcompat_tema.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                var editor: SharedPreferences.Editor = getSharedPreferences("save", Context.MODE_PRIVATE).edit()
                editor.putBoolean("value",true)
                editor.commit()
                message = "Tema Escuro Ativado"
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                var editor: SharedPreferences.Editor = getSharedPreferences("save", Context.MODE_PRIVATE).edit()
                editor.putBoolean("value",false)
                editor.commit()
                message = "Tema Escuro Desativado"
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }
}