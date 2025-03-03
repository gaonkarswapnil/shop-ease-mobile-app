import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")

    // Dagger Hilt Plugin
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.shopease"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.shopease"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        val properties = Properties().apply {
            load(project.rootProject.file("secret.properties").inputStream())
        }

        buildConfigField(type = "String", name = "BASE_URL", value = properties.getProperty("BASE_URL"))

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug{
            isMinifyEnabled = false
            isShrinkResources = false
            isDebuggable = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }


    flavorDimensions += "role"
    productFlavors{
        create("development") {
            dimension = "role"
            applicationIdSuffix = ".dev"
        }
        create("production") {
            dimension = "role"
            applicationIdSuffix = ".pro"
        }
    }

    buildFeatures {
        viewBinding = true
        dataBinding = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Splash Screen
    implementation("androidx.core:core-splashscreen:1.0.0")


    // RetroFit and OkHttp dependencies
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")


    // Dagger Hilt Dependencies
    implementation("com.google.dagger:hilt-android:2.51.1")
    kapt("com.google.dagger:hilt-android-compiler:2.51.1")


    //    Glide
    implementation("com.github.bumptech.glide:glide:4.15.0")
}

kapt {
    correctErrorTypes = true
}