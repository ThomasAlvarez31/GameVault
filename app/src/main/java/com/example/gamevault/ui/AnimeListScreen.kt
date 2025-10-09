package com.duoc.menu.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gamevault.R
import com.example.gamevault.viewmodel.AnimePost


@Composable
fun AnimeListScreen() {
    // Lista de publicaciones de anime
    val animeList = listOf(
        AnimePost(1, "Naruto", "Un ninja joven y su viaje para convertirse en Hokage.", R.drawable.naruto),
        AnimePost(2, "One Piece", "Un joven pirata busca el tesoro más grande del mundo.", R.drawable.one_piece),
        AnimePost(3, "Attack on Titan", "Humanos luchan por sobrevivir contra gigantes devoradores de personas.", R.drawable.attack_on_titan)
    )

    // Contenedor principal con fondo
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFF3F4F6) // Fondo gris claro
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // ---------- Barra de encabezado ----------

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(16.dp), // Espaciado de la barra
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Logo esquina izquierda
                Image(
                    painter = painterResource(id = R.drawable.logo), // Reemplaza "logo" con el nombre de tu recurso de logo
                    contentDescription = "Logo Anime",
                    modifier = Modifier
                        .size(40.dp) // Tamaño del logo
                        .clip(CircleShape) // Forma circular para el logo
                )

                // Título
                Spacer(modifier = Modifier.weight(1f)) // Esto empuja el texto hacia la derecha
                Text(
                    text = "Animes Populares",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E88E5) // Color azul
                )
            }

            // Lista de publicaciones
            LazyColumn(modifier = Modifier.padding(16.dp)) {
                items(animeList) { animePost ->
                    // Cada item de la lista (una publicación de anime)

                    AnimePostItem(animePost)
                }
            }
        }
    }
}

@Composable
fun AnimePostItem(animePost: AnimePost) {
    // Card para cada publicación
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        shape = RoundedCornerShape(12.dp),
        color = Color.White
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Imagen de la portada
            Image(
                painter = painterResource(id = animePost.imageResId),
                contentDescription = animePost.titulo,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(8.dp)) // Bordes redondeados
            )
            Spacer(modifier = Modifier.height(12.dp))

            // Título del anime
            Text(
                text = animePost.titulo,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1E88E5) // Color azul
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Breve descripción
            Text(
                text = animePost.descripcion,
                fontSize = 14.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Botón "Ver publicación"
            Button(
                onClick = {  },
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
