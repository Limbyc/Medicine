package com.valance.medicine

import android.app.Application
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.PropertyConversionMethod

class Medicine : Application() {
    lateinit var supabaseClient: SupabaseClient

    override fun onCreate() {
        super.onCreate()

        supabaseClient = createSupabaseClient(
            supabaseUrl = "https://xyzcompany.supabase.co",
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Ind0a2pwZ3R5cm9vcm9raGhremVnIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MTAyNDgyNDEsImV4cCI6MjAyNTgyNDI0MX0.l9kuBsXy2TEHU3PeBDD9i7n3rUYsV1eZ0AN3tPdkfBk"
        ) {
            install(Auth)
            install(Postgrest) {
                defaultSchema = "public"
                propertyConversionMethod = PropertyConversionMethod.SERIAL_NAME
            }
        }
    }
}
