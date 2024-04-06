package com.valance.medicine.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.valance.medicine.databinding.StartFragmentBinding

class StartFragment: Fragment() {

    private lateinit var binding: StartFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = StartFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.LottieView.apply {
            setAnimation("start_animation.json")
            repeatCount = 0
            playAnimation()
        }
    }
}