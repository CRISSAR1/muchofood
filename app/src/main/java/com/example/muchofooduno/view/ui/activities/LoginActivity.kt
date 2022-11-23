package com.example.muchofooduno.view.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.muchofooduno.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    lateinit var iniciob:Button
    lateinit var registrob:Button
    lateinit var recuperarb:TextView
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        firebaseAuth=Firebase.auth

        iniciob=findViewById(R.id.ingresar)
        registrob=findViewById(R.id.registro)
        recuperarb=findViewById(R.id.recuperat)
        val correo= findViewById<EditText>(R.id.correoLogin)
        val contrasena=findViewById<EditText>(R.id.contrasenaLogin)


        iniciob.setOnClickListener{
            login(correo.text.toString(),contrasena.text.toString())
        }
        registrob.setOnClickListener{
            startActivity(Intent(this,RegistroActivity::class.java))
        }
        recuperarb.setOnClickListener{
            startActivity(Intent(this,RecuperarActivity::class.java))
        }

    }
    private fun login(correo:String,contrasena:String){
        firebaseAuth.signInWithEmailAndPassword(correo,contrasena)
            .addOnCompleteListener(this){
                task->if(task.isSuccessful){
                    val user=firebaseAuth.currentUser
                        Toast.makeText(baseContext,user?.uid.toString(),Toast.LENGTH_SHORT).show()
                startActivity(Intent(this,HomeActivity::class.java))
            }else{
                Toast.makeText(baseContext,"Error al ingreso",Toast.LENGTH_SHORT).show()
            }
            }

    }
}