package com.valance.medicine.ui.fragment

import android.content.Context
import android.content.SharedPreferences
import android.content.res.ColorStateList
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.valance.medicine.Medicine.Companion.sharedPreferences
import com.valance.medicine.R
import com.valance.medicine.databinding.UserinfoFragmentBinding
import com.valance.medicine.ui.presenter.UserInfoPresenter
import com.valance.medicine.ui.view.UserInfoView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserInfoFragment: Fragment(), UserInfoView {

    private lateinit var binding: UserinfoFragmentBinding
    private lateinit var presenter: UserInfoPresenter
    private var addInfoFlag = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = UserinfoFragmentBinding.inflate(inflater, container,false)
        return  binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = UserInfoPresenter(this, requireContext())

        binding.back.setOnClickListener {
            findNavController().navigate(R.id.profileFragment)
        }

        val birthdayEditText = binding.birthday
        val nameEditText = binding.name
        val lastNameEditText = binding.lastName


        nameEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                addInfo()
            }
        })

        lastNameEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                addInfo()
            }
        })

        birthdayEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun afterTextChanged(s: Editable?) {
                s?.let {
                    val text = s.toString()
                    val parts = text.split(".")
                    if (parts.size > 3) {
                        val formattedText = "${parts[0]}.${parts[1]}.${parts[2]}"
                        birthdayEditText.setText(formattedText)
                        birthdayEditText.setSelection(birthdayEditText.text!!.length)
                    } else if (parts.size == 1 && parts[0].length >= 2) {
                        val formattedText = "${parts[0].substring(0, 2)}."
                        birthdayEditText.setText(formattedText)
                        birthdayEditText.setSelection(birthdayEditText.text!!.length)
                    } else if (parts.size == 2 && parts[1].length >= 2) {
                        val formattedText = "${parts[0]}.${parts[1].substring(0, 2)}."
                        birthdayEditText.setText(formattedText)
                        birthdayEditText.setSelection(birthdayEditText.text!!.length)
                    }
                }
                addInfo()
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val text = s.toString()
                val parts = text.split(".")
                if (before > 0 && count == 0 && (start == 3 || start == 6)) {
                    val newText = StringBuilder(text)
                    if (text[start - 1] == '.') {
                        newText.deleteCharAt(start - 2)
                    } else {
                        newText.deleteCharAt(start - 1)
                    }
                    birthdayEditText.setText(newText.toString())
                    birthdayEditText.setSelection(start - 1)
                } else if (parts.size <= 3 && parts.size > 0) {
                    val day = parts[0].toIntOrNull() ?: 0
                    val month = parts.getOrNull(1)?.toIntOrNull() ?: 0
                    val year = parts.getOrNull(2)?.toIntOrNull() ?: 0

                    if (day !in 1..31) {
                        birthdayEditText.error = "Некорректный день"
                    } else if (month !in 1..12) {
                        birthdayEditText.error = "Некорректный месяц"
                    } else if (year !in 1921..2024) {
                        birthdayEditText.error = "Некорректный год"
                    } else {
                        birthdayEditText.error = null
                    }
                }
            }
        })

    }

    private fun addInfo() {
        val name = binding.name.text.toString().trim()
        val lastName = binding.lastName.text.toString().trim()
        val birthday = binding.birthday.text.toString().trim()

        if (name.isNotBlank() && lastName.isNotBlank() && birthday.isNotBlank()) {
            binding.addInfo.backgroundTintList = ColorStateList.valueOf(
                ContextCompat.getColor(binding.addInfo.context, R.color.registration_button_ready)
            )
            addInfoFlag = 1

            binding.addInfo.setOnClickListener {
                GlobalScope.launch {
                    val userPhone = sharedPreferences.getString("userPhone", null)
                    userPhone?.let { phone ->
                        presenter.addUserInfo(phone, name, lastName, birthday)
                    }
                }
                findNavController().navigate(R.id.profileFragment)

            }

        } else {
            binding.addInfo.backgroundTintList = ColorStateList.valueOf(
                ContextCompat.getColor(binding.addInfo.context, R.color.registration_button_not_ready)
            )
            addInfoFlag = 0
        }
    }

    override suspend fun showSuccessMessage(message: String) {
        withContext(Dispatchers.Main) {
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }
    }

    override suspend fun showErrorMessage(message: String) {
        withContext(Dispatchers.Main) {
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }
    }


}