package com.example.gamevault.network

data class AnimeResponse(
    val data: List<Anime>
)

data class AnimeDetailResponse(
    val data: Anime
)

data class Anime(
    val mal_id: Int,
    val title: String,
    val synopsis: String?,
    val images: AnimeImages,
    val genres: List<Genre>,
    val status: String,
    val episodes: Int?,
    val type: String?
) {
    val image_url: String
        get() = images.jpg.image_url
}


data class Genre(
    val name: String
)

data class AnimeImages(
    val jpg: AnimeImageDetail
)

data class AnimeImageDetail(
    val image_url: String
)

