package com.jina.clonespotify.data.remote

import com.google.gson.annotations.SerializedName
import com.jina.clonespotify.data.model.Album
import com.jina.clonespotify.data.model.Artist
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

data class TrackDTO(
    @SerializedName("id") val id: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("artist") val artist: Artist?,
    @SerializedName("album") val album: Album?,
    @SerializedName("preview") val preview: String?,
    @SerializedName("md5_image") val md5Image: String?
)

data class TrackSearchRespond(
    @SerializedName("docs") val docs: List<TrackDTO>
)

interface MusicApi {
    @GET("search")
    suspend fun searchTracks(
        @Query("q") query: String,
        @Query("fields") fields: String = "id, title, artist, album, preview, md5_image",
        @Query("limit") limit: Int = 20
    ): TrackSearchRespond
}

object RetrofitInstance {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://developers.deezer.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api: MusicApi = retrofit.create(MusicApi::class.java)
}