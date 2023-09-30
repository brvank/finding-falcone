package com.example.findingfalcone.View.Fragments

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.findingfalcone.Model.Planet
import com.example.findingfalcone.Utility.PlanetSelector
import com.example.findingfalcone.View.PlanetAdapter
import com.example.findingfalcone.ViewModel.AssembleArmyViewModel
import com.example.findingfalcone.databinding.SearchableSingleSelectLayoutBinding

class SearchableSingleSelectDialogFragment(val planetSelector: PlanetSelector) : DialogFragment() {

    private lateinit var binding: SearchableSingleSelectLayoutBinding
    private lateinit var assembleArmyViewModel: AssembleArmyViewModel
    private lateinit var planetAdapter: PlanetAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SearchableSingleSelectLayoutBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        assembleArmyViewModel = ViewModelProvider(requireActivity()).get()
        val planets = ArrayList<Planet>()

        for(p in assembleArmyViewModel.getPlanets()){
            planets.add(p)
        }

        for(i in 1..4){
            val planet = assembleArmyViewModel.planetHashMap[i]
            if(!planet.isNullOrEmpty()){
                planets.removeIf {
                    it.name == planet
                }
            }
        }

        planetAdapter = PlanetAdapter(planets, object : PlanetSelector {
            override fun select(planet: Planet) {
                selectAndDismiss(planet)
            }
        })

        binding.rvItems.adapter = planetAdapter
        binding.rvItems.layoutManager = LinearLayoutManager(requireActivity())

        binding.ivCancel.setOnClickListener {
            dismiss()
        }

        binding.etSearch.addTextChangedListener {
            text: Editable? -> run {
                planetAdapter.filterList(text.toString())
            }
        }

        binding.etSearch.requestFocus()

    }

    private fun selectAndDismiss(planet: Planet){
        //work with the planet selected
        planetSelector.select(planet)
        dismiss()
    }
}