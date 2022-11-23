package com.example.muchofooduno.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.muchofooduno.R
import com.example.muchofooduno.model.ensaslada
import com.example.muchofooduno.model.pizza
import com.squareup.picasso.Picasso

class EnsaladaAdapter(private val context: Context): RecyclerView.Adapter<EnsaladaAdapter.ViewHolder>() {
    private var ensaladalista= mutableListOf<ensaslada>()

    fun setListData(data:MutableList<ensaslada>){
        ensaladalista=data
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup,int: Int):ViewHolder{
        val v=LayoutInflater.from(viewGroup.context).inflate(R.layout.card_view_ensalada,viewGroup,false)
        return ViewHolder(v)
    }
    inner class ViewHolder(ItemView: View): RecyclerView.ViewHolder(ItemView){
        fun binVew(ensaslada: ensaslada){
            itemView.findViewById<TextView>(R.id.tittle).text=ensaslada.titulo
            itemView.findViewById<TextView>(R.id.precio).text= ensaslada.precio
            Picasso.with(context).load(ensaslada.image).into(itemView.findViewById<ImageView>(R.id.image))
        }
    }


    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        val ensalada=ensaladalista[i]
        viewHolder.binVew(ensalada)
    }

    override fun getItemCount(): Int {
        return if(ensaladalista.size>0){
            ensaladalista.size
        }else{
            0
        }

    }
}