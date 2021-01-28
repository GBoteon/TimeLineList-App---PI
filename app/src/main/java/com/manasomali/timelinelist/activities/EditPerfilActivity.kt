package com.manasomali.timelinelist.activities

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import com.facebook.login.LoginManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.manasomali.timelinelist.Constants
import com.manasomali.timelinelist.R
import com.manasomali.timelinelist.viewmodels.AuthViewModel
import com.squareup.picasso.Picasso
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_editperfil.*
import kotlinx.android.synthetic.main.activity_lista.*
import kotlinx.android.synthetic.main.activity_login.*


class EditPerfilActivity : AppCompatActivity() {
    private val viewModel: AuthViewModel by viewModels()
    lateinit var storageReference: StorageReference
    private val COD_IMG = 1000
    val firebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editperfil)
        requestedOrientation = SCREEN_ORIENTATION_PORTRAIT

        imageview_voltar_editpefiltoperfil.setOnClickListener {
            startActivity(Intent(this, PerfilActivity::class.java))
        }
        button_editaperfil_save.setOnClickListener {
            val nome = edittext_editaperfil_nome.text.toString()
            val sobrenome = edittext_editaperfil_sobrenome.text.toString()
            if (validaCampos(nome, sobrenome)) {
                viewModel.editDados(firebaseAuth.currentUser!!.uid, nome, sobrenome)
            }

            Toast.makeText(this, "Dados Atualizados.", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, PerfilActivity::class.java))
        }
        imageview_exitfirebase.setOnClickListener {
            Firebase.auth.signOut()
            LoginManager.getInstance().logOut()
            startActivity(Intent(this, LoginActivity::class.java))
        }
        viewModel.getUser(firebaseAuth.currentUser!!.uid)
        viewModel.usuario.observe(this) {
            edittext_editaperfil_nome.setText(it.nome)
            edittext_editaperfil_sobrenome.setText(it.sobrenome)
            Picasso.get().load(Uri.parse(it.foto)).placeholder(R.mipmap.ic_person).into(circularimageview_editperfil)
        }
        viewModel.fotoUri.observe(this) {
            Picasso.get().load(Uri.parse(it)).placeholder(R.mipmap.ic_person).into(circularimageview_editperfil)
        }
        button_editaperfil_mudarfoto.setOnClickListener {
            setImgFirebase()
        }
        initViewModel()
    }
    fun setImgFirebase() {
        storageReference = FirebaseStorage.getInstance().getReference(firebaseAuth.currentUser!!.uid)
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Selecione a Imagem"), COD_IMG)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == COD_IMG){
            viewModel.uploadFoto(data)
        }
    }
    private fun initViewModel() {
        viewModel.loading.observe(this) {
            if(it) {
                startLoading()
            } else {
                endLoading()
            }
        }
    }
    private fun startLoading() {
        button_editaperfil_mudarfoto.isClickable = false
        button_editaperfil_save.isClickable = false
        edittext_editaperfil_nome.isClickable = false
        edittext_editaperfil_sobrenome.isClickable = false
        progressbar_editaperfil.visibility = View.VISIBLE
        progressbar_editaperfil.background.alpha = 150
    }
    private fun endLoading() {
        button_editaperfil_mudarfoto.isClickable = true
        button_editaperfil_save.isClickable = true
        edittext_editaperfil_nome.isClickable = true
        edittext_editaperfil_sobrenome.isClickable = true
        progressbar_editaperfil.visibility = View.GONE
    }
    private fun validaCampos(nome: String, sobrenome: String): Boolean {
        return when {
            nome.isBlank() || sobrenome.isBlank() -> {
                false
            }
            else -> true
        }
    }
}