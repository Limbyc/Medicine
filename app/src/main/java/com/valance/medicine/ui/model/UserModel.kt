package com.valance.medicine.ui.model

import com.google.firebase.firestore.FirebaseFirestore

class UserModel {
    private val db = FirebaseFirestore.getInstance()
    private val collectionRef = db.collection("Users")

    fun checkIfUserExists(phone: String, callback: (Boolean) -> Unit) {
        collectionRef.whereEqualTo("phone", phone).get()
            .addOnSuccessListener { documents ->
                callback(documents.size() > 0)
            }
            .addOnFailureListener { exception ->
                callback(false)
            }
    }

    fun saveUser(phone: String, password: String, callback: (Boolean) -> Unit) {
        val user = hashMapOf(
            "phone" to phone,
            "password" to password
        )
        collectionRef.document("user")
            .set(user)
            .addOnSuccessListener {
                callback(true)
            }
            .addOnFailureListener { e ->
                callback(false)
            }
    }
}
