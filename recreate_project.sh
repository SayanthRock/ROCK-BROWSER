#!/bin/bash
set -e

# Settings and build.gradle
cat << 'KTS' > settings.gradle.kts
pluginManagement { repositories { google(); mavenCentral(); gradlePluginPortal() } }
dependencyResolutionManagement { repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS); repositories { google(); mavenCentral() } }
rootProject.name = "RockBrowser"
include(":app", ":core:common", ":core:database", ":core:datastore", ":core:network", ":core:security", ":core:designsystem", ":browser", ":feature:home", ":feature:tabs", ":feature:bookmarks", ":feature:history", ":feature:downloads", ":feature:private", ":feature:settings", ":feature:github", ":feature:about")
KTS

cat << 'KTS' > build.gradle.kts
plugins {
    id("com.android.application") version "8.3.0" apply false
    id("com.android.library") version "8.3.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("com.google.devtools.ksp") version "1.9.0-1.0.13" apply false
}
KTS

cat << 'KTS' > gradle.properties
org.gradle.jvmargs=-Xmx4096m -Dfile.encoding=UTF-8
android.useAndroidX=true
kotlin.code.style=official
android.nonTransitiveRClass=true
KTS

# Setup modules
for module in core/common core/database core/datastore core/network core/security core/designsystem browser feature/home feature/tabs feature/bookmarks feature/history feature/downloads feature/private feature/settings feature/github feature/about; do
    mkdir -p $module/src/main/java/com/sayanthrock/rockbrowser/${module//\//\.}
    cat << 'XML' > $module/src/main/AndroidManifest.xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"/>
XML

    namespace_suffix=$(echo "$module" | sed 's/\//\./g')
    cat << KTS > $module/build.gradle.kts
plugins { id("com.android.library"); id("org.jetbrains.kotlin.android") }
android { namespace = "com.sayanthrock.rockbrowser.${namespace_suffix}"; compileSdk = 34; defaultConfig { minSdk = 29 }; compileOptions { sourceCompatibility = JavaVersion.VERSION_17; targetCompatibility = JavaVersion.VERSION_17 }; kotlinOptions { jvmTarget = "17" }; buildFeatures { compose = true }; composeOptions { kotlinCompilerExtensionVersion = "1.5.1" } }
dependencies { implementation("androidx.core:core-ktx:1.12.0"); implementation("androidx.appcompat:appcompat:1.6.1"); implementation("com.google.android.material:material:1.11.0"); implementation(platform("androidx.compose:compose-bom:2024.02.00")); implementation("androidx.compose.ui:ui"); implementation("androidx.compose.ui:ui-graphics"); implementation("androidx.compose.material3:material3") }
KTS
done

# App Module
mkdir -p app/src/main/java/com/sayanthrock/rockbrowser/ui
mkdir -p app/src/main/res/values app/src/main/res/xml app/src/main/res/drawable app/src/main/res/mipmap-anydpi-v26
cat << 'XML' > app/src/main/AndroidManifest.xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android" xmlns:tools="http://schemas.android.com/tools">
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.CAMERA" />
    <uses-feature android:name="android.hardware.camera" android:required="false" />
    <uses-permission android:name="android.permission.RECORD_AUDIO" />
    <uses-feature android:name="android.hardware.microphone" android:required="false" />
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
    <uses-permission android:name="android.permission.POST_NOTIFICATIONS" />
    <application android:networkSecurityConfig="@xml/network_security_config" android:allowBackup="true" android:dataExtractionRules="@xml/data_extraction_rules" android:fullBackupContent="@xml/backup_rules" android:icon="@mipmap/ic_launcher" android:label="ROCK BROWSER" android:roundIcon="@mipmap/ic_launcher_round" android:supportsRtl="true" android:theme="@style/Theme.RockBrowser" tools:targetApi="31">
        <activity android:name=".MainActivity" android:exported="true" android:label="ROCK BROWSER" android:theme="@style/Theme.RockBrowser">
            <intent-filter><action android:name="android.intent.action.MAIN" /><category android:name="android.intent.category.LAUNCHER" /></intent-filter>
        </activity>
    </application>
</manifest>
XML
cat << 'XML' > app/src/main/res/xml/network_security_config.xml
<?xml version="1.0" encoding="utf-8"?><network-security-config><base-config cleartextTrafficPermitted="true"><trust-anchors><certificates src="system" /><certificates src="user" /></trust-anchors></base-config></network-security-config>
XML

cat << 'KTS' > app/build.gradle.kts
plugins { id("com.android.application"); id("org.jetbrains.kotlin.android") }
android {
    namespace = "com.sayanthrock.rockbrowser"; compileSdk = 34
    defaultConfig { applicationId = "com.sayanthrock.rockbrowser"; minSdk = 29; targetSdk = 34; versionCode = 1; versionName = "1.0.0"; testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"; vectorDrawables { useSupportLibrary = true } }
    signingConfigs { create("release") { storeFile = rootProject.file("test-release-key.jks"); storePassword = "testpassword"; keyAlias = "my-alias"; keyPassword = "testpassword" } }
    buildTypes { release { isMinifyEnabled = true; proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"); signingConfig = signingConfigs.getByName("release") } }
    compileOptions { sourceCompatibility = JavaVersion.VERSION_17; targetCompatibility = JavaVersion.VERSION_17 }; kotlinOptions { jvmTarget = "17" }; buildFeatures { compose = true }; composeOptions { kotlinCompilerExtensionVersion = "1.5.1" }
}
dependencies {
    implementation("androidx.core:core-ktx:1.12.0"); implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0"); implementation("androidx.activity:activity-compose:1.8.2"); implementation(platform("androidx.compose:compose-bom:2024.02.00")); implementation("androidx.compose.ui:ui"); implementation("androidx.compose.ui:ui-graphics"); implementation("androidx.compose.ui:ui-tooling-preview"); implementation("androidx.compose.material3:material3"); implementation("androidx.navigation:navigation-compose:2.7.7"); implementation("androidx.webkit:webkit:1.10.0")
    implementation(project(":core:common")); implementation(project(":core:database")); implementation(project(":core:datastore")); implementation(project(":core:network")); implementation(project(":core:security")); implementation(project(":core:designsystem")); implementation(project(":browser")); implementation(project(":feature:home")); implementation(project(":feature:tabs")); implementation(project(":feature:bookmarks")); implementation(project(":feature:history")); implementation(project(":feature:downloads")); implementation(project(":feature:private")); implementation(project(":feature:settings")); implementation(project(":feature:github")); implementation(project(":feature:about"))
}
KTS

# Fix KSP for Room
cat << 'KTS' > core/database/build.gradle.kts
plugins { id("com.android.library"); id("org.jetbrains.kotlin.android"); id("com.google.devtools.ksp") }
android { namespace = "com.sayanthrock.rockbrowser.core.database"; compileSdk = 34; defaultConfig { minSdk = 29 }; compileOptions { sourceCompatibility = JavaVersion.VERSION_17; targetCompatibility = JavaVersion.VERSION_17 }; kotlinOptions { jvmTarget = "17" } }
dependencies { implementation("androidx.core:core-ktx:1.12.0"); implementation("androidx.room:room-runtime:2.6.1"); implementation("androidx.room:room-ktx:2.6.1"); ksp("androidx.room:room-compiler:2.6.1") }
KTS

# Create minimal files
cat << 'KT' > app/src/main/java/com/sayanthrock/rockbrowser/MainActivity.kt
package com.sayanthrock.rockbrowser
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
class MainActivity : ComponentActivity() { override fun onCreate(savedInstanceState: Bundle?) { super.onCreate(savedInstanceState); setContent { } } }
KT

mkdir -p core/database/src/main/java/com/sayanthrock/rockbrowser/core/database/entity core/database/src/main/java/com/sayanthrock/rockbrowser/core/database/dao
cat << 'KT' > core/database/src/main/java/com/sayanthrock/rockbrowser/core/database/entity/HistoryEntity.kt
package com.sayanthrock.rockbrowser.core.database.entity
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "history") data class HistoryEntity(@PrimaryKey(autoGenerate = true) val id: Long = 0, val url: String, val title: String)
KT

cat << 'KT' > core/database/src/main/java/com/sayanthrock/rockbrowser/core/database/dao/HistoryDao.kt
package com.sayanthrock.rockbrowser.core.database.dao
import androidx.room.Dao
import androidx.room.Query
import com.sayanthrock.rockbrowser.core.database.entity.HistoryEntity
import kotlinx.coroutines.flow.Flow
@Dao interface HistoryDao { @Query("SELECT * FROM history") fun getAllHistory(): Flow<List<HistoryEntity>> }
KT

cat << 'KT' > core/database/src/main/java/com/sayanthrock/rockbrowser/core/database/RockDatabase.kt
package com.sayanthrock.rockbrowser.core.database
import androidx.room.Database
import androidx.room.RoomDatabase
import com.sayanthrock.rockbrowser.core.database.dao.HistoryDao
import com.sayanthrock.rockbrowser.core.database.entity.HistoryEntity
@Database(entities = [HistoryEntity::class], version = 1, exportSchema = false)
abstract class RockDatabase : RoomDatabase() { abstract fun historyDao(): HistoryDao }
KT

# Create GitHub Actions workflow
mkdir -p .github/workflows
cat << 'YML' > .github/workflows/android.yml
name: Android CI
on:
  push:
    branches: [ "main" ]
  pull_request:
    branches: [ "main" ]
jobs:
  build:
    runs-on: ubuntu-latest
    steps:
    - uses: actions/checkout@v4
    - name: set up JDK 17
      uses: actions/setup-java@v4
      with:
        java-version: '17'
        distribution: 'temurin'
        cache: gradle
    - name: Grant execute permission for gradlew
      run: chmod +x gradlew
    - name: Build with Gradle
      run: ./gradlew assembleDebug
    - name: Upload Debug APK
      uses: actions/upload-artifact@v4
      with:
        name: app-debug
        path: app/build/outputs/apk/debug/app-debug.apk
YML

# Docs
cat << 'DOC' > SECURITY.md
# Security Policy
## Supported Versions
| Version | Supported          |
| ------- | ------------------ |
| 1.0.0   | :white_check_mark: |
DOC

# Add .gitignore
cat << 'IGN' > .gitignore
*.iml
.gradle/
.gradle
/local.properties
/.idea/caches
/.idea/libraries
/.idea/modules.xml
/.idea/workspace.xml
/.idea/navEditor.xml
/.idea/assetWizardSettings.xml
.DS_Store
/build
*/build
captures
.externalNativeBuild
.cxx
local.properties
*.jks
*.keystore

# Build folders
build/
app/build/
browser/build/
core/*/build/
feature/*/build/
IGN

# Create Release Key
keytool -genkey -v -keystore test-release-key.jks -keyalg RSA -keysize 2048 -validity 10000 -alias my-alias -storepass testpassword -keypass testpassword -dname "CN=Sayanth Rock, OU=RockBrowser, O=Sayanth Rock, L=City, ST=State, C=US" > /dev/null 2>&1
