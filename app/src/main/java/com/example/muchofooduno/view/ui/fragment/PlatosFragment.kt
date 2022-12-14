package com.example.muchofooduno.view.ui.fragment

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.findFragment
import androidx.navigation.fragment.findNavController
import com.example.muchofooduno.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import android.widget.EditText as EditText


class PlatosFragment : Fragment() {
    lateinit var firebaseAuth: FirebaseAuth
    var database:DatabaseReference= FirebaseDatabase.getInstance().getReference("Usuario")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_platos, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firebaseAuth= Firebase.auth
        val user= firebaseAuth.currentUser
        val name= view.findViewById<EditText>(R.id.nombrePerfil)
        val apellido= view.findViewById<EditText>(R.id.apellidoPerfil)
        val phone= view.findViewById<EditText>(R.id.celularPerfil)
        val direction= view.findViewById<EditText>(R.id.direccionPerfil)
        val email= view.findViewById<EditText>(R.id.correoPerfil)
        val editName= view.findViewById<ImageButton>(R.id.botonEdicionNombre)
        val editApellido= view.findViewById<ImageButton>(R.id.botonEdicionApellido)
        val editEmail= view.findViewById<ImageButton>(R.id.botonEdicionPerfilCorreo)
        val editDirection= view.findViewById<ImageButton>(R.id.botonEdicionPerfilDireccion)
        val editPhone= view.findViewById<ImageButton>(R.id.botonEdicionPerfilCelular)
        val btncamara=view.findViewById<Button>(R.id.btmCamara)
        val btngallery=view.findViewById<Button>(R.id.btmGaleria)

        editName.setOnClickListener{
            database.child(user?.uid.toString()).child("name").setValue(name.text.toString()).addOnSuccessListener {
                Toast.makeText(this.context,"Se cambio el nombre",Toast.LENGTH_LONG).show()
            }
        }
        editApellido.setOnClickListener{
            database.child(user?.uid.toString()).child("apellido").setValue(apellido.text.toString()).addOnSuccessListener {
                Toast.makeText(this.context,"Se cambio el apellido",Toast.LENGTH_LONG).show()
            }
        }

        editPhone.setOnClickListener{
            database.child(user?.uid.toString()).child("celular").setValue(phone.text.toString()).addOnSuccessListener {
                Toast.makeText(this.context,"Se cambio el telefono",Toast.LENGTH_LONG).show()
            }
        }
        editDirection.setOnClickListener{
            database.child(user?.uid.toString()).child("direccion").setValue(direction.text.toString()).addOnSuccessListener {
                Toast.makeText(this.context,"Se cambio la direccion",Toast.LENGTH_LONG).show()
            }
        }
        editEmail.setOnClickListener{
            user?.updateEmail(email.text.toString())
        }

        btncamara.setOnClickListener {
            val intent=Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent,123)
        }
        btngallery.setOnClickListener {
            val intent=Intent(Intent.ACTION_PICK)
            intent.type="image/*"
            startActivityForResult(intent,456)
        }
        super.onViewCreated(view, savedInstanceState)
        val btm=view.findViewById<BottomNavigationView>(R.id.btmnavigation)
        btm.setOnNavigationItemReselectedListener {
            when(it.itemId){
                R.id.home -> findNavController().navigate(R.id.action_platosFragment_to_homeFragment)
                R.id.map -> findNavController().navigate(R.id.action_platosFragment_to_mapaFragment)
                R.id.cerrar -> {
                    firebaseAuth.signOut()
                    findNavController().navigate(R.id.action_pizzaFragment_to_loginActivity)
                    true
                }
            }
        }
        email.setText(user?.email.toString())

        database.child(user?.uid.toString()).addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                name.setText(snapshot.child("name").value.toString())
                direction.setText(snapshot.child("direccion").value.toString())
                phone.setText(snapshot.child( "celular").value.toString())
                apellido.setText(snapshot.child("apellido").value.toString())
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
        /*
           database.addValueEventListener(object :ValueEventListener{
               override fun onDataChange(snapshot: DataSnapshot) {
                   for (ds in snapshot.children){
                       name.setText(ds.child("name").value.toString())
                   }
               }
               override fun onCancelled(error: DatabaseError) {
               }
           })*/
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val imageView=view?.findViewById<ImageView>(R.id.fotoperfil)
        if(requestCode==123){
            var bitmap=data?.extras?.get("data") as Bitmap
            imageView?.setImageBitmap(bitmap)
        }else if (requestCode==456){
            imageView?.setImageURI(data?.data)
        }
    }

}