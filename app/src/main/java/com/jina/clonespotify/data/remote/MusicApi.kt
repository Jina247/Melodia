package com.jina.clonespotify.data.remote

import com.jina.clonespotify.data.model.RecordingSearchResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface MusicApi {
    @GET("recording")
    suspend fun searchTracks(
        @Query("query") query: String,
        @Query("fmt") fmt: String = "json",
        @Query("limit") limit: Int = 20
    ): RecordingSearchResponse

}

val client = OkHttpClient.Builder()
    .addInterceptor { chain ->
        val request = chain.request().newBuilder()
            .header("User-Agent", "CloneSpotifyApp/1.0 (trannnhii2407@gmail.com)")
            .build()
        chain.proceed(request)
    }
    .build()

object RetrofitInstance {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://musicbrainz.org/ws/2/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    val api: MusicApi = retrofit.create(MusicApi::class.java)
}