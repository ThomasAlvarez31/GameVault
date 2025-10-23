package com.example.gamevault.network

import retrofit2.http.GET
import retrofit2.http.Query

interface AnimeApiService {
    @GET("top/anime")
    suspend fun getTopAnimes(): AnimeResponse

    @GET("anime")
    suspend fun searchAnimes(@Query("q") query: String): AnimeResponse
}
