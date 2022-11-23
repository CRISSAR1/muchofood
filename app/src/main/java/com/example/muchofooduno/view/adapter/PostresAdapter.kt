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
import com.example.muchofooduno.model.postres
import com.squareup.picasso.Picasso
class PostresAdapter(private val context: Context):RecyclerView.Adapter<PostresAdapter.ViewHolder>(){
    private var postreslista= mutableListOf<postres>()

    fun setListData(data:MutableList<postres>){
        postreslista=data
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup,int: Int):ViewHolder{
        val v=LayoutInflater.from(viewGroup.context).inflate(R.layout.car_view_postres,viewGroup,false)
        return ViewHolder(v)
    }
    inner class ViewHolder(ItemView: View): RecyclerView.ViewHolder(ItemView){
        fun binVew(postres: postres){
            itemView.findViewById<TextView>(R.id.tittle).text=postres.titulo
            itemView.findViewById<TextView>(R.id.precio).text= postres.precio
            Picasso.with(context).load(postres.image).into(itemView.findViewById<ImageView>(R.id.image))
        }
    }


    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        val pizzas=postreslista[i]
        viewHolder.binVew(pizzas)
    }

    override fun getItemCount(): Int {
        return if(postreslista.size>0){
            postreslista.size
        }else{
            0
        }

    }

}