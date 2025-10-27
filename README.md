##Projecto GameVault: Acceso y Comunidad

---
## 🌟 Visión General del Proyecto

GameVault es una aplicación móvil desarrollada en Kotlin para Android diseñada para ser el punto de acceso principal a la información de tus series y películas de anime favoritas. El proyecto combina un sistema de autenticación robusto para gestionar usuarios (Registro/Login) con un visor de contenido enriquecido y una sección comunitaria para comentarios y apreciaciones.Su objetivo principal es proporcionar a los fanáticos una experiencia segura, personalizada y social.

---

## 🛠️ Tecnologías y Requisitos
Este proyecto fue desarrollado utilizando las siguientes herramientas y bibliotecas:
Lenguaje: Kotlin
Plataforma: Android (API 24+)
IDE: Android Studio

---

## 👨‍💻 Integrantes 

| Nombre            | Rol                           |
|-------------------|-------------------------------|
| Nicolas Bello     | Desarrollador, Lider proyecto |
| Thomas Alvarez    | Desarrollador Backend         |

---

## 👨‍💻Requisitos Previos
  Para ejecutar y compilar este proyecto, necesitas tener instalado:
  Android Studio (versión más reciente recomendada).
  JDK (Java Development Kit).
  SDK de Android (el proyecto usa una versión compatible con la mayoría de los dispositivos modernos).
  
---
## 🚀 Guía de Inicio Rápido
Sigue estos pasos para poner en marcha el proyecto:
1. Clonar el Repositorio
´´´
git clone https://github.com/ThomasAlvarez31/GameVault.git
cd GameVault/v1/RegistrationActivity

---

1. Abrir y Sincronizar Gradle
  Abre el proyecto en Android Studio.
  Espera a que Gradle sincronice las dependencias. Si hay errores, verifica que hayas fusionado correctamente todas las dependencias del build.gradle de tu amigo.
2. Ejecución
  Selecciona un dispositivo emulado o físico con Android 7.0 (Nougat) o superior.
  Haz clic en Run 'app'.
El flujo de inicio es obligatorio y secuencial debido al sistema de autenticación:
  RegistrationActivity (Pantalla de Registro / Opción de Login).
  LoginActivity (Validación de Credenciales).
  MainActivity (El Content Vault del Anime).
