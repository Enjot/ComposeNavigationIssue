import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
    id("org.jetbrains.kotlin.plugin.serialization")
}

android {
    namespace = "com.enjot.composenavigationissue"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.enjot.composenavigationissue"
        minSdk = 21
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        manifestPlaceholders["GOOGLE_MAPS_API_KEY"] =
            gradleLocalProperties(rootDir, project.providers).getProperty("GOOGLE_MAPS_API_KEY")
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    // core & lifecycle
    implementation("androidx.core:core-ktx:1.17.0-beta01")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.10.0-alpha01")

    // Compose & activity
    implementation("androidx.activity:activity-compose:1.12.0-alpha05")
    implementation(platform("androidx.compose:compose-bom-alpha:2025.07.01"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material-icons-extended")

    // Unit tests
    testImplementation("junit:junit:4.13.2")

    // Android tests
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    androidTestImplementation(platform("androidx.compose:compose-bom-alpha:2025.07.01"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")

    // Debug-only
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    implementation("com.google.maps.android:maps-compose:6.7.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.9.0")

    // https://androidx.dev/snapshots/builds
    implementation("androidx.navigation3:navigation3-runtime-android:1.0.0-alpha06")
    implementation("androidx.navigation3:navigation3-ui-android:1.0.0-alpha06")
    implementation("androidx.navigationevent:navigationevent:1.0.0-alpha05")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.9.2")
}
