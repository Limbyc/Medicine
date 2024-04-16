package com.valance.medicine.ui.presenter

import android.widget.Toast
import androidx.navigation.NavController
import com.valance.medicine.R
import com.valance.medicine.ui.fragment.RegistrationFragment
import com.valance.medicine.ui.model.UserModel

class RegistrationPresenter(private val userModel: UserModel, private val navController: NavController,  private val fragment: RegistrationFragment) {

    fun registerUser(phone: String, password: String) {
        userModel.checkIfUserExists(phone) { exists ->
            if (exists) {
                fragment.showUserExistsMessage()
            } else {
                userModel.saveUser(phone, password) { success ->
                    if (success) {
                        navController.navigate(R.id.mainFragment)
                    } else {
                        Toast.makeText(navController.context, "Ошибка сохранения пользователя", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
