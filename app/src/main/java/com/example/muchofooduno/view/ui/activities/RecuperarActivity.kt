package com.example.muchofooduno.view.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.muchofooduno.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RecuperarActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    lateinit var brecuperar:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recuperar)
        firebaseAuth=Firebase.auth
        brecuperar=findViewById(R.id.Brecucontra)

        val correo=findViewById<EditText>(R.id.correoRestaurar)
        brecuperar.setOnClickListener{
            cambiocontrasena(correo.text.toString())
        }
    }
    private fun cambiocontrasena(correo:String){
        firebaseAuth.sendPasswordResetEmail(correo)
            .addOnCompleteListener(this){
                task->if(task.isSuccessful) {
                Toast.makeText(baseContext, "correo enviado", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, LoginActivity::class.java))
            }else{
                Toast.makeText(baseContext,"Error",Toast.LENGTH_SHORT).show()
            }
            }
    }
}