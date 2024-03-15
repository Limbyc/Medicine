package com.valance.medicine

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.valance.medicine.Medicine
import com.valance.medicine.R
import com.valance.medicine.databinding.ActivityMainBinding
import io.github.jan.supabase.SupabaseClient

class MainActivity : AppCompatActivity() {
    private lateinit var supabaseClient: SupabaseClient
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideSystemUI()
        supportFragmentManager.beginTransaction()
            .replace(R.id.my_nav_host_fragment, MainFragment())
            .commit()
        val myApplication = application as Medicine
        supabaseClient = myApplication.supabaseClient

    }

    private fun hideSystemUI() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, binding.root).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }
}