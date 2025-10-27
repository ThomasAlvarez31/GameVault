package com.example.registrationactivity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.registrationactivity.SharedPreferencesHelper
import com.example.registrationactivity.R // <--- Importa la clase de recursos R
class RegistrationActivity : AppCompatActivity() {

    private lateinit var usernameInput: EditText
    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var confirmPasswordInput: EditText
    private lateinit var registerButton: Button

    // Inicializamos el Helper para la persistencia de datos
    private lateinit var prefsHelper: SharedPreferencesHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Asegúrate de que tu layout XML se llama activity_registration
        setContentView(R.layout.activity_registration)

        prefsHelper = SharedPreferencesHelper(this)

        // Inicializar vistas (asegúrate de que estos IDs coincidan con tu XML)
        usernameInput = findViewById(R.id.etUsername)
        emailInput = findViewById(R.id.etEmail)
        passwordInput = findViewById(R.id.etPassword)
        confirmPasswordInput = findViewById(R.id.etConfirmPassword)
        registerButton = findViewById(R.id.btnRegister)

        registerButton.setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser() {
        val username = usernameInput.text.toString().trim()
        val email = emailInput.text.toString().trim()
        val password = passwordInput.text.toString()
        val confirmPassword = confirmPasswordInput.text.toString()

        // 1. Validaciones
        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show()
            return
        }

        if (password != confirmPassword) {
            Toast.makeText(this, "Las contraseñas no coinciden. Inténtalo de nuevo.", Toast.LENGTH_SHORT).show()
            return
        }

        // Una validación de email simple (solo para el ejemplo)
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Formato de correo electrónico inválido.", Toast.LENGTH_SHORT).show()
            return
        }

        // 2. Comprobar si el usuario (por email) ya existe
        if (prefsHelper.userExists(email)) {
            Toast.makeText(this, "El correo ya está registrado.", Toast.LENGTH_SHORT).show()
            return
        }

        // 3. Guardar el Usuario
        prefsHelper.saveUser(username, email, password)

        // 4. Mostrar éxito y Redirigir
        Toast.makeText(this, "¡Registro exitoso! Por favor, inicia sesión.", Toast.LENGTH_LONG).show()

        // Redirección a tu LoginActivity
        // NOTA: Para que esta línea funcione, debes tener un archivo LoginActivity.kt
        // y su correspondiente definición en AndroidManifest.xml
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish() // Cierra la actividad de registro
    }
}
