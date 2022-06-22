plugins {
    id("com.android.application")
    id("dagger.hilt.android.plugin")
    kotlin("plugin.serialization") version "1.6.21"
    kotlin("android")
    kotlin("kapt")
}
repositories {
    google()
    mavenCentral()
}
android {
    compileSdk = 32

    defaultConfig {
        applicationId = "com.example.githubapp"
        minSdk = 21
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
    kapt {
        correctErrorTypes = true
    }
}
dependencies {

    implementation("androidx.core:core-ktx:1.8.0")
    implementation("androidx.appcompat:appcompat:1.4.2")
    implementation("com.google.android.material:material:1.6.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")

    //Hilt
    implementation(Dependencies.Dagger.hilt)
    kapt(Dependencies.Dagger.hiltCompiler)

    //Retrofit
    implementation(Dependencies.Squareup.retrofit)
    implementation(Dependencies.Squareup.retrofitConverter)
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0")

    //Navigation Component
    implementation(Dependencies.Navigation.navFragment)
    implementation(Dependencies.Navigation.navUI)

    //Kotlin Serialization
    implementation(Dependencies.Kotlin.serialization)

    //Coroutine
    implementation(Dependencies.Kotlin.coroutine)

    //Lifecycle
    implementation(Dependencies.Lifecycle.viewModel)
    implementation(Dependencies.Lifecycle.lifeDate)
    implementation(Dependencies.Lifecycle.runtime)
    implementation(Dependencies.Lifecycle.saveState)
}

