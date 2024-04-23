package com.valance.medicine.ui.view

import com.valance.medicine.data.Doctor

interface DoctorContractInterface {
    interface View {
        fun showDoctors(doctors: List<Doctor>)
    }

    interface Presenter {
        fun getDoctors()
    }
}