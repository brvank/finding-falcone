package com.example.findingfalcone.ViewModel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.findingfalcone.Repository.NetworkRepo
import com.example.findingfalcone.Utility.Api
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

class AssembleArmyViewModel : ViewModel() {

    var networkRepo: NetworkRepo = NetworkRepo()

    private var loading: MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)
    private var error: MutableLiveData<String> = MutableLiveData("")

    fun observeLoading(owner: LifecycleOwner, observer: Observer<Boolean>){
        loading.observe(owner, observer)
    }

    //for main thread
    fun setLoading(state: Boolean){
        loading.value = state
    }

    //for background thread
    fun postLoading(state: Boolean){
        loading.postValue(state)
    }

    fun getLoading() : Boolean{
        return loading.value!!
    }

    fun observeError(owner: LifecycleOwner, observer: Observer<String>){
        error.observe(owner, observer)
    }

    //for main thread
    fun setError(state: String){
        error.value = state
    }

    //for background thread
    fun postError(state: String){
        error.postValue(state)
    }

    fun getError(): String{
        return error.value!!
    }

    //****************************Repository work
    fun getPlanets(runnable: Runnable){
        setLoading(true)
        setError("")
        networkRepo.get(Api.getPlanets, object : Callback{
            override fun onFailure(call: Call, e: IOException) {
                loading.postValue(false)
                error.postValue("Something went wrong! Please Retry!")
            }

            override fun onResponse(call: Call, response: Response) {
                loading.postValue(false)
                runnable.run()
            }
        })
    }

    fun getVehicles(runnable: Runnable){
        setLoading(true)
        setError("")
        networkRepo.get(Api.getVehicles, object : Callback{
            override fun onFailure(call: Call, e: IOException) {
                postLoading(false)
                postError("Something went wrong! Please Retry!")
            }

            override fun onResponse(call: Call, response: Response) {
                postLoading(false)
                runnable.run()
            }
        })
    }
}