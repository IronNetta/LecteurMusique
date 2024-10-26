plugins {
    id("com.android.application")
}

android {
    namespace = "be.seba.testapk.lecteurmulti"
    compileSdk = 34

    defaultConfig {
        applicationId = "be.seba.testapk.lecteurmulti"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildFeatures.viewBinding = true
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
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("androidx.cardview:cardview:1.0.0")
    implementation("androidx.viewpager:viewpager:1.0.0")
    implementation("com.github.crosswall:Android-Coverflow:release-v1.0.5")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.databinding:databinding-runtime:8.7.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
}