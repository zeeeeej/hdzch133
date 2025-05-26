plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "com.yunnext.pad.app"
    compileSdk = 34

    val versionCodeCommon = 15
    defaultConfig {
        applicationId = "com.yunnext.pad.app"
        minSdk = 28
        targetSdk = 31
        versionCode = versionCodeCommon
        versionName = "0.0.$versionCodeCommon"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.13"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    flavorDimensions("server")

    productFlavors {

        create("_HDDev") {
            dimension = "server"
            applicationIdSuffix = ".dev"
            versionNameSuffix = "-dev"

            buildConfigField("String", "HOST", "\"https://test-bt-test.yunext.com/\"")
        }

        create("_Dev") {
            dimension = "server"
            applicationIdSuffix = ".alpha"
            versionNameSuffix = "-alpha"
            buildConfigField("String", "HOST", "\"https://blesmarttest.test.com.cn/\"")
        }

        create("_Release") {
            dimension = "server"
            applicationIdSuffix = ""
            versionNameSuffix = ""
            buildConfigField("String", "HOST", "\"https://blesmart.test.com.cn/\"")
        }
    }

    buildFeatures.buildConfig = true

    lintOptions {
        disable("GoogleAppIndexingWarning")
        baseline(file("lint-baseline.xml"))// your choice of filename/path here
    }

    applicationVariants.all {
        outputs.forEach { output ->
            val versionName = versionName
            val versionCode = versionCode
            val buildType = buildType.name
            val flavorName = flavorName

            // 自定义 APK 名称
            val apkName = "ZCH133_${flavorName}_${buildType}_v${versionName}_${versionCode}.apk"
            (output as com.android.build.gradle.internal.api.BaseVariantOutputImpl).outputFileName = apkName
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.lifecycle.process)
    implementation(libs.protolite.well.known.types)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(libs.bundles.mlkit)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.constraintlayout.compose)
    implementation(libs.navigation.compose)
    implementation(libs.accompanist.systemuicontroller)
    implementation(libs.bundles.zeeeeej)
    implementation(libs.bundles.kotlinx)
    //implementation(libs.bundles.ktor.client)
    implementation(libs.bundles.http)
    implementation(libs.fastble)
    implementation(libs.coil)

    implementation(project(":okstream"))
    implementation(project(":okserial"))



}