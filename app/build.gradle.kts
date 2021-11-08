import org.gradle.internal.impldep.org.bouncycastle.asn1.iana.IANAObjectIdentifiers.experimental

plugins {
    id("com.android.application")
    id("dagger.hilt.android.plugin")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
    id("kotlin-android")
    id("com.google.gms.google-services")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    compileSdkVersion(Versions.compileSdkVersion)
    buildToolsVersion(Versions.buildToolsVersion)

//    buildFeatures {
//        viewBinding = true
//    }

    viewBinding {
        isEnabled = true
    }

    lintOptions {
        disable ("TypographyFractions","TypographyQuotes", "UnsafeExperimentalUsageError", "UnsafeExperimentalUsageWarning")
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }

    defaultConfig {
        applicationId = "com.fruktoland.app"
        minSdkVersion(Versions.minSdkVersion)
        targetSdkVersion(31)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
        javaCompileOptions {
            annotationProcessorOptions {
                arguments += mapOf("room.schemaLocation" to "$projectDir/schemas")
            }
        }
    }

    buildTypes {
        getByName("debug") {
            isTestCoverageEnabled = true
        }

        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }


    sourceSets.getByName("androidTest") {
        assets.srcDirs(File("resources"))
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

}

dependencies {

    implementation("androidx.core:core-ktx:${Versions.androidxCoreKtx}")
    implementation ("androidx.appcompat:appcompat:${Versions.androidxAppcompat}")
    implementation ("com.google.android.material:material:${Versions.androidMaterial}")
    implementation ("androidx.constraintlayout:constraintlayout:${Versions.androidxConstraintlayout}")
    implementation("com.google.firebase:firebase-common-ktx:20.0.0")
//    testImplementation ("junit:junit:4.+")
//    androidTestImplementation ("androidx.test.ext:junit:1.1.3")
//    androidTestImplementation ("androidx.test.espresso:espresso-core:3.4.0")

    //Firebase
    implementation ("com.google.firebase:firebase-bom:${Versions.firebaseVersion}")
    implementation("com.google.firebase:firebase-analytics:${Versions.firebaseAnalyticsVersion}")
    implementation("com.google.firebase:firebase-messaging:${Versions.firebaseMessagingVersion}")
//    implementation ("com.google.firebase:firebase-messaging-ktx:${Versions.firebaseVersion}")
//    implementation ("com.google.firebase:firebase-messaging:23.0.0")
//    implementation ("com.google.firebase:firebase-analytics-ktx:${Versions.firebaseVersion}")

    //Navigation
    implementation ("androidx.navigation:navigation-fragment-ktx:${Versions.navVersion}")
    implementation ("androidx.navigation:navigation-ui-ktx:${Versions.navVersion}")

    implementation("androidx.constraintlayout:constraintlayout:${Versions.androidxConstraintlayout}")
    implementation("com.afollestad.material-dialogs:core:${Versions.materialDialogs}")
    implementation("com.afollestad.material-dialogs:files:${Versions.materialDialogs}")
    implementation("com.afollestad.material-dialogs:input:${Versions.materialDialogs}")

    // Activity Ktx for by viewModels()
    implementation("androidx.fragment:fragment-ktx:${Versions.fragmentKTX}")
    implementation("androidx.activity:activity-ktx:${Versions.activityKTX}")

    //ViewModel & LiveData
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycleViewmodel}")
    implementation("androidx.lifecycle:lifecycle-extensions:${Versions.lifecycleExtensions}")

    // Room
    implementation("androidx.room:room-runtime:${Versions.roomVersion}")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    kapt("androidx.room:room-compiler:${Versions.roomVersion}")
    implementation("androidx.room:room-ktx:${Versions.roomVersion}")

//    //Dagger - Hilt
//    implementation("com.google.dagger:hilt-android:${Versions.hiltVersion}")
//    kapt("androidx.hilt:hilt-compiler:${Versions.hiltJetpack}")
//    kapt("com.google.dagger:hilt-android-compiler:${Versions.hiltVersion}")
//    kapt("com.google.dagger:hilt-compiler:${Versions.hiltVersion}")

    implementation ("com.google.dagger:hilt-android:2.40")
    kapt ("com.google.dagger:hilt-compiler:2.40")

    // For instrumentation tests
    androidTestImplementation  ("com.google.dagger:hilt-android-testing:2.40")
    kaptAndroidTest ("com.google.dagger:hilt-compiler:2.40")

    // For local unit tests
    testImplementation ("com.google.dagger:hilt-android-testing:2.40")
    kaptTest ("com.google.dagger:hilt-compiler:2.40")

    //Logger
    implementation("org.slf4j:slf4j-api:${Versions.slf4jVersion}")

    //Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutinesVersions}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutinesVersions}")
//
//    //Work
//    implementation("androidx.work:work-runtime-ktx:2.4.0")
//
    //Debug only
    debugImplementation("com.amitshekhar.android:debug-db:1.0.6")

    // Database (ROOM) - Test helpers
    testImplementation("androidx.room:room-testing:${Versions.roomVersion}")
    androidTestImplementation("androidx.room:room-testing:${Versions.roomVersion}")

    //Input Mask library
    implementation ("com.redmadrobot:input-mask-android:${Versions.inputMaskVersion}")
    implementation ("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlinVersion}")

    //Retrofit
    implementation ("com.squareup.retrofit2:retrofit:${Versions.retrofitVersion}")
    implementation("com.squareup.retrofit2:converter-gson:${Versions.retrofitVersion}")
    implementation("com.google.code.gson:gson:${Versions.gsonVersion}")
    implementation ("com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:${Versions.retrofit2CoroutinesVersion}")

    //Picasso
    implementation ("com.squareup.picasso:picasso:${Versions.picassoVersion}")

}

kapt {
    correctErrorTypes = true
}
