package com.valance.medicine.ui.model

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
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
        val updatedUserData: Map<String, Any> = hashMapOf(
            "name" to name,
            "lastName" to lastName,
            "birthday" to birthday
        )

        return try {
            val querySnapshot = collectionRef.whereEqualTo("phone", userPhone).get().await()

            if (!querySnapshot.isEmpty) {
                val documentSnapshot = querySnapshot.documents[0]
                collectionRef.document(documentSnapshot.id).update(updatedUserData).await()
                SaveUserResult(documentSnapshot.id, userPhone, true)
            } else {
                SaveUserResult(null, null, false)
            }
        } catch (e: Exception) {
            SaveUserResult(null, null, false)
        }
    }



    private fun generateNumericUserId(): Long {
        val currentTime = System.currentTimeMillis()
        return (currentTime % 1000000)
    }
}
