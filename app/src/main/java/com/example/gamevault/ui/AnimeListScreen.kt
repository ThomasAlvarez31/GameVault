package com.duoc.menu.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gamevault.network.Anime
import com.example.gamevault.network.RetrofitInstance
import coil.compose.rememberImagePainter
import com.example.gamevault.R
import kotlinx.coroutines.launch
import coil.compose.AsyncImage

@Composable
fun AnimeListScreen() {
    var animeList by remember { mutableStateOf<List<Anime>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()

    // Realizamos la llamada a la API en un hilo en segundo plano
    LaunchedEffect(true) {
        try {
            val response = RetrofitInstance.api.getTopAnimes()
            animeList = response.data.take(5) // Obtener solo los 5 más populares
            isLoading = false
        } catch (e: Exception) {
            isLoading = false
            errorMessage = "Error al cargar los datos"
        }
    }

    // Contenedor principal con fondo
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFF3F4F6) // Fondo gris claro
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Barra de encabezado
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo1), // Usa tu propio logo
                    contentDescription = "Logo Anime",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Animes Populares",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E88E5)
                )
            }

            // Verifica si está cargando o si ocurrió un error
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            } else if (errorMessage.isNotEmpty()) {
                Text(text = errorMessage, color = Color.Red, modifier = Modifier.align(Alignment.CenterHorizontally))
            } else {
                // Lista de animes
                LazyColumn(modifier = Modifier.padding(16.dp)) {
                    items(animeList) { anime ->
                        AnimePostItem(anime)
                    }
                }
            }
        }
    }
}

@Composable
fun AnimePostItem(anime: Anime) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        shape = RoundedCornerShape(12.dp),
        color = Color.White
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            AsyncImage(
                model = anime.image_url,
                contentDescription = anime.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = anime.title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1E88E5)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Mostrar géneros
            val genreText = if (anime.genres.isNotEmpty()) {
                anime.genres.joinToString(", ") { it.name }
            } else {
                "Sin géneros disponibles"
            }
            Text(
                text = "Géneros: $genreText",
                fontSize = 14.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Mostrar estado de emisión
            Text(
                text = "Estado: ${anime.status}",
                fontSize = 14.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Mostrar cantidad de capítulos, si es null mostramos "Desconocido"
            val episodesText = anime.episodes?.toString() ?: "Desconocido"
            Text(
                text = "Episodios: $episodesText",
                fontSize = 14.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Tipo: ${anime.type ?: "Tipo desconocido"}",
                fontSize = 14.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = { /* Acción para ver detalles */ },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFFC107),
                    contentColor = Color.Black
                )
            ) {
                Text("Ver publicación")
            }
        }
    }
}
