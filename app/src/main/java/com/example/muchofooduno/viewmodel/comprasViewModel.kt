package com.example.muchofooduno.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.muchofooduno.model.compras
import com.example.muchofooduno.repository.repo

class comprasViewModel: ViewModel() {
    val repo= repo()
    fun comprasData(): LiveData<MutableList<compras>> {
        val mutabledata= MutableLiveData<MutableList<compras>>()
        repo.getComprasData().observeForever { result->
            mutabledata.value=result
        }
        return mutabledata
    }
}