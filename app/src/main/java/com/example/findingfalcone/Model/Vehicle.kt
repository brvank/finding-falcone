package com.example.findingfalcone.Model

import org.json.JSONObject

data class Vehicle(val name:String, val totalNo:Int, val maxDistance:Int, val speed:Int){
    companion object{
        fun map(data: JSONObject) : Vehicle{
            return Vehicle(
                data.optString("name", ""),
                data.optInt("total_no", 0),
                data.optInt("max_distance", 0),
                data.optInt("speed", 0)
            )
        }
    }
}
