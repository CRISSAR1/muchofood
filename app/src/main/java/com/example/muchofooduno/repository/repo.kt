package com.example.muchofooduno.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.muchofooduno.model.compras
import com.example.muchofooduno.model.pizza
import com.example.muchofooduno.model.ensaslada
import com.example.muchofooduno.model.postres
import com.google.firebase.firestore.FirebaseFirestore

class repo {
    fun getPizzaData(): LiveData<MutableList<pizza>>{
        val mutabledata=MutableLiveData<MutableList<pizza>>()

        FirebaseFirestore.getInstance().collection("pizzas").get()
            .addOnSuccessListener { result->
                val listData= mutableListOf<pizza>()
                for(document in result){
                    val titulo=document.getString("titulo")
                    val precio=document.getString("precio")
                    val image=document.getString("image")
                    val pizza=pizza(titulo!!, precio!!,image!!)
                    listData.add(pizza)
                }
                mutabledata.value=listData

            }
        return mutabledata
    }
    fun getEnsaladaData(): LiveData<MutableList<ensaslada>>{
        val mutabledata=MutableLiveData<MutableList<ensaslada>>()

        FirebaseFirestore.getInstance().collection("ensalada").get()
            .addOnSuccessListener { result->
                val listData= mutableListOf<ensaslada>()
                for(document in result){
                    val titulo=document.getString("titulo")
                    val precio=document.getString("precio")
                    val image=document.getString("image")
                    val ensalada=ensaslada(titulo!!, precio!!,image!!)
                    listData.add(ensalada)
                }
                mutabledata.value=listData

            }
        return mutabledata
    }
    fun getPostresData(): LiveData<MutableList<postres>>{
        val mutabledata=MutableLiveData<MutableList<postres>>()

        FirebaseFirestore.getInstance().collection("postres").get()
            .addOnSuccessListener { result->
                val listData= mutableListOf<postres>()
                for(document in result){
                    val titulo=document.getString("titulo")
                    val precio=document.getString("precio")
                    val image=document.getString("image")
                    val postre=postres(titulo!!, precio!!,image!!)
                    listData.add(postre)
                }
                mutabledata.value=listData

            }
        return mutabledata
    }
    fun getComprasData(): LiveData<MutableList<compras>>{
        val mutabledata=MutableLiveData<MutableList<compras>>()

        FirebaseFirestore.getInstance().collection("compras").get()
            .addOnSuccessListener {
                    result->
                val listData= mutableListOf<compras>()
                for(document in result){
                    val titulo=document.getString("titulo")
                    val precio=document.getString("precio")
                    val image=document.getString("image")
                    val compra=compras(titulo!!, precio!!,image!!)
                    listData.add(compra)
                }
                mutabledata.value=listData

            }
        return mutabledata
    }
}