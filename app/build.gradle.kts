plugins {
    id("com.android.application")
    kotlin("android") version "1.8.0" // Assurez-vous d'importer la bonne version de Kotlin
}


android {
    namespace = "com.example.capstone"
    compileSdk = 34
    // Utilisez SDK 33

    defaultConfig {
        applicationId = "com.example.capstone"
        minSdk = 21
        targetSdk = 33  // Utilisez SDK 33
        versionCode = 1
        versionName = "1.0"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8 // Utilisez JavaVersion pour la compatibilité source
        targetCompatibility = JavaVersion.VERSION_1_8 // Utilisez JavaVersion pour la compatibilité cible
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.6.1") // Version mise à jour
    implementation("com.google.android.material:material:1.10.0") // Version mise à jour
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation(kotlin("stdlib", "1.8.0"))
    implementation(libs.activity) // Utilisation correcte pour la version de Kotlin
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation(libs.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)


}