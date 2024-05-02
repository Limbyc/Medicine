package com.valance.medicine.ui.presenter

import android.content.Context
import android.util.Log
import com.valance.medicine.ui.model.UserModel
import com.valance.medicine.ui.view.UserInfoView

class UserInfoPresenter(private val view: UserInfoView, private val context: Context) {
    private val userModel = UserModel(context)

    suspend fun addUserInfo(phone: String, name: String, lastName: String, birthday: String) {
        try {
            if (phone != null) {
                val result = userModel.addUserInfo(phone, name, lastName, birthday)
                if (result.success) {
                    view.showSuccessMessage("Информация пользователя успешно обновлена")
                } else {
                    view.showErrorMessage("Ошибка при обновлении информации пользователя")
                    Log.e("UserInfoPresenter", "Error updating user info: $result")
                }
            } else {
                view.showErrorMessage("Номер телефона не найден в SharedPreferences")
                Log.e("UserInfoPresenter", "Phone number not found in SharedPreferences")
            }
        } catch (e: Exception) {
            view.showErrorMessage("Ошибка при обновлении информации пользователя")
            Log.e("UserInfoPresenter", "Error updating user info: ${e.message}", e)
        }
    }

}
