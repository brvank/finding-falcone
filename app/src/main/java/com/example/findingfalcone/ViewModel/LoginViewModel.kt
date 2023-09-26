package com.example.findingfalcone.ViewModel

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.findingfalcone.Repository.NetworkRepo
import com.example.findingfalcone.Utility.Api
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

class LoginViewModel : ViewModel() {

    var name: String = ""
    var networkRepo: NetworkRepo = NetworkRepo()

    private var loading: MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)
    private var error: MutableLiveData<String> = MutableLiveData("")

    fun observeLoading(owner: LifecycleOwner, observer: Observer<Boolean>){
        loading.observe(owner, observer)
    }

    fun setLoading(state: Boolean){
        loading.value = state
    }

    fun getLoading() : Boolean{
        return loading.value!!
    }

    fun observeError(owner: LifecycleOwner, observer: Observer<String>){
        error.observe(owner, observer)
    }

    fun setError(state: String){
        error.value = state
    }

    fun getError(): String{
        return error.value!!
    }

    //************************** repository work
    fun getToken(runnable: Runnable){
        setLoading(true)
        error.postValue("")
        networkRepo.post(Api.generateToken, "{}", object: Callback{
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
}