pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()  // Ajoutez également 'google' ici si nécessaire
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}

rootProject.name = "LecteurMulti"
include(":app")
