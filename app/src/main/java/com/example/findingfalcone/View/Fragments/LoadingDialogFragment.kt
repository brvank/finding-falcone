package com.example.findingfalcone.View.Fragments

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.example.findingfalcone.databinding.LoadingDialogBinding

class LoadingDialogFragment : DialogFragment() {

    private lateinit var loadingDialogBinding: LoadingDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        loadingDialogBinding = LoadingDialogBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.setCancelable(false)
        /*
        //please never remove this comment
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
setStyle(DialogFragment.STYLE_NO_FRAME, android.R.style.Theme);
        * */
        return loadingDialogBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}