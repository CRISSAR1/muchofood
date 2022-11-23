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
import androidx.fragment.app.findFragment
import androidx.navigation.fragment.findNavController
import com.example.muchofooduno.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import android.widget.EditText as EditText


class PlatosFragment : Fragment() {
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth=Firebase.auth
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_platos, container, false)
        val nombrecompleto = view.findViewById<EditText>(R.id.nombrecompleto)
        val botonedicion = view.findViewById<ImageButton>(R.id.botonEdicionNombre)


        nombrecompleto.isEnabled = false
        botonedicion.setOnClickListener {
            if (nombrecompleto.isEnabled == false) {
                nombrecompleto.isEnabled = true
            } else if (nombrecompleto.isEnabled == true) {
                nombrecompleto.isEnabled = false
            }
        }
        val btmCamara = view.findViewById<Button>(R.id.btmCamara)

        btmCamara.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, 123)
        }
        val btmGaleria = view.findViewById<Button>(R.id.btmGaleria)
        btmGaleria.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 456)
        }
        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val imageView = view?.findViewById<ImageView>(R.id.fotoperfil)
        if (requestCode == 123) {
            var bitmap = data?.extras?.get("data") as Bitmap
            imageView?.setImageBitmap(bitmap)
        } else if (requestCode == 456) {
            imageView?.setImageURI(data?.data)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btm = view.findViewById<BottomNavigationView>(R.id.btmnavigation)
        btm.setOnNavigationItemReselectedListener {
            when (it.itemId) {
                R.id.home -> findNavController().navigate(R.id.action_platosFragment_to_homeFragment)
                R.id.map -> findNavController().navigate(R.id.action_platosFragment_to_mapaFragment)
                R.id.cerrar -> {
                    firebaseAuth.signOut()
                    findNavController().navigate(R.id.action_pizzaFragment_to_loginActivity)
                    true
                }
            }
        }

    }
}