package com.valance.medicine.ui.model

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

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

        val numericUserId = generateNumericUserId().toString()
        user["user_id"] = numericUserId

        collectionRef.add(user)
            .addOnSuccessListener { documentReference ->
                callback(true)
            }
            .addOnFailureListener { e ->
                callback(false)
            }
    }

    fun checkUserCredentials(phone: String, password: String, callback: (Boolean) -> Unit) {
        collectionRef.whereEqualTo("phone", phone).whereEqualTo("password", password).get()
            .addOnSuccessListener { documents ->
                callback(documents.size() > 0)
            }
            .addOnFailureListener { exception ->
                callback(false)
            }
    }


//    suspend fun getUserData(phone: String): String? {
//        val documents = collectionRef.whereEqualTo("phone", phone).get().await()
//        if (documents.isNotEmpty()) {
//            val document = documents[0]
//            val userId = document.getString("user_id") ?: ""
//            if (userId.all { it.isDigit() }) {
//                return userId
//            }
//        }
//        return null
//    }

    private fun generateNumericUserId(): Long {
        val currentTime = System.currentTimeMillis()
        return (currentTime % 1000000)
    }


}
