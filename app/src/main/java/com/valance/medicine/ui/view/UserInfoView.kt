package com.valance.medicine.ui.view

interface UserInfoView {
    suspend fun showSuccessMessage(message: String)
    suspend fun showErrorMessage(message: String)
}