package com.valance.medicine.ui.presenter

import com.valance.medicine.ui.model.DoctorModel
import com.valance.medicine.ui.view.DoctorContractInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DoctorPresenter(private val view: DoctorContractInterface.View) : DoctorContractInterface.Presenter {

    private val model = DoctorModel()

    override fun getDoctors() {
        GlobalScope.launch(Dispatchers.Main) {
            val doctors = model.getDoctors()
            view.showDoctors(doctors)
        }
    }
}
