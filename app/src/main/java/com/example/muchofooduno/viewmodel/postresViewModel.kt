package com.example.muchofooduno.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.muchofooduno.model.postres
import com.example.muchofooduno.repository.repo


class postresViewModel:ViewModel(){
    val repo= repo()
    fun postresData(): LiveData<MutableList<postres>> {
        val mutabledata= MutableLiveData<MutableList<postres>>()
        repo.getPostresData().observeForever { result->
            mutabledata.value=result
        }
        return mutabledata
    }
}