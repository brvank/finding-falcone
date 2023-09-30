package com.example.findingfalcone.Utility

interface FragmentFormController {

    enum class FormStatus{
        PREV,
        NEXT,
        DONE
    }

    public fun execute(formStatus: FormStatus)
}