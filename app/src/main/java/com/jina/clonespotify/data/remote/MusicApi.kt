package com.jina.clonespotify.data.remote

import com.jina.clonespotify.data.model.JamendoPlaylistResponse
import com.jina.clonespotify.data.model.JamendoPlaylistTracksResponse
import com.jina.clonespotify.data.model.JamendoTrackResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface MusicApi {

    // Search tracks by tags/mood
    @GET("tracks")
    suspend fun searchTracks(
        @Query("client_id") clientId: String,
        @Query("tags") tags: String? = null,
        @Query("name") name: String? = null,
        @Query("search") search: String? = null,
        @Query("limit") limit: Int = 50,
        @Query("format") format: String = "json",
        @Query("audioformat") audioFormat: String = "mp32",
        @Query("imagesize") imageSize: Int = 500
    ): JamendoTrackResponse

    // Get track by ID
    @GET("tracks")
    suspend fun getTrackById(
        @Query("client_id") clientId: String,
        @Query("id") trackId: String,
        @Query("format") format: String = "json",
        @Query("audioformat") audioFormat: String = "mp32",
        @Query("imagesize") imageSize: Int = 500
    ): JamendoTrackResponse

    // Get popular/featured tracks
    @GET("tracks")
    suspend fun getPopularTracks(
        @Query("client_id") clientId: String,
        @Query("order") order: String = "popularity_total",
        @Query("limit") limit: Int = 50,
        @Query("format") format: String = "json",
        @Query("audioformat") audioFormat: String = "mp32",
        @Query("imagesize") imageSize: Int = 500
    ): JamendoTrackResponse

    // Get playlists
    @GET("playlists")
    suspend fun getPlaylists(
        @Query("client_id") clientId: String,
        @Query("name") name: String? = null,
        @Query("limit") limit: Int = 20,
        @Query("format") format: String = "json"
    ): JamendoPlaylistResponse

    // Get playlist tracks
    @GET("playlists/tracks")
    suspend fun getPlaylistTracks(
        @Query("client_id") clientId: String,
        @Query("id") playlistId: String,
        @Query("limit") limit: Int = 50,
        @Query("format") format: String = "json",
        @Query("imagesize") imageSize: Int = 500
    ): JamendoPlaylistTracksResponse
}

object JamendoRetrofitInstance {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.jamendo.com/v3.0/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api: MusicApi = retrofit.create(MusicApi::class.java)
}