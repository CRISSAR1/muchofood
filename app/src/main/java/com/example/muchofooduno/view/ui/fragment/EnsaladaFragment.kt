package com.example.muchofooduno.view.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.muchofooduno.R
import com.example.muchofooduno.model.ensaslada
import com.example.muchofooduno.view.adapter.EnsaladaAdapter
import com.example.muchofooduno.view.adapter.OnEnsaladaItemClickListener
import com.example.muchofooduno.viewmodel.ensaladaViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase


class EnsaladaFragment : Fragment(),OnEnsaladaItemClickListener {
    lateinit var recyclerEnsalada:RecyclerView
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var adapter:EnsaladaAdapter
    val database: FirebaseFirestore = FirebaseFirestore.getInstance()

    private val viewmodel by lazy { ViewModelProvider(this).get(ensaladaViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth= Firebase.auth
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_ensalada, container, false)
        recyclerEnsalada=view.findViewById(R.id.recyclerview)
        adapter= EnsaladaAdapter(requireContext(),this)
        recyclerEnsalada.layoutManager= LinearLayoutManager(context)
        recyclerEnsalada.adapter=adapter
        observeData()
        return view
    }
    fun observeData(){
        viewmodel.ensaladaData().observe(viewLifecycleOwner, Observer{
            adapter.setListData(it)
            adapter.notifyDataSetChanged()
        })
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btm=view.findViewById<BottomNavigationView>(R.id.btmnavigation)
        btm.setOnNavigationItemReselectedListener {
            when(it.itemId){
                R.id.home -> findNavController().navigate(R.id.action_ensaladaFragment_to_homeFragment)
                R.id.perfil -> findNavController().navigate(R.id.action_ensaladaFragment_to_platosFragment)
                R.id.map -> findNavController().navigate(R.id.action_ensaladaFragment_to_mapaFragment)
                R.id.cerrar -> {
                    firebaseAuth.signOut()
                    findNavController().navigate(R.id.action_pizzaFragment_to_loginActivity)
                    true
                }
            }
        }
    }

    override fun onItemClick(ensalada: ensaslada, position: Int) {
        val titulo:String=ensalada.titulo
        val precio:String=ensalada.precio
        val image:String=ensalada.image
        val dato= hashMapOf(
            "titulo" to titulo,
            "precio" to precio,
            "image" to image
        )
        database.collection("compras")
            .document(titulo)
            .set(dato)
            .addOnSuccessListener {
                Toast.makeText(context,"Ensalada añadida al carrito", Toast.LENGTH_SHORT).show()
            }
    }
}