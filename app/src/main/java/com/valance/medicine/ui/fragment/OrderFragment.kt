package com.valance.medicine.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.valance.medicine.R
import com.valance.medicine.databinding.OrderFragmentBinding
import com.valance.medicine.ui.presenter.ProfessionPresenter
import com.valance.medicine.ui.view.ProfessionInterface

class OrderFragment : Fragment(), ProfessionInterface {

    private lateinit var binding: OrderFragmentBinding
    private lateinit var tabLayout: TabLayout
    private lateinit var presenter: ProfessionPresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = OrderFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tabLayout = binding.tabLayout
        presenter = ProfessionPresenter(this)
        presenter.getDataFromFirebase()
    }

    override fun showData(data: List<String>) {
        for (profession in data) {
            val tab = tabLayout.newTab()
            val customTabView = createTabView(profession)
            tab.customView = customTabView
            tabLayout.addTab(tab)
        }
    }

    private fun createTabView(text: String): View {
        val customTabView = LayoutInflater.from(context).inflate(R.layout.profession_type, null) as TextView
        customTabView.text = text
        return customTabView
    }

}


