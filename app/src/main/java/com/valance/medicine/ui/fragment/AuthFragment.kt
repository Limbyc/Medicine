package com.valance.medicine.ui.fragment

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.valance.medicine.R
import com.valance.medicine.databinding.AuthFragmentBinding
import com.valance.medicine.ui.model.UserModel
import com.valance.medicine.ui.presenter.AuthPresenter


class AuthFragment: Fragment() {

    private lateinit var binding: AuthFragmentBinding
    private lateinit var authPresenter: AuthPresenter
    private lateinit var navController: NavController
    private var authFlag = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AuthFragmentBinding.inflate(inflater,container, false)

        navController = findNavController()
        val userModel = UserModel(requireContext())
        authPresenter = AuthPresenter(userModel, navController, this)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.registration.setOnClickListener {
            findNavController().navigate(R.id.registrationFragment)
        }

            binding.phoneAuth.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    formatPhoneNumber(s)
                    successRegistration()
                    authFlag = 1
                }
            })
            binding.passwordAuth.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    successRegistration()
                    authFlag = 1
                }
            })

            watchPasswordAuth()

            binding.loginButton.setOnClickListener {
                val phone = binding.phoneAuth.text.toString().trim()
                val password = binding.passwordAuth.text.toString().trim()

                if (phone.isEmpty() || password.isEmpty()) {
                    Toast.makeText(requireContext(), "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT).show()
                    authFlag = 0
                } else {
                    authPresenter.authenticateUser(phone, password)
                }
            }
    }

    private fun formatPhoneNumber(text: Editable?) {
        text?.let {
            val insertions = arrayOf(
                0 to '+', 1 to '3', 2 to '7', 3 to '5', 4 to '(', 7 to ')', 11 to '-', 14 to '-'
            )

            insertions.forEach { (index, char) ->
                if (index < it.length && it[index] != char) {
                    it.insert(index, char.toString())
                }
            }
        }
    }

    private fun successRegistration() {
        if (authFlag == 1 && !binding.phoneAuth.text.isNullOrEmpty()) {
            binding.loginButton.backgroundTintList = ColorStateList.valueOf(
                ContextCompat.getColor(binding.loginButton.context, R.color.registration_button_ready)
            )
        } else {
            binding.loginButton.backgroundTintList = ColorStateList.valueOf(
                ContextCompat.getColor(binding.loginButton.context, R.color.registration_button_not_ready)
            )
            authFlag = 0
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun watchPasswordAuth() {
        binding.watchPassword.setOnTouchListener { view, motionEvent ->
            val cursorPosition = binding.passwordAuth.selectionEnd
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    binding.passwordAuth.transformationMethod = null
                }
                MotionEvent.ACTION_UP -> {
                    binding.passwordAuth.transformationMethod = PasswordTransformationMethod.getInstance()
                    view.performClick()
                }
                MotionEvent.ACTION_CANCEL -> {
                    binding.passwordAuth.transformationMethod = PasswordTransformationMethod.getInstance()
                }
            }

            binding.passwordAuth.setSelection(cursorPosition)
            true
        }
    }
    fun showErrorMassage(){
        binding.errorAuth.visibility = View.VISIBLE
    }
}