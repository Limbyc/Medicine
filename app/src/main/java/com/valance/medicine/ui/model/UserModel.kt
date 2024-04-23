package com.valance.medicine.ui.model

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class UserModel {
    private val db = FirebaseFirestore.getInstance()
    private val collectionRef = db.collection("Users")

    suspend fun checkIfUserExists(phone: String): Boolean {
        val documents = collectionRef.whereEqualTo("phone", phone).get().await()
        return documents.size() > 0
    }

    data class SaveUserResult(val userId: String?, val phone: String?, val success: Boolean,)

    suspend fun saveUser(phone: String, password: String): SaveUserResult {
        val user = hashMapOf(
            "phone" to phone,
            "password" to password
        )

        val numericUserId = generateNumericUserId().toString()
        user["user_id"] = numericUserId

        return try {
            collectionRef.add(user).await()
            SaveUserResult( numericUserId, phone, true)
        } catch (e: Exception) {
            SaveUserResult( null, null, false)
        }
    }



    suspend fun checkUserCredentials(phone: String, password: String): Boolean {
        val documents = collectionRef.whereEqualTo("phone", phone).whereEqualTo("password", password).get().await()
        return documents.size() > 0
    }

    private fun generateNumericUserId(): Long {
        val currentTime = System.currentTimeMillis()
        return (currentTime % 1000000)
    }
}

