plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.parcelize)
}

android {
    namespace = "com.example.zt_composttest"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.zt_composttest"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(libs.google.android.material)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx.v190)
    implementation(libs.androidx.lifecycle.runtime.ktx.v262)
    implementation(libs.androidx.activity.compose.v180)
    implementation(libs.androidx.compose.bom.v20250400)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material3.window.size.class1.android)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit.v115)
    androidTestImplementation(libs.androidx.espresso.core.v351)
    androidTestImplementation(libs.androidx.compose.bom.v20230300)
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(libs.okhttp)

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0") // 或者最新版本
    implementation("com.squareup.retrofit2:converter-gson:2.9.0") // 或者你选择的转换器，如 Moshi, Jackson

    // OkHttp (Retrofit 会传递依赖，但显式声明可以控制版本，并用于高级配置)
    implementation("com.squareup.okhttp3:okhttp:4.10.0") // 或者最新版本
    implementation("com.squareup.okhttp3:logging-interceptor:4.10.0") // 用于日志记录 (可选)

    // ViewModel 和 LiveData (或 Flow)
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0") // 或者最新版本
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0") // 如果使用 LiveData
    // 或者使用 Kotlin Coroutines Flow (推荐)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3") // 或者最新版本

    // Gson (如果你使用 GsonConverter)
    implementation("com.google.code.gson:gson:2.10.1") // 或者最新版本

    // Fragment KTX for viewModels delegate
    implementation("androidx.fragment:fragment-ktx:1.6.2") // 或者查看最新的稳定版本

    // 你可能已经有了这些，但为了完整性：
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")// ViewModel KTX
    implementation("androidx.activity:activity-ktx:1.8.2") // 如果在 Activity 中也使用 viewModels

}