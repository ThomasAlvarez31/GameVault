package com.duoc.menu.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.gamevault.network.Anime
import com.example.gamevault.network.RetrofitInstance
import kotlinx.coroutines.launch

// 📌 Modelo de datos local para los comentarios
data class Comment(val title: String, val content: String)

@Composable
fun AnimeDetailScreen(animeId: Int) {
    // Estado principal del detalle del anime
    var anime by remember { mutableStateOf<Anime?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf("") }

    // Estados visuales
    var showFullSynopsis by remember { mutableStateOf(false) } // controla si se muestra toda la sinopsis
    var liked by remember { mutableStateOf<Boolean?>(null) } // controla si el usuario dio like/dislike
    var showCommentDialog by remember { mutableStateOf(false) } // controla si se muestra el cuadro de comentario
    var comments by remember { mutableStateOf(listOf<Comment>()) } // lista de comentarios guardados localmente

    val coroutineScope = rememberCoroutineScope()

    // Llamada a la API para obtener los detalles del anime
    LaunchedEffect(animeId) {
        coroutineScope.launch {
            try {
                val response = RetrofitInstance.api.getAnimeById(animeId)
                anime = response.data
            } catch (e: Exception) {
                errorMessage = "Error al cargar detalles"
            } finally {
                isLoading = false
            }
        }
    }

    // Contenedor general de la pantalla
    Box(modifier = Modifier.fillMaxSize()) {
        when {
            // 🔄 Estado de carga
            isLoading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }

            // Error de carga
            errorMessage.isNotEmpty() -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(errorMessage, color = MaterialTheme.colorScheme.error)
            }

            // Datos cargados correctamente
            anime != null -> {
                val currentAnime = anime!!

                // Lista desplazable con el detalle del anime + comentarios
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFF8F9FA))
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Sección principal del detalle del anime
                    item {
                        Card(
                            shape = RoundedCornerShape(16.dp),
                            elevation = CardDefaults.cardElevation(6.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column {
                                // Imagen principal del anime
                                Image(
                                    painter = rememberAsyncImagePainter(currentAnime.image_url),
                                    contentDescription = currentAnime.title,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(250.dp)
                                )

                                Column(Modifier.padding(16.dp)) {
                                    // Título
                                    Text(
                                        text = currentAnime.title,
                                        style = MaterialTheme.typography.headlineSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF1E88E5)
                                    )

                                    Spacer(Modifier.height(8.dp))

                                    //  Mostrar sinopsis (abreviada si es muy larga)
                                    val synopsis = currentAnime.synopsis ?: "Sin descripción"
                                    val shortText = if (synopsis.length > 200)
                                        synopsis.take(200) + "..."
                                    else synopsis

                                    Text(
                                        text = if (showFullSynopsis) synopsis else shortText,
                                        textAlign = TextAlign.Justify
                                    )

                                    // Botón "Ver más / Ver menos" si la sinopsis es larga
                                    if (synopsis.length > 200) {
                                        TextButton(onClick = { showFullSynopsis = !showFullSynopsis }) {
                                            Text(if (showFullSynopsis) "Ver menos" else "Ver más")
                                        }
                                    }

                                    Spacer(Modifier.height(8.dp))
                                    Divider()
                                    Spacer(Modifier.height(8.dp))

                                    // Información adicional del anime
                                    Text("📺 Tipo: ${currentAnime.type ?: "Desconocido"}")
                                    Text("📊 Estado: ${currentAnime.status}")
                                    Text("🎞 Episodios: ${currentAnime.episodes ?: "N/A"}")
                                    Text("🏷 Géneros: ${currentAnime.genres.joinToString { it.name }}")

                                    Spacer(Modifier.height(16.dp))

                                    //  Botones de "Like" y "Dislike"
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(20.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.align(Alignment.CenterHorizontally)
                                    ) {
                                        // Botón Like
                                        IconButton(onClick = { liked = if (liked == true) null else true }) {
                                            Icon(
                                                imageVector = Icons.Outlined.ThumbUp,
                                                contentDescription = "Like",
                                                tint = if (liked == true) Color(0xFF4CAF50) else Color.Gray
                                            )
                                        }

                                        // Botón Dislike
                                        IconButton(onClick = { liked = if (liked == false) null else false }) {
                                            Icon(
                                                imageVector = Icons.Filled.Close,
                                                contentDescription = "Dislike",
                                                tint = if (liked == false) Color(0xFFF44336) else Color.Gray
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        Spacer(Modifier.height(24.dp))

                        //  Encabezado de la sección de comentarios
                        Text(
                            "💬 Comentarios",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    // Mostrar los comentarios guardados
                    items(comments) { comment ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                            Column(Modifier.padding(12.dp)) {
                                Text(comment.title, fontWeight = FontWeight.Bold)
                                Spacer(Modifier.height(4.dp))
                                Text(comment.content, color = Color.DarkGray)
                            }
                        }
                    }
                }

                // FAB (Floating Action Button)
                // Sirve para abrir el cuadro donde se agrega un nuevo comentario
                FloatingActionButton(
                    onClick = { showCommentDialog = true },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(24.dp),
                    containerColor = Color(0xFF1E88E5)
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Agregar comentario", tint = Color.White)
                }

                // Cuadro de diálogo para crear un nuevo comentario
                if (showCommentDialog) {
                    // Variables locales para los campos del comentario
                    var commentTitle by remember { mutableStateOf("") }
                    var commentContent by remember { mutableStateOf("") }

                    AlertDialog(
                        onDismissRequest = { showCommentDialog = false },

                        //  Botón "Agregar" guarda el comentario
                        confirmButton = {
                            TextButton(onClick = {
                                // Se valida que ambos campos no estén vacíos
                                if (commentTitle.isNotBlank() && commentContent.isNotBlank()) {
                                    // Se agrega un nuevo comentario a la lista (inmutable)
                                    // Esto actualiza automáticamente la interfaz gracias a Compose
                                    comments = comments + Comment(commentTitle, commentContent)
                                    // Cierra el diálogo
                                    showCommentDialog = false
                                }
                            }) {
                                Text("Agregar")
                            }
                        },

                        // Botón para cancelar sin guardar
                        dismissButton = {
                            TextButton(onClick = { showCommentDialog = false }) {
                                Text("Cancelar")
                            }
                        },

                        //  Título del diálogo
                        title = { Text("Nuevo comentario") },

                        // Contenido del cuadro (campos de texto)
                        text = {
                            Column {
                                OutlinedTextField(
                                    value = commentTitle,
                                    onValueChange = { commentTitle = it },
                                    label = { Text("Título") },
                                    modifier = Modifier.fillMaxWidth()
                                )
                                Spacer(Modifier.height(8.dp))
                                OutlinedTextField(
                                    value = commentContent,
                                    onValueChange = { commentContent = it },
                                    label = { Text("Contenido") },
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}
