package com.valance.medicine.ui.model

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class UserModel(private val context: Context) {
    private val db = FirebaseFirestore.getInstance()
    private val collectionRef = db.collection("Users")

    suspend fun checkIfUserExists(phone: String): Boolean {
        val documents = collectionRef.whereEqualTo("phone", phone).get().await()
        return documents.size() > 0
    }

    data class SaveUserResult(val userId: String?, val phone: String?, val success: Boolean)

    suspend fun saveUser(phone: String, password: String): SaveUserResult {
        val user = hashMapOf(
            "phone" to phone,
            "password" to password,
        )

        val numericUserId = generateNumericUserId().toString()
        user["user_id"] = numericUserId

        return try {
            collectionRef.add(user).await()
            SaveUserResult(numericUserId, phone, true)
        } catch (e: Exception) {
            SaveUserResult(null, null, false)
        }
    }

    suspend fun checkUserCredentials(phone: String, password: String): Boolean {
        val documents = collectionRef.whereEqualTo("phone", phone).whereEqualTo("password", password).get().await()
        return documents.size() > 0
    }

    suspend fun addUserInfo(userPhone: String, name: String, lastName: String, birthday: String): UserModel.SaveUserResult {
        val updatedUserData = hashMapOf(
            "name" to name,
            "lastName" to lastName,
            "birthday" to birthday
        )

        return try {
            collectionRef.document(userPhone).set(updatedUserData).await()
            SaveUserResult(userPhone, userPhone, true)
        } catch (e: Exception) {
            SaveUserResult(null, null, false)
        }
    }

    private fun generateNumericUserId(): Long {
        val currentTime = System.currentTimeMillis()
        return (currentTime % 1000000)
    }
}
