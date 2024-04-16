package com.valance.medicine.ui.fragment

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewPropertyAnimator
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.valance.medicine.R
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

        binding.LottieView.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}

            override fun onAnimationEnd(animation: Animator) {
                navigateToOtherFragment()
            }

            override fun onAnimationCancel(animation: Animator) {}

            override fun onAnimationRepeat(animation: Animator) {}
        })


        binding.LottieView.setAnimation("start_animation.json")
        binding.LottieView.repeatCount = 0
        binding.LottieView.playAnimation();

        binding.Skip.setOnClickListener {
            navigateToOtherFragment()
        }
    }

    private fun navigateToOtherFragment() {
        findNavController().navigate(R.id.registrationFragment)
    }
}