package com.example.findingfalcone.Model

import org.json.JSONObject

data class Planet(val name:String, val distance:Int){
    companion object{
        fun map(data: JSONObject) : Planet{
            return Planet(
                data.optString("name", ""),
                data.optInt("distance", 0)
            )
        }
    }
}