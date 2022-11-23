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
import com.example.muchofooduno.model.pizza
import com.squareup.picasso.Picasso

class PizzaAdapter(private val context: Context):RecyclerView.Adapter<PizzaAdapter.ViewHolder>() {
    private var pizzalista= mutableListOf<pizza>()

    fun setListData(data:MutableList<pizza>){
        pizzalista=data
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup,int: Int):ViewHolder{
        val v=LayoutInflater.from(viewGroup.context).inflate(R.layout.card_view_pizza,viewGroup,false)
        return ViewHolder(v)
    }
    inner class ViewHolder(ItemView: View): RecyclerView.ViewHolder(ItemView){
         fun binVew(pizza: pizza){
             itemView.findViewById<TextView>(R.id.tittle).text=pizza.titulo
             itemView.findViewById<TextView>(R.id.precio).text= pizza.precio
             Picasso.with(context).load(pizza.image).into(itemView.findViewById<ImageView>(R.id.image))
         }
    }


    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        val pizzas=pizzalista[i]
        viewHolder.binVew(pizzas)
    }

    override fun getItemCount(): Int {
        return if(pizzalista.size>0){
            pizzalista.size
        }else{
            0
        }

    }
}