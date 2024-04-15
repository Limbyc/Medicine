package com.valance.medicine.fragment

import android.content.ContentValues.TAG
import android.content.res.ColorStateList
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.valance.medicine.R
import com.valance.medicine.databinding.RegistrationFragmentBinding
import androidx.core.content.ContextCompat
import com.google.firebase.firestore.FirebaseFirestore

class RegistrationFragment : Fragment() {

    private lateinit var binding: RegistrationFragmentBinding
    private var registrationFlag = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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
    }

    private fun checkPasswordsMatch() {
        val password = binding.password.text.toString()
        val confirmPassword = binding.confirmPassword.text.toString()

        if (password == confirmPassword) {
            registrationFlag = 1
        } else {
            registrationFlag = 0
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
        if (registrationFlag == 1 && !binding.phone.text.isNullOrEmpty()) {
            binding.Registration.backgroundTintList = ColorStateList.valueOf(
                ContextCompat.getColor(binding.Registration.context, R.color.registration_button_ready)
            )
            binding.Registration.setOnClickListener {
                val phone = binding.phone.text.toString()
                val password = binding.password.text.toString()

                val user = hashMapOf(
                    "phone" to phone,
                    "password" to password
                )
                val db = FirebaseFirestore.getInstance()
                val collectionRef = db.collection("Users")
                val documentRef = collectionRef.document("user")

                documentRef
                    .set(user)
                    .addOnSuccessListener {
                        Log.d(TAG, "DocumentSnapshot added with ID: ${documentRef.id}")
                        findNavController().navigate(R.id.mainFragment)
                    }
                    .addOnFailureListener { e ->
                        Log.w(TAG, "Error adding document", e)
                    }
            }
        } else {
            binding.Registration.backgroundTintList = ColorStateList.valueOf(
                ContextCompat.getColor(binding.Registration.context, R.color.registration_button_not_ready)
            )
            registrationFlag = 0
        }
    }


    private fun watchPassword() {
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


}