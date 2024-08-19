@file:Suppress("UnstableApiUsage")

plugins {
	alias(libs.plugins.android.application)
	alias(libs.plugins.jetbrains.kotlin.android)
	alias(libs.plugins.compose.compiler)
	alias(libs.plugins.serialization)
	alias(libs.plugins.ksp)
	alias(libs.plugins.google.dagger.hilt.android)
	alias(libs.plugins.realm)
}

android {

	namespace = "com.zsoltbertalan.pokedexterous"

	defaultConfig {
		applicationId = "com.zsoltbertalan.pokedexterous"
		versionCode = libs.versions.versionCode.get().toInt()
		versionName = libs.versions.versionName.toString()
		vectorDrawables.useSupportLibrary = true
		minSdk = libs.versions.minSdkVersion.get().toInt()
		compileSdk = libs.versions.compileSdkVersion.get().toInt()
		targetSdk = libs.versions.targetSdkVersion.get().toInt()
		testInstrumentationRunner = "com.zsoltbertalan.pokedexterous.PokedexterousAndroidJUnitRunner"
	}

	buildTypes {

		getByName("release") {
			isDebuggable = false
			isMinifyEnabled = true
			proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
		}

		getByName("debug") {
			isMinifyEnabled = false
		}

	}

	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_17
		targetCompatibility = JavaVersion.VERSION_17
	}

	buildFeatures {
		buildConfig = true
		compose = true
	}


	testOptions {
		//Needed for Mockk
		packaging { jniLibs { useLegacyPackaging = true } }
		//Needed for paging library
		unitTests.isReturnDefaultValues = true
	}

	packaging {
		resources.excludes.add("MANIFEST.MF")
		resources.excludes.add("META-INF/LICENSE")
		resources.excludes.add("META-INF/LICENSE.txt")
		resources.excludes.add("META-INF/LICENSE.md")
		resources.excludes.add("META-INF/LICENSE-notice.md")
		resources.excludes.add("META-INF/MANIFEST.MF")
		resources.excludes.add("META-INF/NOTICE.txt")
		resources.excludes.add("META-INF/rxjava.properties")
		resources.excludes.add("jsr305_annotations/Jsr305_annotations.gwt.xml")
	}

}

dependencies {

	implementation(libs.androidx.activity.compose)
	implementation(libs.androidx.appcompat)
	implementation(platform(libs.androidx.compose.bom))
	implementation(libs.androidx.compose.ui.ui)
	implementation(libs.androidx.compose.ui.graphics)
	implementation(libs.androidx.compose.ui.text)
	implementation(libs.androidx.compose.ui.unit)
	implementation(libs.androidx.compose.ui.tooling)
	implementation(libs.androidx.compose.ui.toolingPreview)
	implementation(libs.androidx.compose.material3)
	implementation(libs.androidx.coreKtx)
	implementation(libs.androidx.constraintLayout)
	implementation(libs.androidx.navigation.uiKtx)
	implementation(libs.androidx.lifecycle.runtime.compose)
	implementation(libs.androidx.navigation.compose)
	implementation(libs.androidx.hilt.navigation.compose)
	implementation(libs.google.material)
	implementation(libs.androidx.coreKtx)
	implementation(libs.kotlinResult.result)
	implementation(libs.kotlinResult.coroutines)
	implementation(libs.kotlinx.serialization.json)
	implementation(libs.kotlin.parcelize.runtime)
	implementation(libs.squareUp.okhttp3.loggingInterceptor)
	implementation(libs.timber)
	implementation(libs.realm.base)

	implementation(libs.google.gson)
	implementation(libs.squareUp.retrofit2.retrofit)
	implementation(libs.squareUp.retrofit2.converterGson)

	androidTestImplementation(libs.squareUp.okhttp3.loggingInterceptor)

	implementation(libs.google.dagger.core)
	add("ksp", libs.google.dagger.compiler)
	kspTest(libs.google.dagger.compiler)
	kspAndroidTest(libs.google.dagger.compiler)

	implementation(libs.google.dagger.hilt.android)
	add("ksp", libs.androidx.hilt.compiler)
	kspTest(libs.androidx.hilt.compiler)
	add("ksp", libs.google.dagger.hilt.androidCompiler)
	kspTest(libs.google.dagger.hilt.androidCompiler)
	kspAndroidTest(libs.google.dagger.hilt.androidCompiler)

	implementation(libs.coil)

	implementation(libs.inject)

	testImplementation(libs.androidx.test.coreKtx)
	testImplementation(libs.androidx.test.ext.jUnit)
	testImplementation(libs.test.mockk.core)
	testImplementation(libs.kotlinx.coroutines.test)
	testImplementation(libs.test.kotest.assertions.core)
	testImplementation(libs.androidx.paging.testing)

	debugRuntimeOnly(platform(libs.androidx.compose.bom))
	//Needed for createComposeRule, NOT ONLY for createAndroidComposeRule, as in the docs
	debugRuntimeOnly(libs.androidx.compose.ui.testManifest)

	androidTestImplementation(libs.test.mockk.android)
	androidTestImplementation(libs.androidx.test.espresso.core)
	androidTestImplementation(libs.androidx.test.ext.jUnit)
	androidTestImplementation(libs.androidx.test.rules)
	androidTestImplementation(libs.androidx.test.runner)
	androidTestImplementation(libs.androidx.compose.ui.test.android)
	androidTestImplementation(libs.androidx.compose.ui.test.junit4)
	androidTestImplementation(libs.androidx.compose.ui.test.junit4.android)
	androidTestImplementation(libs.google.gson)
	androidTestImplementation(libs.google.dagger.hilt.androidTesting)
	androidTestImplementation(libs.squareUp.retrofit2.retrofit)
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
	compilerOptions.freeCompilerArgs.add("-opt-in=androidx.compose.material3.ExperimentalMaterial3Api")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
	compilerOptions.freeCompilerArgs.add("-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
	compilerOptions.freeCompilerArgs.add("-opt-in=androidx.compose.ui.test.ExperimentalTestApi")
}
