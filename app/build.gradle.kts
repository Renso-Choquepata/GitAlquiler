import org.jetbrains.kotlin.gradle.utils.IMPLEMENTATION

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)

    //agregados por el desarrollador
    id("com.google.gms.google-services")
}




android {
    namespace = "choquepata.puma.gitalquiler"
    compileSdk = 34

    defaultConfig {
        applicationId = "choquepata.puma.gitalquiler"
        minSdk = 25
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}
dependencies {

    implementation(libs.firebase.database.ktx)
    val fragment_version = "1.8.2"

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //agregados por el desarrollador
    implementation(platform("com.google.firebase:firebase-bom:33.1.2"))
    //implementation 'com.firebaseui:firebase-ui-auth:7.2.0'
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation(libs.picasso)
    implementation("androidx.fragment:fragment-ktx:$fragment_version")
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation ("com.squareup.retrofit2:converter-moshi:2.11.0")
    implementation("com.google.firebase:firebase-storage")
    implementation("androidx.fragment:fragment-ktx:$1.8.2")


    //implementation(platform("com.google.firebase:firebase-bom:32.7.1"))
}