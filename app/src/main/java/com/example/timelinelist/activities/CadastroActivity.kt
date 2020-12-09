package com.example.timelinelist.activities


import android.content.Intent
import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.timelinelist.R
import kotlinx.android.synthetic.main.activity_cadastro.*


class CadastroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)
        requestedOrientation = SCREEN_ORIENTATION_PORTRAIT


                btnCadastro.setOnClickListener {
            val intent = Intent(this, ListaActivity::class.java)
            startActivity(intent)

        }
    }
}