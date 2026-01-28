package com.jina.clonespotify.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TrackDTO(
    val id: String,
    val name: String,
    @SerialName("artist_name") val artistName: String? = null,
    @SerialName("artist_id") val artistId: String? = null,
    @SerialName("album_name") val albumName: String? = null,
    @SerialName("album_id") val albumId: String? = null,
    @SerialName("album_image") val albumImage: String? = null,
    val image: String? = null,
    val audio: String,
    val audiodownload: String? = null,
    val duration: Int = 0,
    val releasedate: String? = null
)

fun TrackDTO.toTrack(): Track {
    return Track(
        id = id,
        title = name,
        artist = Artist(
            id = artistId ?: "unknown",
            name = artistName ?: "Unknown Artist"
        ),
        album = albumName ?: "Unknown Album",
        coverUrl = albumImage?.takeIf { it.isNotEmpty() } ?: image ?: "",
        audioUrl = audio
    )
}






