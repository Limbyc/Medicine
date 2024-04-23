package com.valance.medicine.ui.model

import com.google.firebase.firestore.FirebaseFirestore
import com.valance.medicine.data.Doctor

import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DoctorModel {
    val db = FirebaseFirestore.getInstance()
    val doctorsCollection = db.collection("Doctor")
    suspend fun getDoctors(): List<Doctor> = withContext(Dispatchers.IO) {

        try {
            val querySnapshot = doctorsCollection.get().await()
            val doctorsList = mutableListOf<Doctor>()
            for (document in querySnapshot.documents) {
                val doctor = document.toObject(Doctor::class.java)
                doctor?.let { doctorsList.add(it) }
            }
            doctorsList
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}

