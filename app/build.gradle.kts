plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.valance.medicine"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.valance.medicine"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures{
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.fragment:fragment-ktx:1.6.2")

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //supabase
    implementation ("io.github.jan-tennert.supabase:bom:2.2.1")
    implementation ("io.github.jan-tennert.supabase:storage-kt:2.2.1")
    implementation ("io.github.jan-tennert.supabase:gotrue-kt:2.2.1")
    implementation ("io.ktor:ktor-client-core:2.3.9")
    implementation ("io.ktor:ktor-client-json:1.6.2")
    implementation ("io.ktor:ktor-client-serialization:1.6.2")
    implementation ("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")
    implementation("io.github.jan-tennert.supabase:postgrest-kt")
    implementation ("io.ktor:ktor-client-okhttp:2.0.0")

}