package com.example.muchofooduno.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.muchofooduno.model.ensaslada
import com.example.muchofooduno.repository.repo

class ensaladaViewModel: ViewModel(){
    val repo= repo()
    fun ensaladaData(): LiveData<MutableList<ensaslada>> {
        val mutabledata= MutableLiveData<MutableList<ensaslada>>()
        repo.getEnsaladaData().observeForever { result->
            mutabledata.value=result
        }
        return mutabledata
    }
}