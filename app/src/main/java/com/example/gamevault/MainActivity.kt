package com.example.gamevault

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.duoc.menu.ui.AnimeListScreen
import com.example.gamevault.ui.theme.GameVaultTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GameVaultTheme {
            //Controlador para recordar en que pantalla estoy
            val navController = rememberNavController() //controlador de nav
            //Estructura visual para la barra de nav
            Scaffold(
                bottomBar = {
                    NavigationBar {
                        NavigationBarItem(
                            //ruta princiapl
                            selected = navController.currentBackStackEntry?.destination?.route=="home",
                            onClick = {navController.navigate("home")},
                            label = {Text("inicio")},
                            icon = { Icon(Icons.Filled.Home, "Inicio") }
                        )
                        NavigationBarItem(
                            //ruta princiapl
                            selected = navController.currentBackStackEntry?.destination?.route=="form",
                            onClick = {navController.navigate("form")},
                            label = {Text("Formulario")},
                            icon = { Icon(Icons.Filled.Home, "Formulario") }
                        )

                    }
                }

            ){ innerPadding ->
                NavHost(
                    navController = navController,
                    startDestination= "home",
                    modifier= Modifier.padding(innerPadding)
                ){
                    composable("home"){ AnimeListScreen()}
                    //composable("form"){(FormularioScreen())}
                }

            }
        }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GameVaultTheme {

    }
}