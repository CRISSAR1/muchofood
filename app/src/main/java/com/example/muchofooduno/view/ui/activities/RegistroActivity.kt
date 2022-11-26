package com.example.muchofooduno.view.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.muchofooduno.R
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class RegistroActivity : AppCompatActivity() {
    lateinit var bregistro:Button
    private lateinit var nombre:EditText
    private lateinit var  apellido:EditText
    private lateinit var celular:EditText
    private lateinit var direccion:EditText
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var dbreferencia:DatabaseReference
    private lateinit var database:FirebaseDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)
        firebaseAuth= Firebase.auth
        database=FirebaseDatabase.getInstance()
        dbreferencia=database.reference.child("Usuario")

        bregistro=findViewById(R.id.Bregistrarse)
        val correo=findViewById<EditText>(R.id.correoRegistro)
        val contrasena=findViewById<EditText>(R.id.contrasenaregistro)
        nombre=findViewById(R.id.nombreRegistro)
        apellido=findViewById(R.id.apellidoRegistro)
        celular=findViewById(R.id.celularRegistro)
        direccion=findViewById(R.id.direccionRegistro)

        bregistro.setOnClickListener{
            crearcuenta(correo.text.toString(),contrasena.text.toString())
        }
    }
    private fun crearcuenta(correo:String, contrasena:String){
        val nombre:String=nombre.text.toString()
        val apellido:String=apellido.text.toString()
        val celular:String=celular.text.toString()
        val direccion:String=direccion.text.toString()

        firebaseAuth.createUserWithEmailAndPassword(correo,contrasena)
            .addOnCompleteListener(this){
                Task->if(Task.isSuccessful){
                    val user=firebaseAuth.currentUser
                    val userdb=dbreferencia.child(user?.uid.toString())
                    userdb.child("name").setValue(nombre)
                    userdb.child("apellido").setValue(apellido)
                    userdb.child("celular").setValue(celular)
                    userdb.child("direccion").setValue(direccion)

                    Toast.makeText(baseContext,"Registro Exitoso",Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this,HomeActivity::class.java))
            }else{
                Toast.makeText(baseContext,"Error registro",Toast.LENGTH_SHORT).show()
            }

            }

    }
}