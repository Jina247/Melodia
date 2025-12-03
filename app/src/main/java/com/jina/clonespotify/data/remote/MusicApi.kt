package com.jina.clonespotify.data.remote

import com.jina.clonespotify.data.model.JamendoTrackResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface MusicApi {
    @GET("tracks")
    suspend fun searchTracks(
        @Query("client_id") clientId: String,
        @Query("tags") tags: String,
        @Query("limit") limit: Int = 20,
        @Query("format") format: String = "json"
    ): JamendoTrackResponse

}

object RetrofitInstance {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.jamendo.com/v3.0/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api: MusicApi = retrofit.create(MusicApi::class.java)
}