// settings.gradle.kts (Corregido)

pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "RegistrationActivity"
include(":app")

// *** LÍNEA CORREGIDA ***
// Se incluye el módulo con el nombre interno que necesitas (:GameVault1)
// PERO se le indica a Gradle que lo busque en la carpeta externa 'GameVault'
// ASUMIENDO que la carpeta 'GameVault' está al mismo nivel que tu proyecto.
include(":GameVault1")
project(":GameVault1").projectDir = file("../GameVault/app")
// *************************