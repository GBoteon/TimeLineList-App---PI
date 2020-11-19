package com.example.timelinelist.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.example.timelinelist.R
import kotlinx.android.synthetic.main.activity_editperfil.*
import kotlinx.android.synthetic.main.activity_perfil.*
import kotlinx.android.synthetic.main.activity_perfil.switchcompat_tema

class EditPerfilActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editperfil)

        imageview_voltar_editpefiltoperfil.setOnClickListener { startActivity(Intent(this, PerfilActivity::class.java)) }
        btnSave.setOnClickListener { startActivity(Intent(this, PerfilActivity::class.java)) }

    }
}