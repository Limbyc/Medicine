package com.valance.medicine.ui.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.valance.medicine.R
import com.valance.medicine.databinding.RegistrationFragmentBinding
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.valance.medicine.ui.model.UserModel
import com.valance.medicine.ui.presenter.RegistrationPresenter
import com.valance.medicine.ui.view.UserAuthView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RegistrationFragment : Fragment(), UserAuthView {

    private lateinit var binding: RegistrationFragmentBinding
    private var registrationFlag = 0
    private lateinit var presenter: RegistrationPresenter
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        navController = findNavController()

        val userModel = UserModel(requireContext())
        presenter = RegistrationPresenter(userModel, navController, requireContext(), this)

        binding = RegistrationFragmentBinding.inflate(inflater,container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.Vhod.setOnClickListener {
            findNavController().navigate(R.id.authFragment)
        }

        binding.phone.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                formatPhoneNumber(s)
                successRegistration()
            }
        })
        binding.password.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                checkPasswordsMatch()
                successRegistration()
            }
        })
        binding.confirmPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                checkPasswordsMatch()
                successRegistration()
            }
        })

        watchPassword()

        binding.Registration.setOnClickListener {
            val phone = binding.phone.text.toString()
            val password = binding.password.text.toString()
            GlobalScope.launch {
                presenter.registerUser(phone, password)
            }
        }

    }

    private fun checkPasswordsMatch() {
        val password = binding.password.text.toString()
        val confirmPassword = binding.confirmPassword.text.toString()

        if (password == confirmPassword) registrationFlag = 1
        else registrationFlag = 0

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
        if (registrationFlag == 1 && !binding.phone.text.isNullOrEmpty()) {
            binding.Registration.backgroundTintList = ColorStateList.valueOf(
                ContextCompat.getColor(binding.Registration.context, R.color.registration_button_ready)
            )
        } else {
            binding.Registration.backgroundTintList = ColorStateList.valueOf(
                ContextCompat.getColor(binding.Registration.context, R.color.registration_button_not_ready)
            )
            registrationFlag = 0
        }
    }

    fun showUserExistsMessage(){
        binding.userAuth.visibility = View.VISIBLE
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun watchPassword() {
        binding.watchPasswordConfirm.setOnTouchListener { view, motionEvent ->
            val cursorPosition = binding.confirmPassword.selectionEnd
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    binding.confirmPassword.transformationMethod = null
                }
                MotionEvent.ACTION_UP -> {
                    binding.confirmPassword.transformationMethod = PasswordTransformationMethod.getInstance()
                    view.performClick()
                }
                MotionEvent.ACTION_CANCEL -> {
                    binding.confirmPassword.transformationMethod = PasswordTransformationMethod.getInstance()
                }
            }

            binding.confirmPassword.setSelection(cursorPosition)
            true
        }
        binding.watchPassword.setOnTouchListener { view, motionEvent ->
            val cursorPosition = binding.password.selectionEnd
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    binding.password.transformationMethod = null
                }
                MotionEvent.ACTION_UP -> {
                    binding.password.transformationMethod = PasswordTransformationMethod.getInstance()
                    view.performClick()
                }
                MotionEvent.ACTION_CANCEL -> {
                    binding.password.transformationMethod = PasswordTransformationMethod.getInstance()
                }
            }

            binding.password.setSelection(cursorPosition)
            true
        }
    }

    override fun showUserAuth() {
        activity?.runOnUiThread {
            binding.userAuth.visibility = View.VISIBLE
        }

    }
}