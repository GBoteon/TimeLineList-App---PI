package com.manasomali.timelinelist.activities

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.facebook.login.LoginManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.manasomali.timelinelist.Constants
import com.manasomali.timelinelist.Constants.EMPTY_STRING
import com.manasomali.timelinelist.Constants.KEY_IDUSER
import com.manasomali.timelinelist.Constants.KEY_NOME
import com.manasomali.timelinelist.Constants.KEY_SOBRENOME
import com.manasomali.timelinelist.R
import com.manasomali.timelinelist.UserSetup
import com.squareup.picasso.Picasso
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_editperfil.*


class EditPerfilActivity : AppCompatActivity() {

    val sharedPrefs by lazy {  getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE) }
    lateinit var alertDialog: AlertDialog
    lateinit var storageReference: StorageReference
    private val COD_IMG = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editperfil)
        requestedOrientation = SCREEN_ORIENTATION_PORTRAIT
        imageview_voltar_editpefiltoperfil.setOnClickListener { startActivity(Intent(this,
            PerfilActivity::class.java)) }
        button_editaperfil_save.setOnClickListener {
            sharedPrefs.edit().putString(KEY_NOME, edittext_editaperfil_nome.text.toString()).apply()
            sharedPrefs.edit().putString(KEY_SOBRENOME,
                edittext_editaperfil_sobrenome.text.toString()).apply()
            Toast.makeText(this, "Dados Atualizados.", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, PerfilActivity::class.java))
        }
        imageview_exitfirebase.setOnClickListener {
            Firebase.auth.signOut()
            LoginManager.getInstance().logOut()
            startActivity(Intent(this, LoginActivity::class.java))
        }
        edittext_editaperfil_nome.setText(sharedPrefs.getString(KEY_NOME, EMPTY_STRING).toString())
        edittext_editaperfil_sobrenome.setText(sharedPrefs.getString(KEY_SOBRENOME, EMPTY_STRING)
            .toString())
        Picasso.get().load(Uri.parse(UserSetup.getUserFoto(this).toString())).placeholder(
            R.mipmap.ic_person).into(
            circularimageview_editperfil)
        button_editaperfil_mudarfoto.setOnClickListener {
            setImgFirebase()
        }
    }
    fun setImgFirebase() {
        alertDialog = SpotsDialog.Builder().setContext(this).build()
        storageReference = FirebaseStorage.getInstance().getReference(UserSetup.getUserId(this).toString() + "/profilePicture/" + UserSetup.getUserNome(this).toString())
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        alertDialog.show()
        startActivityForResult(Intent.createChooser(intent, "Selecione a Imagem"), COD_IMG)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == COD_IMG){
            val uploadFile = storageReference.putFile(data!!.data!!)
            uploadFile.continueWithTask{ task ->
                if(task.isSuccessful)
                {
                    Toast.makeText(this, "Imagem Carrregada com sucesso!", Toast.LENGTH_SHORT).show()
                }
                storageReference.downloadUrl
            }.addOnCompleteListener{ task ->
                if(task.isSuccessful){
                    val downloadUri = task.result.toString()
                    alertDialog.dismiss()
                    sharedPrefs.edit().putString(Constants.KEY_FOTO, downloadUri).apply()
                    Picasso.get().load(Uri.parse(sharedPrefs.getString(Constants.KEY_FOTO,
                        EMPTY_STRING))).placeholder(
                        R.mipmap.ic_person).into(
                        circularimageview_editperfil)
                }
            }
        }
    }
}