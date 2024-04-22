package com.valance.medicine.ui.presenter

import android.widget.Toast
import androidx.navigation.NavController
import com.valance.medicine.R
import com.valance.medicine.ui.fragment.AuthFragment
import com.valance.medicine.ui.model.UserModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AuthPresenter(private val userModel: UserModel, private val navController: NavController, private val fragment: AuthFragment) {

    fun authenticateUser(phone: String, password: String) {
        if (phone.isEmpty() || password.isEmpty()) {
            Toast.makeText(navController.context, "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT).show()
            return
        }

        GlobalScope.launch(Dispatchers.Main) {
            val isAuthenticated = userModel.checkUserCredentials(phone, password)
            if (isAuthenticated) {
                navController.navigate(R.id.mainFragment)
            } else {
                fragment.showErrorMassage()
            }
        }
    }
}
