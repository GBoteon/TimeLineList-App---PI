package com.manasomali.timelinelist.activities

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.manasomali.timelinelist.Constants
import com.manasomali.timelinelist.Constants.EMPTY_STRING
import com.manasomali.timelinelist.Constants.KEY_NOME
import com.manasomali.timelinelist.Constants.KEY_SOBRENOME
import com.manasomali.timelinelist.R
import kotlinx.android.synthetic.main.activity_editperfil.*
import kotlinx.android.synthetic.main.activity_perfil.*

class EditPerfilActivity : AppCompatActivity() {

    val sharedPrefs by lazy {  getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editperfil)
        requestedOrientation = SCREEN_ORIENTATION_PORTRAIT

        imageview_voltar_editpefiltoperfil.setOnClickListener { startActivity(Intent(this, PerfilActivity::class.java)) }
        button_editaperfil_save.setOnClickListener {
            sharedPrefs.edit().putString(KEY_NOME, edittext_editaperfil_nome.text.toString()).apply()
            sharedPrefs.edit().putString(KEY_SOBRENOME, edittext_editaperfil_sobrenome.text.toString()).apply()
            Toast.makeText(this, "Dados Atualizados.", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, PerfilActivity::class.java))
        }
        imageview_exitfirebase.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this, LoginActivity::class.java))
        }
        edittext_editaperfil_nome.setText(sharedPrefs.getString(KEY_NOME, EMPTY_STRING).toString())
        edittext_editaperfil_sobrenome.setText(sharedPrefs.getString(KEY_SOBRENOME, EMPTY_STRING).toString())
    }
}