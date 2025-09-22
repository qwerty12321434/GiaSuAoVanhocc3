plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.giasuaovanhocc3"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.giasuaovanhocc3"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "MONGODB_URI", "\"mongodb://deprecated\"")
        buildConfigField("String", "MONGODB_DB", "\"deprecated\"")
        buildConfigField("String", "API_BASE_URL", "\"http://10.0.2.2:4000\"")
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
        isCoreLibraryDesugaringEnabled = true
    }
    packaging {
        resources {
            excludes += "META-INF/native-image/**"
        }
    }
    buildFeatures {
        buildConfig = true
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.lifecycle.viewmodel)
    implementation(libs.lifecycle.livedata)
    implementation(libs.lifecycle.runtime)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Thêm dòng này để import thư viện MongoDB Java Driver (exclude record codec to avoid desugaring issues)
    implementation("org.mongodb:mongodb-driver-sync:5.1.2") {
        exclude(group = "org.mongodb", module = "bson-record-codec")
    }
    coreLibraryDesugaring(libs.desugar)
}