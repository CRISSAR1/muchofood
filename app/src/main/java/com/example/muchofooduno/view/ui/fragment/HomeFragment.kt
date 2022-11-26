package com.example.muchofooduno.view.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import com.example.muchofooduno.R


class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val cardPizza=view.findViewById<ImageView>(R.id.cardpizza)
        cardPizza.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragment_to_pizzaFragment)
        }
        val cardMapa=view.findViewById<ImageView>(R.id.rutamapa)
        cardMapa.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragment_to_mapaFragment)
        }
        val cardPerfil=view.findViewById<ImageView>(R.id.cardperfil)
        cardPerfil.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_platosFragment)
        }
        val cardEnsalada=view.findViewById<ImageView>(R.id.cardEnsalada)
        cardEnsalada.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_ensaladaFragment)
        }
        val cardPostre=view.findViewById<ImageView>(R.id.cardPostre)
        cardPostre.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_postresFragment)
        }
        val cardCarrito=view.findViewById<ImageView>(R.id.cardcarrito)
        cardCarrito.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_bebidasFragment)
        }

    }
}