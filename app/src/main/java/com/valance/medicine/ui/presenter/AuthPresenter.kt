package com.valance.medicine.ui.presenter

import android.widget.Toast
import androidx.navigation.NavController
import com.valance.medicine.R
import com.valance.medicine.ui.fragment.AuthFragment
import com.valance.medicine.ui.model.UserModel

class AuthPresenter(private val userModel: UserModel, private val navController: NavController, private val fragment: AuthFragment) {

    fun authenticateUser(phone: String, password: String) {
        if (phone.isEmpty() || password.isEmpty()) {
            Toast.makeText(navController.context, "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT).show()
            return
        }

        userModel.checkUserCredentials(phone, password) { isAuthenticated ->
            if (isAuthenticated) {
                navController.navigate(R.id.mainFragment)
            } else {
                fragment.showErrorMassage()
            }
        }
    }
}
