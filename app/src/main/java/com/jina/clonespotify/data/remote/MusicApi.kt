package com.jina.clonespotify.data.remote

import retrofit2.Retrofit

interface MusicApi {
    suspend fun searchSong()
}

object RetrofitInstance {
    private val retrofit = Retrofit.Builder()
        .baseUrl("")
}