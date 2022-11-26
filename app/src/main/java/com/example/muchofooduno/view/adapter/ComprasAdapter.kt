package com.example.muchofooduno.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.muchofooduno.R
import com.example.muchofooduno.model.compras
import com.example.muchofooduno.model.pizza
import com.example.muchofooduno.model.postres
import com.squareup.picasso.Picasso

class ComprasAdapter(private val context: Context,var clickListener: OnComprasItemClickListener):RecyclerView.Adapter<ComprasAdapter.ViewHolder>() {
    private var compraslista= mutableListOf<compras>()


    fun setListData(data:MutableList<compras>){
        compraslista=data
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup,int: Int):ViewHolder{
        val v=LayoutInflater.from(viewGroup.context).inflate(R.layout.car_view_compras,viewGroup,false)
        return ViewHolder(v)
    }
    inner class ViewHolder(ItemView: View): RecyclerView.ViewHolder(ItemView){
        fun binVew(compras: compras, action:OnComprasItemClickListener){
            itemView.findViewById<TextView>(R.id.tittle).text=compras.titulo
            itemView.findViewById<TextView>(R.id.precio).text= compras.precio
            Picasso.with(context).load(compras.image).into(itemView.findViewById<ImageView>(R.id.image))
            val btneliminar=itemView.findViewById<ImageButton>(R.id.eliminarCarrito)
            btneliminar.setOnClickListener{
                action.onItemClick(compras,adapterPosition)
            }
        }
    }


    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        val pizzas=compraslista[i]
        viewHolder.binVew(pizzas,clickListener)
    }

    override fun getItemCount(): Int {
        return if(compraslista.size>0){
            compraslista.size
        }else{
            0
        }

    }
}
interface OnComprasItemClickListener {
    fun onItemClick(compra: compras, position:Int)
}
