package com.example.findingfalcone.ViewModel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel

class AssembleArmyViewModel : ViewModel() {

    private var loading: MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)

    fun observeLoading(owner: LifecycleOwner, observer: Observer<Boolean>){
        loading.observe(owner, observer)
    }
}