package com.manasomali.timelinelist.activities

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.observe
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.manasomali.timelinelist.Constants.KEY_THEME
import com.manasomali.timelinelist.Constants.PREFS_NAME
import com.manasomali.timelinelist.Constants.THEME_DARK
import com.manasomali.timelinelist.Constants.THEME_LIGHT
import com.manasomali.timelinelist.Constants.THEME_UNDEFINED
import com.manasomali.timelinelist.R
import com.manasomali.timelinelist.viewmodels.AuthViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_editperfil.*
import kotlinx.android.synthetic.main.activity_perfil.*


class PerfilActivity : AppCompatActivity() {
    private val viewModel: AuthViewModel by viewModels()

    val sharedPrefs by lazy {  getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)
        requestedOrientation = SCREEN_ORIENTATION_PORTRAIT

        initThemeListener()
        initTheme()

        imageview_voltar_pefiltolista.setOnClickListener { startActivity(Intent(this,
            ListaActivity::class.java)) }
        imageview_editperfil.setOnClickListener { startActivity(Intent(this,
            EditPerfilActivity::class.java)) }

        button_estatisticas.setOnClickListener { startActivity(Intent(this,
            EstatisticasActivity::class.java)) }

        viewModel.getUser(FirebaseAuth.getInstance().currentUser!!.uid)
        viewModel.usuario.observe(this) {
            textview_perfil_nome.text = it.nome
            textview_perfil_sobrenome.text = it.sobrenome
            textview_perfil_email.text = it.email
            Picasso.get().load(Uri.parse(it.foto)).placeholder(R.mipmap.ic_person).into(circularimageview_perfil)
        }

    }

    private fun initThemeListener(){
        themeGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.themeLight -> setTheme(AppCompatDelegate.MODE_NIGHT_NO, THEME_LIGHT)
                R.id.themeDark -> setTheme(AppCompatDelegate.MODE_NIGHT_YES, THEME_DARK)
            }
        }
    }

    private fun setTheme(themeMode: Int, prefsMode: Int) {
        AppCompatDelegate.setDefaultNightMode(themeMode)
        saveTheme(prefsMode)
    }

    private fun initTheme() {
        when (getSavedTheme()) {
            THEME_LIGHT -> themeLight.isChecked = true
            THEME_DARK -> themeDark.isChecked = true
            THEME_UNDEFINED -> {
                when (resources.configuration.uiMode.and(Configuration.UI_MODE_NIGHT_MASK)) {
                    Configuration.UI_MODE_NIGHT_NO -> themeLight.isChecked = true
                    Configuration.UI_MODE_NIGHT_YES -> themeDark.isChecked = true
                    Configuration.UI_MODE_NIGHT_UNDEFINED -> themeLight.isChecked = true
                }
            }
        }
    }

    private fun saveTheme(theme: Int) = sharedPrefs.edit().putInt(KEY_THEME, theme).apply()
    private fun getSavedTheme() = sharedPrefs.getInt(KEY_THEME, THEME_UNDEFINED)

}