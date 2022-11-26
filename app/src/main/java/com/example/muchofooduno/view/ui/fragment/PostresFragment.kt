package com.example.muchofooduno.view.ui.fragment

import android.annotation.SuppressLint
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
import com.example.muchofooduno.model.compras
import com.example.muchofooduno.model.postres
import com.example.muchofooduno.view.adapter.OnComprasItemClickListener
import com.example.muchofooduno.view.adapter.OnPostresItemClickListener
import com.example.muchofooduno.view.adapter.PostresAdapter
import com.example.muchofooduno.viewmodel.postresViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase


class PostresFragment : Fragment(), OnPostresItemClickListener {

    lateinit var firebaseAuth: FirebaseAuth
    lateinit var recyclerPostres: RecyclerView
    lateinit var adapter:PostresAdapter
    val database: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val viewmodel by lazy { ViewModelProvider(this).get(postresViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth= Firebase.auth
    }
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_postres, container, false)
        recyclerPostres=view.findViewById(R.id.recyclerview)
        adapter=PostresAdapter(requireContext(),this)
        recyclerPostres.layoutManager=LinearLayoutManager(context)
        recyclerPostres.adapter=adapter
        observeData()
        return view
    }
    fun observeData(){
        viewmodel.postresData().observe(viewLifecycleOwner, Observer{
            adapter.setListData(it)
            adapter.notifyDataSetChanged()
        })
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btm=view.findViewById<BottomNavigationView>(R.id.btmnavigation)
        btm.setOnNavigationItemReselectedListener {
            when(it.itemId){
                R.id.home -> findNavController().navigate(R.id.action_postresFragment_to_homeFragment)
                R.id.perfil -> findNavController().navigate(R.id.action_postresFragment_to_platosFragment)
                R.id.map -> findNavController().navigate(R.id.action_postresFragment_to_mapaFragment)
                R.id.cerrar -> {
                    firebaseAuth.signOut()
                    findNavController().navigate(R.id.action_pizzaFragment_to_loginActivity)
                    true
                }
            }
        }
    }

    override fun onItemClick(postres: postres, position: Int) {
        val titulo:String=postres.titulo
        val precio:String=postres.precio
        val image:String=postres.image
        val dato= hashMapOf(
            "titulo" to titulo,
            "precio" to precio,
            "image" to image
        )
        database.collection("compras")
            .document(titulo)
            .set(dato)
            .addOnSuccessListener {
                Toast.makeText(context,"Postre añadida al carrito", Toast.LENGTH_SHORT).show()
            }
    }


}
