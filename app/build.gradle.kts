import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
    alias(libs.plugins.hilt.android)
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
    implementation(libs.androidx.navigation.fragment)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.androidx.splashscreen)

    implementation(libs.retrofit)
    implementation(libs.retrofit.convertor.gson)
    implementation(libs.okHttp)
    implementation(libs.okHttp.logging.interceptor)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    implementation(libs.glide)

    implementation(libs.lifecycle.viewmodel)
    implementation(libs.lifecycle.livedata)

    implementation(libs.coroutines.core)
    implementation(libs.coroutines.android)

    implementation(libs.activity.ktx)
    implementation(libs.fragment.ktx)

    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.ui)


    implementation(libs.room.runtime)
    kapt(libs.room.compiler)
    implementation(libs.room.ktx)

    implementation(libs.razorpay)
}

kapt {
    correctErrorTypes = true
}