plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    kotlin("plugin.serialization") version "2.1.10"
}

android {
    namespace = "com.example.tasklistqa"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.tasklistqa"
        minSdk = 28
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }

}

dependencies {
    implementation(libs.androidx.navigation.testing)
    testImplementation(libs.junit)
    testImplementation(libs.robolectric)
    testImplementation(libs.core.ktx)
    implementation(libs.kotlinx.coroutines.test)
    implementation(libs.accompanist.swiperefresh.v0312alpha)
    implementation(libs.gson)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)
    implementation(libs.retrofit)
    implementation(libs.koin.androidx.compose)
    implementation(platform(libs.koin.bom))
    implementation(libs.insert.koin.koin.core)
    implementation(libs.koin.core)
    implementation(libs.swipe)
    implementation(libs.accompanist.swiperefresh)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.core.ktx)
    testImplementation (libs.mockito.core)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.ui.test.junit4.android)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    androidTestImplementation(libs.compose)
    testImplementation(kotlin("test"))
    testImplementation (libs.koin.test)
    testImplementation (libs.koin.test.junit4)
    testImplementation (libs.mockk)
    androidTestImplementation(libs.kotlinx.coroutines.test.v173)
}