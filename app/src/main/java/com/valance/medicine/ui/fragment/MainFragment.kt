package com.valance.medicine.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.valance.medicine.databinding.MainFragmentBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainFragment: Fragment() {


    private lateinit var binding: MainFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        launchQuery()
    }

    private fun launchQuery() {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            requireActivity().runOnUiThread {
            }
        }
    }
}