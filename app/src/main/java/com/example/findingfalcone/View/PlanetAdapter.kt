package com.example.findingfalcone.View

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.findingfalcone.Model.Planet
import com.example.findingfalcone.Utility.PlanetSelector
import com.example.findingfalcone.databinding.DestinationItemLayoutBinding

class PlanetAdapter(private var planetsList: ArrayList<Planet>, private val planetSelector: PlanetSelector) : RecyclerView.Adapter<PlanetAdapter.ViewHolder>() {

    private var masterPlanetsList: ArrayList<Planet> = planetsList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(DestinationItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return planetsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvName.text = planetsList[position].name
        holder.binding.tvValue.text = (planetsList[position].distance).toString()
        holder.binding.layoutParent.setOnClickListener {
            planetSelector.select(planetsList[position])
        }
    }

    class ViewHolder(val binding: DestinationItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    fun filterList(q: String){
        if(q.isNotEmpty()) {
            planetsList = masterPlanetsList.filter {
                it.name.contains(q, ignoreCase = true)
            } as ArrayList<Planet>

            notifyDataSetChanged()
        }else{
            planetsList = masterPlanetsList

            notifyDataSetChanged()
        }
    }
}