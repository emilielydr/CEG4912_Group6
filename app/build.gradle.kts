import com.android.build.api.dsl.Packaging

plugins {
    id("com.android.application")
    kotlin("android") version "1.8.0" // Assurez-vous d'importer la bonne version de Kotlin

    id("com.google.gms.google-services")
    // Add the dependency for the Performance Monitoring Gradle plugin
    id("com.google.firebase.firebase-perf") version "1.4.2" apply false
}



android {
    namespace = "com.example.capstone"
    compileSdk = 35
    // Utilisez SDK 33

    defaultConfig {
        applicationId = "com.example.capstone"
        minSdk = 23
        targetSdk = 34 // Utilisez SDK 33
        versionCode = 1
        versionName = "1.0"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17 // Utilisez JavaVersion pour la compatibilité source
        targetCompatibility = JavaVersion.VERSION_17 // Utilisez JavaVersion pour la compatibilité cible
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    packaging {
        // Resolve conflicts with the pickFirst option
        resources.pickFirsts.add("META-INF/NOTICE.md")
        resources.pickFirsts.add("META-INF/LICENSE.md")
        resources.pickFirsts.add("META-INF/DEPENDENCIES")

        // You can also exclude files if necessary
        // exclude("META-INF/SOME_FILE.txt")
    }
}

dependencies {
   // classpath("com.android.tools.build:gradle:7.3.0")
    implementation("androidx.appcompat:appcompat:1.6.1") // Version mise à jour
    implementation("com.google.android.material:material:1.10.0") // Version mise à jour
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation(kotlin("stdlib", "1.8.0"))
    implementation(libs.activity)
    implementation(libs.firebase.database) // Utilisation correcte pour la version de Kotlin
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation(platform("com.google.firebase:firebase-bom:33.6.0"))
    implementation(platform("com.google.firebase:firebase-bom:32.2.2")) // Remplacez par la version récente si nécessaire
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation ("com.sendgrid:sendgrid-java:4.7.0")

    implementation(libs.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation("com.google.firebase:firebase-analytics")


    implementation("com.google.android.things:androidthings:1.0")
    implementation("com.jcraft:jsch:0.1.55")
    // Import the BoM for the Firebase platform
    implementation(platform("com.google.firebase:firebase-bom:33.11.0"))

    // Add the dependency for the Performance Monitoring library
    // When using the BoM, you don't specify versions in Firebase library dependencies
    implementation("com.google.firebase:firebase-perf")
    implementation ("com.sun.mail:android-mail:1.6.6")

    implementation ("com.sun.mail:android-activation:1.6.6")
    implementation ("com.google.android.gms:play-services-location:18.0.0")




}