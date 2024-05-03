package com.valance.medicine.ui.presenter

import android.content.Context
import android.preference.PreferenceManager
import androidx.navigation.NavController
import com.valance.medicine.R
import com.valance.medicine.ui.model.UserModel
import com.valance.medicine.ui.view.UserAuthView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RegistrationPresenter(
    private val userModel: UserModel,
    private val navController: NavController,
    private val context: Context,
    private val userAuthView: UserAuthView
) {

    suspend fun registerUser(phone: String, password: String) {
        val userExists = userModel.checkIfUserExists(phone)
        if (userExists) {
            userAuthView.showUserAuth()
            return
        }

        val result = userModel.saveUser(phone, password)
        if (result.success) {
            val (userId, userPhone) = result

            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            val editor = sharedPreferences.edit()

            editor.putString("userId", userId)
            editor.putString("userPhone", userPhone)
            editor.apply()

            withContext(Dispatchers.Main) {
                navController.navigate(R.id.mainFragment)
            }
        }
    }


}



