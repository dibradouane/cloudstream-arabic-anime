
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

buildscript {
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.7.3")
        classpath("com.github.recloudstream:gradle:-SNAPSHOT")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:2.3.0")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }
}

subprojects {
    apply(plugin = "com.android.library")
    apply(plugin = "kotlin-android")
    apply(plugin = "com.lagradost.cloudstream3.gradle")

    extensions.configure<com.lagradost.cloudstream3.gradle.CloudstreamExtension> {
        description = "Anime sources ported from CloudStream-Arabic-Repo"
        authors = listOf("CloudStream Arabic")
        status = 3
        tvTypes = listOf("Anime")
        language = "ar"
        version = 1
        setRepo(
            System.getenv("GITHUB_REPOSITORY")
                ?: "dibradouane/cloudstream-arabic-anime"
        )
    }

    extensions.configure<com.android.build.gradle.LibraryExtension> {
        namespace = "com.v18z1.arabic"
        compileSdk = 35

        defaultConfig {
            minSdk = 21
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }
    }

    tasks.withType<KotlinJvmCompile>().configureEach {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_1_8)
        }
    }

    dependencies {
        add(
            "implementation",
            "com.github.recloudstream.cloudstream:library:-SNAPSHOT"
        )
        add("implementation", kotlin("stdlib"))
        add("implementation", "com.github.Blatzar:NiceHttp:0.4.11")
        add("implementation", "org.jsoup:jsoup:1.18.3")
    }
}
