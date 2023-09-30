package com.example.findingfalcone.ViewModel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.findingfalcone.Model.Planet
import com.example.findingfalcone.Model.Vehicle
import com.example.findingfalcone.Repository.NetworkRepo
import com.example.findingfalcone.Utility.Api
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.json.JSONArray
import java.io.IOException

class AssembleArmyViewModel : ViewModel() {

    var formId: MutableLiveData<Int> = MutableLiveData(1)
    var networkRepo: NetworkRepo = NetworkRepo()

    private var loading: MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)
    private var error: MutableLiveData<String> = MutableLiveData("")
    private var planetsList: ArrayList<Planet> = ArrayList()
    private var vehiclesList: ArrayList<Vehicle> = ArrayList()

    var vehicleHashMap: HashMap<Int, String> = HashMap()
    var planetHashMap: HashMap<Int, String> = HashMap()

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

    //*******************************************
    fun getPlanets() : ArrayList<Planet>{
        return planetsList
    }

    fun getVehicles() : ArrayList<Vehicle>{
        return vehiclesList
    }

    //****************************Repository work
    fun fetchPlanets(runnable: Runnable){
        setLoading(true)
        setError("")
        networkRepo.get(Api.getPlanets, object : Callback{
            override fun onFailure(call: Call, e: IOException) {
                postLoading(false)
                postError("Something went wrong! Please Retry!")
            }

            override fun onResponse(call: Call, response: Response) {
                postLoading(false)
                val data = JSONArray(response.body!!.string())
                planetsList.clear()
                for(i in 0 until data.length()){
                    planetsList.add(Planet.map(data.getJSONObject(i)))
                }
                runnable.run()
            }
        })
    }

    fun fetchVehicles(runnable: Runnable){
        setLoading(true)
        setError("")
        networkRepo.get(Api.getVehicles, object : Callback{
            override fun onFailure(call: Call, e: IOException) {
                postLoading(false)
                postError("Something went wrong! Please Retry!")
            }

            override fun onResponse(call: Call, response: Response) {
                postLoading(false)
                val data = JSONArray(response.body!!.string())
                vehiclesList.clear()
                for(i in 0 until data.length()){
                    val vehicle = Vehicle.map(data.getJSONObject(i))
                    vehiclesList.add(vehicle)
                }
                runnable.run()
            }
        })
    }
}