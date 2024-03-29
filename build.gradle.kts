// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        jcenter()
        mavenCentral()
//        maven("https://jitpack.io")
    }
    dependencies {
        classpath ("com.android.tools.build:gradle:7.0.2")
        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlinVersion}")
        classpath ("com.google.dagger:hilt-android-gradle-plugin:2.40")
        classpath ("androidx.navigation:navigation-safe-args-gradle-plugin:2.3.5")
//        classpath ("com.android.tools.build:gradle:4.2.2")
//        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlinVersion}")
//        classpath ("com.google.dagger:hilt-android-gradle-plugin:${Versions.hiltVersion}")
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files

        classpath ("com.google.gms:google-services:4.3.10")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}