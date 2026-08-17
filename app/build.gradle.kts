plugins { id("com.android.application"); id("org.jetbrains.kotlin.android") }
android {
    namespace = "com.sayanthrock.rockbrowser"; compileSdk = 34
    defaultConfig { applicationId = "com.sayanthrock.rockbrowser"; minSdk = 29; targetSdk = 34; versionCode = 1; versionName = "1.0.0"; testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"; vectorDrawables { useSupportLibrary = true } }
    signingConfigs {
        val keystoreFile = rootProject.file("test-release-key.jks")
        if (keystoreFile.exists()) {
            create("release") {
                storeFile = keystoreFile
                storePassword = System.getenv("KEYSTORE_PASSWORD") ?: "testpassword"
                keyAlias = "my-alias"
                keyPassword = System.getenv("KEY_PASSWORD") ?: "testpassword"
            }
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            val keystoreFile = rootProject.file("test-release-key.jks")
            if (keystoreFile.exists()) {
                signingConfig = signingConfigs.getByName("release")
            }
        }
    }
    compileOptions { sourceCompatibility = JavaVersion.VERSION_17; targetCompatibility = JavaVersion.VERSION_17 }; kotlinOptions { jvmTarget = "17" }; buildFeatures { compose = true }; composeOptions { kotlinCompilerExtensionVersion = "1.5.1" }
}
dependencies {
    implementation("androidx.core:core-ktx:1.12.0"); implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0"); implementation("androidx.activity:activity-compose:1.8.2"); implementation(platform("androidx.compose:compose-bom:2024.02.00")); implementation("androidx.compose.ui:ui"); implementation("androidx.compose.ui:ui-graphics"); implementation("androidx.compose.ui:ui-tooling-preview"); implementation("androidx.compose.material3:material3"); implementation("androidx.navigation:navigation-compose:2.7.7"); implementation("androidx.webkit:webkit:1.10.0")
    implementation(project(":core:common")); implementation(project(":core:database")); implementation(project(":core:datastore")); implementation(project(":core:network")); implementation(project(":core:security")); implementation(project(":core:designsystem")); implementation(project(":browser")); implementation(project(":feature:home")); implementation(project(":feature:tabs")); implementation(project(":feature:bookmarks")); implementation(project(":feature:history")); implementation(project(":feature:downloads")); implementation(project(":feature:private")); implementation(project(":feature:settings")); implementation(project(":feature:github")); implementation(project(":feature:about"))
}
