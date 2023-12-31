package com.example.findingfalcone.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.findingfalcone.Utility.FragmentFormController
import com.example.findingfalcone.View.Fragments.DestinationVehicleSelectionFragment
import com.example.findingfalcone.View.Fragments.LoadingDialogFragment
import com.example.findingfalcone.ViewModel.AssembleArmyViewModel
import com.example.findingfalcone.databinding.ActivityAssembleArmyBinding

class AssembleArmyActivity : AppCompatActivity(), FragmentFormController {

    lateinit var binding: ActivityAssembleArmyBinding
    lateinit var assembleArmyViewModel: AssembleArmyViewModel
    var loadingDialogFragment: LoadingDialogFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAssembleArmyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        assembleArmyViewModel = ViewModelProvider(this@AssembleArmyActivity).get(AssembleArmyViewModel::class.java)

        setupData()
    }

    private fun setupViews(){
        assembleArmyViewModel.formId.observe(this@AssembleArmyActivity, Observer {
            supportFragmentManager
                .beginTransaction()
                .replace(binding.fragmentContainer.id, DestinationVehicleSelectionFragment())
                .commit()
        })
    }

    private fun setupData(){
        assembleArmyViewModel.observeLoading(this@AssembleArmyActivity, Observer {
            if( it!= null){
                if(loadingDialogFragment != null){
                    loadingDialogFragment?.dismiss()
                }
                if(it){
                    loadingDialogFragment = LoadingDialogFragment()
                    loadingDialogFragment?.show(supportFragmentManager, "Loading")
                }
            }
        })

        assembleArmyViewModel.observeError(this@AssembleArmyActivity, Observer{
            if( it!= null && it.isNotEmpty()){
                val builder = AlertDialog.Builder(this@AssembleArmyActivity)
                builder.setTitle("Error")
                builder.setMessage(assembleArmyViewModel.getError())

                val alert = builder.create()
                alert.show()
            }
        })

        assembleArmyViewModel.fetchPlanets {
            runOnUiThread {
                assembleArmyViewModel.fetchVehicles(Runnable {
                    runOnUiThread {
                        setupViews()
                    }
                })
            }
        }

        for (i in 1..4){
            assembleArmyViewModel.vehicleHashMap[i] = ""
            assembleArmyViewModel.planetHashMap[i] = ""
        }
    }

    override fun onPause() {
        super.onPause()
        if(loadingDialogFragment != null){
            loadingDialogFragment?.dismiss()
        }
    }

    override fun execute(formStatus: FragmentFormController.FormStatus) {
        when(formStatus){
            FragmentFormController.FormStatus.PREV -> {
                assembleArmyViewModel.formId.value = assembleArmyViewModel.formId.value!! - 1
            }

            FragmentFormController.FormStatus.NEXT -> {
                assembleArmyViewModel.formId.value = assembleArmyViewModel.formId.value!! + 1
            }

            FragmentFormController.FormStatus.DONE -> {
                findFalcone()
            }
        }
    }

    private fun findFalcone(){

    }
}