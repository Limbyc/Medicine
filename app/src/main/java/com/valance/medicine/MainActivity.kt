package com.valance.medicine

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.valance.medicine.Medicine
import com.valance.medicine.R
import io.github.jan.supabase.SupabaseClient

class MainActivity : AppCompatActivity() {
    private lateinit var supabaseClient: SupabaseClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val myApplication = application as Medicine
        supabaseClient = myApplication.supabaseClient

    }
}