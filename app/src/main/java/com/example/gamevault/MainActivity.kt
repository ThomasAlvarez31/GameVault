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
import com.duoc.menu.ui.AnimeDetailScreen
import com.duoc.menu.ui.AnimeListScreen
import com.duoc.menu.ui.AnimeSearchScreen
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
                            selected = navController.currentBackStackEntry?.destination?.route == "search",
                            onClick = { navController.navigate("search") },
                            label = { Text("Buscar") },
                            icon = { Icon(Icons.Filled.Home, "Buscar") } // Cambia icono si quieres
                        )

                    }
                }

            ){ innerPadding ->
                NavHost(
                    navController = navController,
                    startDestination= "home",
                    modifier= Modifier.padding(innerPadding)
                ) {
                    composable("home") { AnimeListScreen(navController) }
                    composable("search") { AnimeSearchScreen(navController) }
                    composable("detail/{animeId}") { backStackEntry ->
                        val animeId = backStackEntry.arguments?.getString("animeId")?.toIntOrNull()
                        animeId?.let { AnimeDetailScreen(it) }
                    }
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