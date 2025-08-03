plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.safe.args)
    alias(libs.plugins.kapt)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
}

android {
    namespace = "com.nihatmahammadli.crudapp"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.nihatmahammadli.crudapp"
        minSdk = 24
        targetSdk = 36
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
        viewBinding = true
        buildConfig = true
        dataBinding = true
    }
    room {
        schemaDirectory("$projectDir/schemas")
    }
}

dependencies {
    //Dagger
    implementation("com.google.dagger:hilt-android:2.56.2")
    implementation(libs.androidx.recyclerview)
    ksp("com.google.dagger:hilt-android-compiler:2.56.2")

    // Material Design, Swipe
    implementation ("com.google.android.material:material:1.12.0")
    implementation(libs.swiperefreshlayout)

    // Navigation
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.ui)

    // Room database
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)

    // Android
    implementation(libs.androidx.legacy.support.v4)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.swiperefreshlayout)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}