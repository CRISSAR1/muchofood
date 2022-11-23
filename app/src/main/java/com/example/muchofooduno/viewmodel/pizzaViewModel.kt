package com.example.muchofooduno.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.muchofooduno.model.pizza
import com.example.muchofooduno.repository.repo

class pizzaViewModel: ViewModel() {
    val repo=repo()
    fun pizzaData():LiveData<MutableList<pizza>>{
        val mutabledata=MutableLiveData<MutableList<pizza>>()
        repo.getPizzaData().observeForever { result->
            mutabledata.value=result
        }
        return mutabledata
    }

}