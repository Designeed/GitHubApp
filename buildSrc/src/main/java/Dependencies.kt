object Dependencies {

    object Dagger {
        private const val version = "2.42"

        const val hilt = "com.google.dagger:hilt-android:$version"
        const val hiltCompiler = "com.google.dagger:hilt-android-compiler:$version"
    }

    object Squareup {
        private const val version = "2.9.0"

        const val retrofit = "com.squareup.retrofit2:retrofit:$version"
        const val retrofitConverter = "com.squareup.retrofit2:converter-gson:$version"
    }

    object Navigation {
        private const val version = "2.4.2"

        const val navFragment = "androidx.navigation:navigation-fragment-ktx:$version"
        const val navUI = "androidx.navigation:navigation-ui-ktx:$version"
    }

    object Kotlin {
        private const val serializationVersion = "1.3.3"
        private const val coroutineVersion = "1.6.0"

        const val serialization = "org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion"
        const val coroutine = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutineVersion"
    }

    object Lifecycle {
        private const val version = "2.4.1"

        const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
        const val lifeDate = "androidx.lifecycle:lifecycle-livedata-ktx:$version"
        const val runtime = "androidx.lifecycle:lifecycle-runtime-ktx:$version"
        const val saveState = "androidx.lifecycle:lifecycle-viewmodel-savedstate:$version"
    }
}