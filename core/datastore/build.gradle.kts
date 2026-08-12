plugins { id("com.android.library"); id("org.jetbrains.kotlin.android") }
android { namespace = "com.sayanthrock.rockbrowser.core.datastore"; compileSdk = 34; defaultConfig { minSdk = 29 }; compileOptions { sourceCompatibility = JavaVersion.VERSION_17; targetCompatibility = JavaVersion.VERSION_17 }; kotlinOptions { jvmTarget = "17" } }
dependencies { implementation("androidx.core:core-ktx:1.12.0"); implementation("androidx.datastore:datastore-preferences:1.0.0") }
