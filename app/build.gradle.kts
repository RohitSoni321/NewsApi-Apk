plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
    id("androidx.navigation.safeargs")
    id("kotlin-parcelize")
}
val apiKey: String? = project.findProperty("MY_KEY") as? String ?: "default_api_key"
val baseURL: String? = project.findProperty("URL_KEY") as? String ?: "default_url_key"
android {
    namespace = "com.example.newsapiapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.newsapiapp"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        buildConfigField("String","API_KEY","$apiKey")
        buildConfigField("String","BASE_URL","$baseURL")
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
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
    buildFeatures{
        dataBinding = true;
        viewBinding = true;
        buildConfig = true
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    val paging_version = "3.3.5"
    val nav_version = "2.8.6"
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    // Retrofit for network requests
    implementation("com.squareup.retrofit2:retrofit:2.9.0")

    // Gson Converter for Retrofit (JSON parsing)
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // Coroutines for Android
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9")

    // Optional: Logging Interceptor for debugging network calls
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.0")
//        kapt ("androidx.lifecycle:lifecycle-compiler:2.6.2")

    // LiveData
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.0")

    // Lifecycle Runtime (optional)
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.0")

    // Glide for image loading
    implementation("com.github.bumptech.glide:glide:4.16.0")

    // Glide Compiler (for annotations)
    kapt("com.github.bumptech.glide:compiler:4.16.0")

    // Dagger Core
    implementation("com.google.dagger:dagger:2.55")
    kapt("com.google.dagger:dagger-compiler:2.55")

    // Room Database
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")

    //paging
    implementation("androidx.paging:paging-runtime:$paging_version")

    //ok http
    implementation("com.squareup.okhttp3:okhttp:4.12.0")

    //Hilt
    implementation("com.google.dagger:hilt-android:2.51.1")
    kapt("com.google.dagger:hilt-android-compiler:2.51.1")

    // navigation
    // Views/Fragments integration
    implementation("androidx.navigation:navigation-fragment:$nav_version")
    implementation("androidx.navigation:navigation-ui:$nav_version")

    testImplementation(libs.junit)
    testImplementation("com.squareup.okhttp3:mockwebserver:4.12.0")
    testImplementation("com.google.truth:truth:1.4.4")
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}