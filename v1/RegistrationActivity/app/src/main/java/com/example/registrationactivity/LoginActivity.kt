package com.example.registrationactivity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

// ELIMINA CUALQUIER INTENTO DE IMPORTACIÓN A LA CLASE DE TU AMIGO
// Por ejemplo: NO incluyas 'import com.example.gamevault.MainActivity'

class LoginActivity : AppCompatActivity() {

    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var loginButton: Button
    private lateinit var prefsHelper: SharedPreferencesHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        prefsHelper = SharedPreferencesHelper(this)

        emailInput = findViewById(R.id.etEmail)
        passwordInput = findViewById(R.id.etPassword)
        loginButton = findViewById(R.id.btnLogin)

        loginButton.setOnClickListener {
            performLogin()
        }
    }

    private fun performLogin() {
        val email = emailInput.text.toString().trim()
        val password = passwordInput.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos.", Toast.LENGTH_SHORT).show()
            return
        }

        val storedPassword = prefsHelper.getPassword(email)
        if (storedPassword == null) {
            Toast.makeText(this, "Correo no registrado.", Toast.LENGTH_SHORT).show()
            return
        }

        if (storedPassword == password) {
            Toast.makeText(this, "Inicio de sesión exitoso. Redirigiendo a GameVault.", Toast.LENGTH_LONG).show()

            // *** CÓDIGO CLAVE PARA LANZAR LA ACTIVIDAD DEL OTRO MÓDULO ***
            val targetClassName = "com.example.gamevault.MainActivity" // Nombre de paquete EXACTO

            try {
                // 1. Cargamos la clase usando su nombre completo (reflexión)
                val targetClass = Class.forName(targetClassName)

                // 2. Creamos el Intent con la clase cargada
                val intent = Intent(this, targetClass)

                // Limpia la pila para que no pueda volver al Login
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

                startActivity(intent)
                finish() // Cierra el LoginActivity
            } catch (e: ClassNotFoundException) {
                // Si esto falla, el nombre del paquete o la configuración de Gradle/Manifest es incorrecta
                Toast.makeText(this, "ERROR: No se encontró la clase principal de GameVault. Revisar AndroidManifest.", Toast.LENGTH_LONG).show()
                e.printStackTrace()
            }
        } else {
            Toast.makeText(this, "Contraseña incorrecta.", Toast.LENGTH_SHORT).show()
        }
    }
}