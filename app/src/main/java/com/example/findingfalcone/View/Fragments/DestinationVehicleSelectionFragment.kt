package com.example.findingfalcone.View.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.core.view.get
import androidx.core.view.size
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.findingfalcone.Model.Planet
import com.example.findingfalcone.R
import com.example.findingfalcone.Utility.FragmentFormController
import com.example.findingfalcone.Utility.PlanetSelector
import com.example.findingfalcone.ViewModel.AssembleArmyViewModel
import com.example.findingfalcone.databinding.FragmentDestinationVehicleSelectionBinding
import java.lang.Exception

class DestinationVehicleSelectionFragment : Fragment() {

    private lateinit var binding: FragmentDestinationVehicleSelectionBinding
    private lateinit var assembleArmyViewModel: AssembleArmyViewModel
    private lateinit var fragmentFormController: FragmentFormController
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDestinationVehicleSelectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        assembleArmyViewModel = ViewModelProvider(requireActivity()).get()

        fragmentFormController = requireActivity() as FragmentFormController

        setupViews()

        checkForPlanetSelection(false)
    }

    private fun setupViews(){
        binding.tvSelectDestinations.setOnClickListener {
            showSelectionDialog()
        }

        binding.ivDropdown.setOnClickListener {
            showSelectionDialog()
        }

        val formId = assembleArmyViewModel.formId.value!!

        binding.tvSelectDestinations.text = "Select Destination ${formId}"
        binding.tvDestinationId.text = "Destination ${formId}/${4}"

        if(formId == 1){
            binding.btn1.visibility = View.GONE
        }else{
            binding.btn1.text = "Prev"
            binding.btn1.visibility = View.VISIBLE
            binding.btn1.setOnClickListener{
                fragmentFormController.execute(FragmentFormController.FormStatus.PREV)
            }
        }

        if(formId == assembleArmyViewModel.getVehicles().size){
            binding.btn2.text = "Find Queen"
            binding.btn2.setOnClickListener{
                fragmentFormController.execute(FragmentFormController.FormStatus.DONE)
            }
        }else{
            binding.btn2.text = "Next"
            binding.btn2.setOnClickListener{
                fragmentFormController.execute(FragmentFormController.FormStatus.NEXT)
            }
        }

        binding.rgVehicles.setOnCheckedChangeListener { rg, i ->
            if(i >= 0 && i <= rg.childCount){
                var name = (rg.getChildAt(i-1)!! as RadioButton).text!!
                name = name.trim {
                    !it.isLetter()
                }
                assembleArmyViewModel.vehicleHashMap[assembleArmyViewModel.formId.value!!] = name.toString()
                checkForVehicleSelection(false)
            }
        }
    }

    private fun showSelectionDialog(){
        val searchableSingleSelectionFragment: SearchableSingleSelectDialogFragment = SearchableSingleSelectDialogFragment(
            object : PlanetSelector {
                override fun select(planet: Planet) {
                    assembleArmyViewModel.planetHashMap[assembleArmyViewModel.formId.value!!] = planet.name
                    checkForPlanetSelection(true)
                }
            }
        )

        searchableSingleSelectionFragment.show(parentFragmentManager, "Planet Select Dialog")
    }

    private fun checkForPlanetSelection(reset: Boolean){
        val planet = assembleArmyViewModel.planetHashMap[assembleArmyViewModel.formId.value]
        if(!planet.isNullOrEmpty()) {
            binding.tvSelectDestinations.text = planet

            checkForVehicleSelection(reset)
        }
    }

    private fun checkForVehicleSelection(reset: Boolean){
        val planet = assembleArmyViewModel.getPlanets().find {
            it.name == assembleArmyViewModel.planetHashMap[assembleArmyViewModel.formId.value]
        }

        if(reset){
            assembleArmyViewModel.vehicleHashMap[assembleArmyViewModel.formId.value!!] = ""
        }

        val vehicle = assembleArmyViewModel.vehicleHashMap[assembleArmyViewModel.formId.value]
        binding.rgVehicles.removeAllViews()

        val vehicleSelectionCount = HashMap<String, Int>()

        for(v in assembleArmyViewModel.getVehicles()){
            vehicleSelectionCount[v.name] = v.totalNo
        }

        for(i in 1..4){
            val name = assembleArmyViewModel.vehicleHashMap[i]
            if(!name.isNullOrEmpty()) {
                val count = vehicleSelectionCount[name]
                vehicleSelectionCount[name] = count!! - 1
            }
        }

        var i = 1
        for (v in assembleArmyViewModel.getVehicles()){
            val rbVehicle: RadioButton = RadioButton(requireContext())
            val count = vehicleSelectionCount[v.name]
            rbVehicle.text = v.name + "(${count.toString()})"
            rbVehicle.id = i

            if(!vehicle.isNullOrEmpty() && v.name == vehicle){
                rbVehicle.isChecked = true
            }

            if((count != null && count <= 0) || (v.maxDistance < planet!!.distance)){
                rbVehicle.isEnabled = false
            }

            binding.rgVehicles.addView(rbVehicle)
            i++
        }

        var timeTaken = 0
        for(j in 1..4){
            val name = assembleArmyViewModel.vehicleHashMap[j]
            if(!name.isNullOrEmpty()) {
                if(!vehicle.isNullOrEmpty()) {
                    val p = assembleArmyViewModel.getPlanets().find {
                        it.name == name
                    }

                    val v = assembleArmyViewModel.getVehicles().find{
                        it.name == vehicle
                    }

                    if(p != null && v != null){
                        timeTaken += p.distance / v.speed
                    }
                }
            }
        }

        binding.tvTimeTaken.text = "Time taken: $timeTaken"
    }
}