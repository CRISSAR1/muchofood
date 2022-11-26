package com.example.muchofooduno.view.ui.fragment

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.muchofooduno.R
import com.example.muchofooduno.model.compras
import com.example.muchofooduno.model.pizza
import com.example.muchofooduno.view.adapter.ComprasAdapter
import com.example.muchofooduno.view.adapter.OnComprasItemClickListener
import com.example.muchofooduno.viewmodel.comprasViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase


class BebidasFragment : Fragment(),OnComprasItemClickListener {
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var recyclerCompras: RecyclerView
    lateinit var precioT:TextView
    lateinit var compraT:TextView
    val database:FirebaseFirestore=FirebaseFirestore.getInstance()
    lateinit var adapter: ComprasAdapter
    private val viewmodel by lazy { ViewModelProvider(this).get(comprasViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth= Firebase.auth
    }
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_bebidas, container, false)
        recyclerCompras=view.findViewById(R.id.recyclerview)
        precioT=view.findViewById(R.id.preciototal)
        compraT=view.findViewById(R.id.compraT)
        adapter= ComprasAdapter(requireContext(),this)
        recyclerCompras.layoutManager=LinearLayoutManager(context)
        recyclerCompras.adapter=adapter
        observeData()
        preciototal()
        compraT.setOnClickListener{
            realizarCompra()
        }
        return view
    }
    fun observeData(){
        viewmodel.comprasData().observe(viewLifecycleOwner, Observer{
            adapter.setListData(it)
            adapter.notifyDataSetChanged()
        })
    }
    private fun realizarCompra(){
        val builder=AlertDialog.Builder(requireContext())
        builder.setTitle("Compra Comida")
        builder.setMessage("¿Desea realizar compra?")
        builder.setPositiveButton("Aceptar"){
            dialog,witch->
            findNavController().navigate(R.id.action_bebidasFragment_to_homeFragment)
        }
        builder.setNegativeButton("Cancelar",null)
        builder.show()

    }
    private fun preciototal(){
        database.collection("compras")
            .get()
            .addOnSuccessListener {
                result->
                val preciounitario= mutableListOf<String>()
                for(document in result){
                    val precio=document["precio"].toString()
                    preciounitario.add(precio!!)
                }
                val preciototal=preciounitario.mapNotNull { it.toIntOrNull() }.sum()
                precioT.setText(Integer.toString(preciototal))
            }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btm=view.findViewById<BottomNavigationView>(R.id.btmnavigation)
        btm.setOnNavigationItemReselectedListener {
            when(it.itemId){
                R.id.home -> findNavController().navigate(R.id.action_bebidasFragment_to_homeFragment)
                R.id.perfil -> findNavController().navigate(R.id.action_bebidasFragment_to_platosFragment)
                R.id.map -> findNavController().navigate(R.id.action_bebidasFragment_to_mapaFragment)
                R.id.cerrar -> {
                    firebaseAuth.signOut()
                    findNavController().navigate(R.id.action_pizzaFragment_to_loginActivity)
                    true
                }
            }
        }
    }

    override fun onItemClick(compra: compras, position: Int) {
        database.collection("compras")
            .document(compra.titulo)
            .delete()
    }
}
