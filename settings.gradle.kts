pluginManagement {
    repositories {
        mavenCentral()
        jcenter()
        gradlePluginPortal()
        google()
    }
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id.startsWith("com.android")) {
                useModule("com.android.tools.build:gradle:7.2.1")
            }
            if (requested.id.id.startsWith("org.jetbrains.kotlin")) {
                useVersion("1.6.21")
            }
            if (requested.id.id.startsWith("dagger.hilt.android")) {
                useModule("com.google.dagger:hilt-android-gradle-plugin:2.40.5")
            }
            if (requested.id.id.startsWith("androidx.navigation.safeargs")) {
                useModule("androidx.navigation:navigation-safe-args-gradle-plugin:2.4.2")
            }
        }
    }
}

rootProject.name = "GitHubApp"
include(":app")
