package com.manasomali.timelinelist.activities


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
import android.os.Bundle
import android.text.InputType
import android.view.MotionEvent
import android.view.View.OnTouchListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.manasomali.timelinelist.Constants
import com.manasomali.timelinelist.R
import kotlinx.android.synthetic.main.activity_cadastro.*


class CadastroActivity : AppCompatActivity() {

    val sharedPrefs by lazy {  getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE) }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)
        requestedOrientation = SCREEN_ORIENTATION_PORTRAIT
        imageview_voltar_cadastrotologin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        button_cadastro_cadastrese.setOnClickListener {
            if((edittext_cadastro_nome.text.toString().isNotBlank())&&(edittext_cadastro_sobrenome.text.toString().isNotBlank())&&(edittext_cadastro_email.text.toString().isNotBlank())&&(edittext_cadastro_senha1.text.toString().isNotBlank())) {
                if(edittext_cadastro_senha2.text.toString()==edittext_cadastro_senha1.text.toString()) {
                    cadastroUsuarioFirebase(edittext_cadastro_email.text.toString(),
                        edittext_cadastro_senha1.text.toString())
                } else {
                    Toast.makeText(this, "Senhas não são iguais.", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, "Informe os dados.", Toast.LENGTH_LONG).show()
            }
        }
    }
    fun cadastroUsuarioFirebase(email: String, senha: String) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha).addOnCompleteListener(this) { task ->
            if(task.isSuccessful) {
                val emailUser = task.result?.user!!.email
                Toast.makeText(this, "Usuário cadastrado.", Toast.LENGTH_LONG).show()
                sharedPrefs.edit().putString(Constants.KEY_IDUSER, task.result?.user!!.uid).apply()
                sharedPrefs.edit().putString(Constants.KEY_NOME,
                    edittext_cadastro_nome.text.toString()).apply()
                sharedPrefs.edit().putString(Constants.KEY_SOBRENOME,
                    edittext_cadastro_sobrenome.text.toString()).apply()
                sharedPrefs.edit().putString(Constants.KEY_EMAIL, emailUser).apply()
                startActivity(Intent(this, ListaActivity::class.java))
            } else {
                Toast.makeText(this, task.exception?.message.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }
}