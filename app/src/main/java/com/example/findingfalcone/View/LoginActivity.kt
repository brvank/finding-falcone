package com.example.findingfalcone.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.findingfalcone.R
import com.example.findingfalcone.Repository.SharedPreferencesRepo
import com.example.findingfalcone.View.Fragments.LoadingDialogFragment
import com.example.findingfalcone.ViewModel.LoginViewModel
import com.example.findingfalcone.databinding.ActivityLoginBinding
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    lateinit var loginViewModel: LoginViewModel
    var loadingDialogFragment: LoadingDialogFragment? = null
    lateinit var sharedPreferencesRepo: SharedPreferencesRepo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(supportActionBar != null){
            supportActionBar!!.hide()
        }

        if(supportActionBar != null){
            supportActionBar!!.hide()
        }

        if(actionBar != null){
            actionBar!!.hide()
        }

        if(actionBar != null){
            actionBar!!.hide()
        }

        loginViewModel = ViewModelProvider(this@LoginActivity).get(LoginViewModel::class.java)
        sharedPreferencesRepo = SharedPreferencesRepo(this@LoginActivity)

        loginViewModel.observeLoading(this@LoginActivity, Observer {
            if(it != null){
                if(loadingDialogFragment != null){
                    loadingDialogFragment?.dismiss()
                }
                if(it){
                    loadingDialogFragment = LoadingDialogFragment()
                    loadingDialogFragment?.show(supportFragmentManager, "Loading")
                }
            }
        })

        loginViewModel.observeError(this@LoginActivity, Observer{
            if( it!= null && it.isNotEmpty()){
                val builder = AlertDialog.Builder(this@LoginActivity)
                builder.setTitle("Error")
                builder.setMessage(loginViewModel.getError())

                val alert = builder.create()
                alert.show()
            }
        })

        binding.btnLogin.setOnClickListener {
            loginViewModel.getToken(Runnable {
                sharedPreferencesRepo.saveToken(value = loginViewModel.token)
                startActivity(Intent(this, AssembleArmyActivity::class.java))
                finish()
            })
        }

        if(sharedPreferencesRepo.getToken().isNotEmpty()){
            startActivity(Intent(this, AssembleArmyActivity::class.java))
            finish()
        }
    }

    override fun onPause() {
        super.onPause()
        if(loadingDialogFragment != null){
            loadingDialogFragment?.dismiss()
        }
    }
}