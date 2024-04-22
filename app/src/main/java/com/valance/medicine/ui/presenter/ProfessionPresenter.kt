package com.valance.medicine.ui.presenter

import com.google.firebase.firestore.FirebaseFirestore
import com.valance.medicine.ui.view.ProfessionInterface

class ProfessionPresenter(private val view: ProfessionInterface) {

    class Profession(
        var names: List<String>? = null
    )

    fun getDataFromFirebase(){
        val db = FirebaseFirestore.getInstance()
        db.collection("Profession")
            .get()
            .addOnSuccessListener { documents ->
                val professions = mutableListOf<Profession>()
                for (document in documents) {
                    val names = document.get("name") as? List<String>
                    val profession = Profession(names)
                    professions.add(profession)
                }
                val professionNames = professions.flatMap { it.names ?: emptyList() }
                view.showData(professionNames)
            }
    }

}
