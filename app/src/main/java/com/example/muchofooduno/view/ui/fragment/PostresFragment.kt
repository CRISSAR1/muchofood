package com.example.muchofooduno.view.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.muchofooduno.R
import com.example.muchofooduno.view.adapter.PostresAdapter
import com.example.muchofooduno.viewmodel.postresViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class PostresFragment : Fragment() {

    lateinit var firebaseAuth: FirebaseAuth
    lateinit var recyclerPostres: RecyclerView
    lateinit var adapter:PostresAdapter
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
        adapter=PostresAdapter(requireContext())
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

}
